package networking;

import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteModel extends RemoteSubject<String,String> {
    void setNotation(String roomId, String notation) throws RemoteException;
    boolean createRoom(String roomId) throws RemoteException;
    ArrayList<String> getAllChats(String roomId) throws RemoteException;
    void addChatMessage(String roomId, String username, String message) throws RemoteException;
}
