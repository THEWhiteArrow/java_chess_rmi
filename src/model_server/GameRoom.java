package model_server ;

import mediator_server.ServerClientHandler;


public class GameRoom {
	private String id;

	private Chess chess;

	public GameRoom(String id){
		this.id=id;
		this.chess=new Chess();
	}

	public synchronized Chess getChessGame(){
		return chess;
	}

	public String getId(){
		return id;
	}

	public  void addChessPlayer(ServerClientHandler clientHandler){
		chess.addPlayer(clientHandler);
	}

	public void addChessChatMessage(String username, String message){
		chess.addChatMessage(username, message);
	}
	public void addSpectator(ServerClientHandler spectator)
	{
		chess.addSpectator(spectator);
	}
	public int getPlayers()
	{
		return chess.getPlayers();
	}

}
