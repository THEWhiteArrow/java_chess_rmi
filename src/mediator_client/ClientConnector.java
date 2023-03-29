package mediator_client;


import com.google.gson.Gson;
import model_client.ModelClient;
import util.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class ClientConnector  implements ModelClient, utility.observer.javaobserver.UnnamedPropertyChangeSubject
{

	private GamePackage receivedPackage;
	private ChatPackageList chatPackageList;
	private Gson gson;
	private Socket socket;
	private PropertyChangeSupport property;
	private BufferedReader in;
	private PrintWriter out;
	private ModelClient modelClient;
	private boolean setSpectator = false;


	public ClientConnector(String host,int port) {

		try
		{
			this.socket = new Socket(host,port);
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintWriter(socket.getOutputStream(),true);
			this.receivedPackage = null;
			this.chatPackageList=null;
			gson = new Gson();
			ClientReceiver  clientReceiver = new ClientReceiver(this,this.in);
			Thread t1 =new Thread((clientReceiver));
			t1.setDaemon(true);
			t1.start();
			this.property=new PropertyChangeSupport(this);




		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

	}

	public synchronized void  receivedPackage(String json) { //This function might be private and without any arguments passed
		// then we should check for a package being received by the inputStream (in.readline) and then check the type of this package.
		Map<String,String> pkgMap = gson.fromJson(json, Map.class);
		if(pkgMap.get("type").equals("CHAT")){
			ChatPackage chatPkg = gson.fromJson(json,ChatPackage.class);
			property.firePropertyChange("CHAT",null, chatPkg.getMessage());
		}
		else if(pkgMap.get("type").equals("CHAT-LIST")){
			chatPackageList = gson.fromJson(json, ChatPackageList.class);
			notify();
		}
		else{

			receivedPackage = gson.fromJson(json, GamePackage.class);
			switch (receivedPackage.getType()){
				case GamePackage.CREATE, GamePackage.JOIN:
					notify();
					break;
				case GamePackage.ERROR:
					if (receivedPackage.getError().equals("aaa111"))
						setSpectator=true;
					else setSpectator=false;
						break;
				default:
					property.firePropertyChange(receivedPackage.getType(),null, receivedPackage);
			}
		}

	}
	@Override
	public synchronized boolean setSpectator()
	{
		return setSpectator;
	}


	public synchronized ArrayList<String> getAllChat(String roomId){

		out.println( new ChatPackageFactory().getJsonPackage("GET",null,null,roomId,null,null ));
		try {
			wait();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}


		return chatPackageList.getChats();
	}


	public synchronized void sendChatMessage(String id, String username, String message){
		out.println( new ChatPackageFactory().getJsonPackage(ChatPackage.CHAT,null,message,id,username,null));
	}

	@Override public synchronized boolean createGameRoom(String id)
	{
		out.println( new GamePackageFactory().getJsonPackage(GamePackage.CREATE,null,null,id,null,null));		try {
			wait();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		if(receivedPackage!=null && GamePackage.CREATE.equals(receivedPackage.getType()))
			return true;
		return false;
	}

	@Override public synchronized boolean joinGameRoom(String id)
	{
		out.println( new GamePackageFactory().getJsonPackage(GamePackage.JOIN,null,null,id,null,null));
		try {
			wait();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		if (receivedPackage!= null && GamePackage.JOIN.equals(receivedPackage.getType()))
			return true;
		return false;
	}

	@Override public synchronized boolean leaveGameRoom(String id)
	{
		return false;
	}

	@Override public synchronized void sendNotation(String roomId, String notation)
	{
		out.println( new GamePackageFactory().getJsonPackage(GamePackage.NOTATION,notation,null,roomId,null,null));
	}

	@Override public String getNotation(String id)
	{
		receivedPackage =gson.fromJson(id, GamePackage.class);
		if (receivedPackage.getType().equals(GamePackage.NOTATION))
			return receivedPackage.getNotation();
		return null;
	}

	@Override public void displayMessage(String msg)
	{
		out.println( new GamePackageFactory().getJsonPackage(GamePackage.ERROR,null,null,null,null,msg));
	}

	@Override
	public boolean connectToServer(String host, int port) {
//		no purpose for that
		return false;
	}



	@Override public void addListener(PropertyChangeListener listener)
	{
		property.addPropertyChangeListener(listener);
	}

	@Override public void removeListener(PropertyChangeListener listener)
	{
		property.removePropertyChangeListener(listener);
	}
}
