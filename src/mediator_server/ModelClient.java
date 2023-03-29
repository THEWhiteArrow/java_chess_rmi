package mediator_server;

import java.util.ArrayList;

public interface ModelClient extends utility.observer.javaobserver.UnnamedPropertyChangeSubject {

	public  abstract  boolean createGameRoom(String id);
	boolean setSpectator();
	public abstract boolean joinGameRoom(String id);
	public abstract boolean leaveGameRoom(String id);
	public abstract void sendNotation(String roomId, String notation);
	public abstract String getNotation(String id);
	public abstract void displayMessage(String msg);
	boolean connectToServer(String host, int port);
	void sendChatMessage(String roomId, String name, String s);
	ArrayList<String> getAllChat(String roomId);
}
