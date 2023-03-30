package Networking;



import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeHandler;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Client implements ClientInterface, Updates
{

    private ModelServer server;
    private PropertyChangeHandler<String,GameRoom> property;

    public Client()
        throws RemoteException, MalformedURLException, NotBoundException

    {
        UnicastRemoteObject.exportObject(this,0);
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        this.property = new PropertyChangeHandler<>(this);
        this.server = (ModelServer) registry.lookup("Chess");
       //server.addListener((GeneralListener<String, GameRoom>) this) ;



    }

  /*  @Override public void propertyChange(ObserverEvent<String, GameRoom> evt)
        throws RemoteException
    {
        try
        {
            String name = evt.getPropertyName();
            String value1 = evt.getValue1();
            GameRoom value2 = evt.getValue2();

            //        make logic for notation,chat,logs
            switch (name)
            {
                case "NOTATION":
                    property.firePropertyChange(name, null,
                       // value2.getChessGame().getNotation());
                   // break;
                case "CHAT":
                    property.firePropertyChange(name, null,
                      //  value2.getChatLogs().get(0));
                    //break;
            }
        }
        catch (Exception e)
        {

        }
    }*/


   public boolean createGameRoom(String id) throws RemoteException
    {
        server.createGameRoom(id);
        server.addListener((GeneralListener<String,GameRoom>) this,id);

        return true;
    }


    public boolean setSpectator() throws RemoteException  {
        return false;
    }


    public boolean joinGameRoom(String id)throws RemoteException  {
        try {
            server.joinRoom(id);
            server.addListener((GeneralListener<String, GameRoom>) this,id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public boolean leaveGameRoom(String id)throws RemoteException   {
        try{
           server.leaveGameRoom(id);
           server.removeListener((GeneralListener<String, GameRoom>) this,id);
           return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public void sendNotation (String roomId, String notation) throws RemoteException

    {
        server.updateChessGameRoom(roomId, notation);
    }


    public String getNotation(String id)throws RemoteException
    {
        return server.getNotation(id);
    }


    public void displayMessage(String msg ) throws RemoteException{
        System.out.println("FAKE: Displaying msg :))");
    }


    public boolean connectToServer(String host, int port) throws RemoteException
            {
        try{
            this.server = (ModelServer) Naming.lookup("rmi://localhost:1099/SERVER");
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }


    public void sendChatMessage(String roomId, String name, String s)throws RemoteException

    {
        server.addChatMessage(roomId,name, s);
    }


    public ArrayList<String> getAllChat(String roomId)throws RemoteException
    {
        return server.getAllChats(roomId);
    }

  @Override public boolean addListener(
      GeneralListener<String, GameRoom> listener, String... propertyNames)
      throws RemoteException
  {
   return property.addListener(listener,propertyNames);
  }

  @Override public boolean removeListener(
      GeneralListener<String, GameRoom> listener, String... propertyNames)
      throws RemoteException
  {
    return property.removeListener(listener,propertyNames);
  }
}

