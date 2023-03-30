package mediator_client;



import java.util.ArrayList;

public class GameRoom
{
	private String id;

	private Chess chess;
	private ArrayList<String> chatLogs;
	private ArrayList<Client> spectators;

	public GameRoom(String id){
		this.id=id;
		this.chess=new Chess();
		this.chatLogs = new ArrayList<>();
		chatLogs.add("SYSTEM: Welcome all players!");
		spectators = new ArrayList<>();
	}

	public synchronized Chess getChessGame(){
		return chess;
	}

	public String getId(){
		return id;
	}



	public void addChatMessage(String username, String message){
		chatLogs.add(0,username+" : "+ message);

	}
	public void addSpectator(Client spectator)
	{
		spectators.add(spectator);
	}


	public  ArrayList<String> getChatLogs(){
		return chatLogs;
	}


}
