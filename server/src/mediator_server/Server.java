package mediator_server;

import model_server.GameRoom;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeHandler;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements ModelServer
{

	private ArrayList<GameRoom> rooms;

	private PropertyChangeHandler<String,GameRoom> property;

	public Server() throws MalformedURLException, RemoteException {

		this.rooms = new ArrayList<>();
		this.property = new PropertyChangeHandler<>(this,true);
		startRegistry();
		start();




	}

	private void start() throws MalformedURLException, RemoteException {
		UnicastRemoteObject.exportObject( this,0);
		Naming.rebind("SERVER",  this);
		System.out.println("Server Started");
	}
	private void startRegistry() throws RemoteException {
		Registry reg = LocateRegistry.createRegistry(1099);
		System.out.println("Registry started");
	}


	@Override public boolean createGameRoom(String id) 
	{
		GameRoom room = new GameRoom(id);
		return rooms.add(room);
	}



	@Override
	public boolean joinRoom(String id) {
		return true;
	}

	@Override public boolean updateChessGameRoom(String id, String notation) 
	{
//		for (GameRoom room : rooms)
//		{
//			if(room.getId().equals(id))
//				room.getChessGame().setNotation(notation);
//		}
//		property.firePropertyChange("NOTATION", null ,getRoomById(id));
		return true;
	}

	@Override public boolean leaveGameRoom(String id) 
	{
		return false;
	}

	@Override public String getNotation(String id)
	{
		return null;
	}

	@Override
	public GameRoom getRoomById(String id)  {
		for(GameRoom room : rooms)
			if( id.equals(room.getId()))
				return room;
		return null;
	}

	@Override public boolean addChatMessage(String roomId, String username,String message) 
	{
//		getRoomById(roomId).addChatMessage(message,username);
//		property.firePropertyChange("CHAT", null, getRoomById(roomId));
		return true;
	}

	@Override public ArrayList<String> getAllChats(String roomId) 
	{
		return getRoomById(roomId).getChatLogs();
	}



	@Override
	public boolean addListener(GeneralListener<String, GameRoom> listener, String... propertyNames)  {
		property.addListener(listener,propertyNames);
		return true;
	}

	@Override
	public boolean removeListener(GeneralListener<String, GameRoom> listener, String... propertyNames)  {
		property.addListener(listener,propertyNames);
		return true;
	}
}
