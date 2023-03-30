package model_server;


import java.util.ArrayList;

public class ModelManagerServer implements ModelServer {

	private ArrayList<GameRoom> rooms;

	public ModelManagerServer() {
		rooms= new ArrayList<>();
	}

	@Override
	public boolean createGameRoom(String id) {
//		does not account for the case when room of given id already exists
		GameRoom room = new GameRoom(id);
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
	public boolean joinRoom(String id) {
		return true;
	}

	public boolean addChatMessage(String id, String username, String message){
		GameRoom room = getGameRoomById(id);
		if(room==null)return false;

		room.addChatMessage(username,message);

		return true;
	}

	@Override
	public ArrayList<String> getAllChats(String roomId) {
		return getGameRoomById(roomId).getAllChats();
	}

	@Override
	public boolean setNotation(String id, String notation) {
		GameRoom room = getGameRoomById(id);
		if(room==null)return false;

		room.getChessGame().setNotation(notation);
		return true;
	}

	@Override
	public boolean leaveGameRoom(String id) {
		return true;
	}

	@Override
	public String getNotation(String id) {
		return getGameRoomById(id).getChessGame().getNotation();
	}
}
