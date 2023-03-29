package model_server;

import mediator_server.ModelClient;

import java.rmi.Remote;
import java.util.ArrayList;

public interface ModelServer extends utility.observer.subject.RemoteSubject<GameRoom,GameRoom>
{


	public abstract boolean createGameRoom(String id, Client client);

	public abstract boolean joinRoom(String id, Client client);

	boolean joinRoom(String id, Client client);
	public abstract boolean updateChessGameRoom(String id, String notation);

	public abstract boolean leaveGameRoom(String id);

	public abstract String getNotation(String id);

    boolean addChatMessage(String roomId, String username, String message);

    ArrayList<String> getAllChats(String roomId);
}
