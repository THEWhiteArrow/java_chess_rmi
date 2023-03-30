package model_client;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ModelClient extends utility.observer.javaobserver.UnnamedPropertyChangeSubject {

	public  abstract  boolean createGameRoom(String id) throws RemoteException;
	boolean setSpectator() throws RemoteException;
	public abstract boolean joinGameRoom(String id) throws RemoteException;
	public abstract boolean leaveGameRoom(String id) throws RemoteException;
	public abstract void sendNotation(String roomId, String notation)
			throws RemoteException;
	public abstract String getNotation(String id) throws RemoteException;
	public abstract void displayMessage(String msg) throws RemoteException;
	boolean connectToServer(String host, int port) throws RemoteException;
	void sendChatMessage(String roomId, String name, String s)
			throws RemoteException;
	ArrayList<String> getAllChat(String roomId) throws RemoteException;
}
