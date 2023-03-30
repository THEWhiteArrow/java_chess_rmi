package mediator_server;

import mediator_client.Client;
import model_server.GameRoom;
import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ModelServer extends RemoteSubject<String, GameRoom>
{
	
	public abstract boolean createGameRoom(String id) ;

	public abstract boolean joinRoom(String id);

	public abstract boolean updateChessGameRoom(String id, String notation) ;

	public abstract boolean leaveGameRoom(String id) ;

	public abstract String getNotation(String id);

	GameRoom getRoomById(String id) ;

    boolean addChatMessage(String roomId, String username, String message) ;

    ArrayList<String> getAllChats(String roomId) ;
}
