package model_server;

import mediator_server.GamePackage;
import mediator_server.ServerClientHandler;
import util.Logger;

import java.net.Socket;
import java.util.ArrayList;

public class ModelManagerServer implements ModelServer {

	private ArrayList<GameRoom> rooms;

	public ModelManagerServer() {
		rooms= new ArrayList<>();
	}

	@Override
	public synchronized boolean createGameRoom(String id, ServerClientHandler clientHandler) {
//		account for the case when room of given id already exists
		GameRoom room = new GameRoom(id);
		room.addChessPlayer(clientHandler);
		rooms.add(room);
		return true;
	}

	public synchronized GameRoom getGameRoomById(String id){
		if(id==null)return null;
		for(GameRoom room : rooms){
			if(id.equals(room.getId()))
				return room;
		}
		return null;
	}

	@Override
	public synchronized boolean joinRoom(String id, ServerClientHandler clientHandler) {
		GameRoom room = getGameRoomById(id);
		if(room==null)return false;

		if (room.getPlayers()>=2)
		{
			room.addSpectator(clientHandler);
		}
		else
		room.addChessPlayer(clientHandler);



		return true;
	}

	public synchronized  boolean addChatMessage(String id, String username, String message){
		GameRoom room = getGameRoomById(id);
		if(room==null)return false;

		room.addChessChatMessage(username,message);

		return true;
	}

	@Override
	public synchronized ArrayList<String> getAllChats(String roomId) {
		return getGameRoomById(roomId).getChessGame().getChatLogs();
	}

	@Override
	public synchronized boolean updateChessGameRoom(String id, String notation) {
		GameRoom room = getGameRoomById(id);
		if(room==null)return false;

		room.getChessGame().setNotation(notation);
		return true;
	}

	@Override
	public synchronized boolean leaveGameRoom(String id) {
		return false;
	}

	@Override
	public synchronized String getNotation(String id) {
		return null;
	}
}
