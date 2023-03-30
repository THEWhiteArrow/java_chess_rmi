package mediator_client;

import mediator_server.ModelServer;
import model_client.ModelClient;
import model_server.GameRoom;
import utility.observer.event.ObserverEvent;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client implements utility.observer.listener.RemoteListener<String, GameRoom>, ModelClient {

    private ModelServer server;
    private PropertyChangeSupport property;
    public Client(){
        this.property = new PropertyChangeSupport(this);
    }

    @Override
    public void propertyChange(ObserverEvent<String, GameRoom> evt) {
        try{
            String name = evt.getPropertyName();
            String value1 = evt.getValue1();
            GameRoom value2 = evt.getValue2();

    //        make logic for notation,chat,logs
            switch (name){
                case "NOTATION":
                    property.firePropertyChange(name, null, value2.getChessGame().getNotation());
                    break;
                case "CHAT":
                    property.firePropertyChange(name,null,value2.getChatLogs().get(0));
                    break;
            }
        }catch (Exception e){

        }
    }

    @Override
    public boolean createGameRoom(String id)  {
        server.createGameRoom(id);
        try {
            server.addListener(this,id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean setSpectator()  {
        return false;
    }

    @Override
    public boolean joinGameRoom(String id)   {
        try {
            server.joinRoom(id);
            server.addListener(this,id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean leaveGameRoom(String id)   {
        try{
           server.leaveGameRoom(id);
           server.removeListener(this,id);
           return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void sendNotation(String roomId, String notation)  {
        server.updateChessGameRoom(roomId, notation);
    }

    @Override
    public String getNotation(String id) {
        return server.getNotation(id);
    }

    @Override
    public void displayMessage(String msg ) {
        System.out.println("FAKE: Displaying msg :))");
    }

    @Override
    public boolean connectToServer(String host, int port)
            {
        try{
            this.server = (ModelServer) Naming.lookup("rmi://localhost:1099/SERVER");
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void sendChatMessage(String roomId, String name, String s)  {
        server.addChatMessage(roomId,name, s);
    }

    @Override
    public ArrayList<String> getAllChat(String roomId)  {
        return server.getAllChats(roomId);
    }

    @Override
    public void addListener(PropertyChangeListener listener) {
        property.addPropertyChangeListener(listener);
    }

    @Override
    public void removeListener(PropertyChangeListener listener) {
        property.removePropertyChangeListener(listener);
    }
}
