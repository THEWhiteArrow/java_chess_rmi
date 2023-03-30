package model_server;

import java.net.Socket;
import java.util.ArrayList;

public interface ModelServer {


	public abstract boolean createGameRoom(String id);

	public abstract boolean joinRoom(String id);

	public abstract boolean setNotation(String id, String notation);

	public abstract boolean leaveGameRoom(String id);

	public abstract String getNotation(String id);

    boolean addChatMessage(String roomId, String username, String message);

    ArrayList<String> getAllChats(String roomId);
}
