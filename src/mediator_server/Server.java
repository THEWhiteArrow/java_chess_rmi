package mediator_server;




import model_server.GameRoom;
import model_server.ModelServer;
import util.utility.observer.subject.*;
import utility.observer.listener.GeneralListener;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements ModelServer
{

	private mediator_server.ModelServer server;
	private ModelClient client;
	private PropertyChangeHandler<GameRoom,GameRoom> property;

	public Server()
			throws RemoteException, MalformedURLException
	{
		this.property = new PropertyChangeHandler<>(this,true);
		startRegistry();
		start();

	}

	private void start() throws RemoteException, MalformedURLException
	{
		UnicastRemoteObject.exportObject( this,0);
		Naming.rebind("GAME",  this);
		System.out.println("Server Started");
	}
	private void startRegistry() throws RemoteException
	{
		Registry reg = LocateRegistry.createRegistry(1099);
		System.out.println("Registry started");
	}


	@Override public boolean createGameRoom(String id,
			 Client client)
	{
		server.createGameRoom(id,client);
	}

	@Override public boolean joinRoom(String id, ModelClient client)
	{
		server.joinRoom(id,client);
	}

	@Override public boolean updateChessGameRoom(String id, String notation)
	{
		return server.updateChessGameRoom(id,notation);
	}

	@Override public boolean leaveGameRoom(String id)
	{
		return false;
	}

	@Override public String getNotation(String id)
	{
		return null;
	}

	@Override public boolean addChatMessage(String roomId, String username,
			String message)
	{
		return server.addChatMessage(roomId,username,message);
	}

	@Override public ArrayList<String> getAllChats(String roomId)
	{
		return server.getAllChats(roomId);
	}

	@Override public boolean addListener(
			GeneralListener<GameRoom, GameRoom> listener, String... propertyNames)
			throws RemoteException
	{
		return property.addListener(listener);
	}

	@Override public boolean removeListener(
			GeneralListener<GameRoom, GameRoom> listener, String... propertyNames)
			throws RemoteException
	{
		return property.removeListener(listener);
	}
}
