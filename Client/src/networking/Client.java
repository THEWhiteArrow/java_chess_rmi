package networking;

import model_client.ModelClient;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.RemoteListener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Client implements ModelClient, RemoteListener<String, String> {

    private RemoteModel server;
    private PropertyChangeSupport property;
    public Client(){
        this.property=new PropertyChangeSupport(this);
    }

    @Override
    public boolean connectToServer(String host, int port) {
        try {
            UnicastRemoteObject.exportObject(this, 0);
            server = (RemoteModel) Naming.lookup("rmi://"+host+":1099/SERVER");
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean createGameRoom(String id) {
        boolean isCreated = false;
        try {
            isCreated = server.createRoom(id);
            if(isCreated)
                server.addListener(this,id+"-chat",id+"-notation");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return isCreated;
    }

    @Override
    public boolean setSpectator() {
        return false;
    }


    @Override
    public boolean joinGameRoom(String id) {
        boolean isJoined = false;
        try {
            server.addListener(this,id+"-chat",id+"-notation");
            isJoined=true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return isJoined;
    }

    @Override
    public boolean leaveGameRoom(String id) {
        return false;
    }

    @Override
    public void sendNotation(String roomId, String notation) {
        try {
            server.setNotation(roomId,notation);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNotation(String id) {
        return null;
    }

    @Override
    public void displayMessage(String msg) {
        return;
    }

    @Override
    public void sendChatMessage(String roomId, String name, String s) {
        try {
            server.addChatMessage(roomId,name,s);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getAllChat(String roomId) {
        ArrayList<String> chats = new ArrayList<>();

        try {
            chats = server.getAllChats(roomId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return chats;
    }

    @Override
    public void addListener(PropertyChangeListener listener) {
        property.addPropertyChangeListener(listener);
    }

    @Override
    public void removeListener(PropertyChangeListener listener) {
        property.addPropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(ObserverEvent<String, String> evt) throws RemoteException {
        String name = evt.getPropertyName();
        if(name.contains("chat")) name="chat";
        else if(name.contains("notation"))name="notation";

        property.firePropertyChange(name, evt.getValue1(), evt.getValue2());
    }
}
