package mediator_server;

import model_server.GameRoom;
import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ModelServer extends RemoteSubject<String, GameRoom>
{
	
	public abstract boolean createGameRoom(String id) throws RemoteException;

	public abstract boolean joinRoom(String id)throws RemoteException;

	public abstract boolean updateChessGameRoom(String id, String notation)throws RemoteException ;

	public abstract boolean leaveGameRoom(String id)throws RemoteException ;

	public abstract String getNotation(String id)throws RemoteException;

	GameRoom getRoomById(String id) throws RemoteException;

    boolean addChatMessage(String roomId, String username, String message)throws RemoteException ;

    ArrayList<String> getAllChats(String roomId)throws RemoteException ;
}
