package networking;

import model_server.ModelServer;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeHandler;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements RemoteModel {

    private ModelServer model;
    private PropertyChangeHandler property;
    public Server(ModelServer model) throws Exception {
        this.model=model;
        this.property=new PropertyChangeHandler(this);
        startRegistry();
        startServer();
    }


    private void startServer() throws MalformedURLException, RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
        Naming.rebind("SERVER", this);
        System.out.println("Server started...");
    }

    private void startRegistry() throws Exception {
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started...");
        } catch (RemoteException e) {
            throw new Exception("Registry already started? " + e.getMessage());
        }
    }



    @Override
    public void setNotation(String roomId, String notation) throws RemoteException {
        model.setNotation(roomId, notation);
        property.firePropertyChange(roomId+"-notation",null,notation);
    }

    @Override
    public boolean createRoom(String roomId) throws RemoteException {
        return model.createGameRoom(roomId);
    }

    @Override
    public ArrayList<String> getAllChats(String roomId) throws RemoteException {
        return model.getAllChats(roomId);
    }

    @Override
    public void addChatMessage(String roomId, String username, String message) throws RemoteException {
        model.addChatMessage(roomId, username,message);
        property.firePropertyChange(roomId+"-chat",null,model.getAllChats(roomId).get(0));
    }

    @Override
    public boolean addListener(GeneralListener<String, String> listener, String... propertyNames) throws RemoteException {
        property.addListener(listener,propertyNames);
        return true;
    }

    @Override
    public boolean removeListener(GeneralListener<String, String> listener, String... propertyNames) throws RemoteException {
        property.addListener(listener,propertyNames);
        return true;
    }
}
