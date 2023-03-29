package mediator_server;

import com.google.gson.Gson;
import model_server.GameRoom;
import model_server.ModelServer;
import util.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class ServerClientHandler implements Runnable, PropertyChangeListener
{

	private Socket socket;

	private BufferedReader in;

	private PrintWriter out;

	private Gson gson;


	private ModelServer model;


	public ServerClientHandler(ModelServer model,Socket socket) {
		this.model = model;
		this.socket = socket; // Change class diagram ;
		this.gson = new Gson();
		try
		{
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintWriter(socket.getOutputStream(),true);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

	}

	public void disconnect() {
		try
		{
			in.close();
			out.close();
			socket.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void sendErrorPackage(String message) {
		out.println( new GamePackageFactory().getJsonPackage(GamePackage.ERROR,null,null,null,null,message));
	}

	public synchronized void setSpectator()
	{
		out.println( new GamePackageFactory().getJsonPackage(GamePackage.ERROR,null,null,null,null,"aaa111"));
	}

	public synchronized void sendNotationPackage(String notation) {
		out.println( new GamePackageFactory().getJsonPackage(GamePackage.NOTATION,notation,null,null,null,null));
	}

	public synchronized  void sendChatPackage(String message){
		out.println( new ChatPackageFactory().getJsonPackage(ChatPackage.CHAT,null,message,null,null,null));
	}

	public synchronized  void sendChatListPackage(ArrayList<String> chats){
		ChatPackageList chatPackageList = new ChatPackageList(chats);
//		doesnt make a sence to implement factory for this since this one is completely different from others
		out.println( gson.toJson(chatPackageList) );
	}

	public void run() {
		while (true)
		{
			try
			{
				String receive =in.readLine();
				Map<String,String> pkg = gson.fromJson(receive,Map.class);

				if(pkg.get("type").equals("CHAT")){
					ChatPackage chatPackageReceived = gson.fromJson(receive, ChatPackage.class);
					String roomId = chatPackageReceived.getRoomId();
					String username = chatPackageReceived.getUsername();
					String message = chatPackageReceived.getMessage();

					model.addChatMessage(roomId,username,message);
				}
				else if(pkg.get("type").equals("GET")){
					ChatPackage chatPackageReceived = gson.fromJson(receive, ChatPackage.class);
 					sendChatListPackage( model.getAllChats(chatPackageReceived.getRoomId()) );
				}
				else{

					GamePackage gamePackageReceived = gson.fromJson(receive, GamePackage.class);

					switch (gamePackageReceived.getType())

					{
						case GamePackage.NOTATION:

								String id = gamePackageReceived.getRoomID();
								String notation = gamePackageReceived.getNotation();
								model.updateChessGameRoom(id, notation);

							break;

						case GamePackage.ERROR:
							if (gamePackageReceived.getError().equals("aaa111"))
							{

							}
							String error = gamePackageReceived.getError();

							System.out.println(error);
							break;

						case GamePackage.JOIN:
							String roomID = gamePackageReceived.getRoomID();
							model.joinRoom(roomID,this);
							out.println( new GamePackageFactory().getJsonPackage(GamePackage.JOIN,null,null,roomID,null,null));
							break;
						case GamePackage.CREATE:
							String roomId = gamePackageReceived.getRoomID();
							model.createGameRoom(roomId,this);
							out.println( new GamePackageFactory().getJsonPackage(GamePackage.CREATE,null,null,null,null,null));
							break;
					}
				}

			}
			catch (IOException e)
			{
				System.out.println("error: "+e.getMessage());
				break;
//				throw new RuntimeException(e);
			}
		}


	}

	@Override public void propertyChange(PropertyChangeEvent evt)
	{
		GamePackage gamePackage = new GamePackage(null,null,null,
				(String) evt.getNewValue());
		out.println(gson.toJson(gamePackage));

	}
}
