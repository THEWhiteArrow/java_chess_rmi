package model_server;

import java.util.ArrayList;


public class GameRoom {
	private String id;

	private Chess chess;
	private ArrayList<String> chats;

	public GameRoom(String id){
		this.id = id;
		this.chess = new Chess();
		this.chats = new ArrayList<>();
		chats.add(0,"Welcome my friends , in bulgaria its like this");
	}

	public Chess getChessGame(){
		return chess;
	}

	public String getId(){
		return id;
	}

	public void addChatMessage(String username, String message){
		chats.add(0, username+ " : "+message);
	}

	public ArrayList<String> getAllChats(){
		return chats;
	}

}
