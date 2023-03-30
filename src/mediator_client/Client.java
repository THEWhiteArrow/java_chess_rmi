package mediator_client;

import mediator_server.ModelServer;
import model_client.ModelClient;
import model_server.GameRoom;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.RemoteListener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client implements RemoteListener<String,GameRoom>,
    Serializable
{

    private mediator_client.ModelServer server;
    private PropertyChangeSupport property;
    public Client(){
        this.property = new PropertyChangeSupport(this);
    }

    @Override
    public void propertyChange(ObserverEvent<String, GameRoom> evt) throws RemoteException{
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


    public boolean createGameRoom(String id) throws RemoteException
    {
        server.createGameRoom(id);
        try {
            server.addListener(this,id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    public boolean setSpectator()  {
        return false;
    }


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


    public void sendNotation(String roomId, String notation)
        throws RemoteException
    {
        server.updateChessGameRoom(roomId, notation);
    }


    public String getNotation(String id) throws RemoteException
    {
        return server.getNotation(id);
    }


    public void displayMessage(String msg ) {
        System.out.println("FAKE: Displaying msg :))");
    }


    public boolean connectToServer(String host, int port)
            {
        try{
            this.server = (mediator_client.ModelServer) Naming.lookup("rmi://localhost:1099/SERVER");
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }


    public void sendChatMessage(String roomId, String name, String s)
        throws RemoteException
    {
        server.addChatMessage(roomId,name, s);
    }


    public ArrayList<String> getAllChat(String roomId) throws RemoteException
    {
        return server.getAllChats(roomId);
    }


    public void addListener(PropertyChangeListener listener) {
        property.addPropertyChangeListener(listener);
    }


    public void removeListener(PropertyChangeListener listener) {
        property.removePropertyChangeListener(listener);
    }
}
