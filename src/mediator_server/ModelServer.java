package mediator_server;

import java.util.ArrayList;

public interface ModelServer
{



	public abstract boolean createGameRoom(String id, ModelClient clientHandler);

	public abstract boolean joinRoom(String id, ModelClient clientHandler);

	public abstract boolean updateChessGameRoom(String id, String notation);

	public abstract boolean leaveGameRoom(String id);

	public abstract String getNotation(String id);

    boolean addChatMessage(String roomId, String username, String message);

    ArrayList<String> getAllChats(String roomId);
}
