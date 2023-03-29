package model_client;



import mediator_client.ClientConnector;

import mediator_server.GamePackage;
import util.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

public class ModelManagerClient implements ModelClient, PropertyChangeListener {

	private ClientConnector client;

	private PropertyChangeSupport property;

	public ModelManagerClient() {
		this.property = new PropertyChangeSupport(this);
	}

	public boolean connectToServer(String host, int port){

		this.client = new ClientConnector(host, port);
		this.client.addListener(this);

		return true;
	}

	public synchronized boolean createGameRoom(String id) {
		return client.createGameRoom(id);
	}

	@Override public boolean setSpectator()
	{
		return client.setSpectator();
	}

	public synchronized boolean joinGameRoom(String id) {

		return client.joinGameRoom(id);
	}

	public synchronized boolean leaveGameRoom(String id) {
		return false;
	}


	public synchronized void sendNotation(String roomId, String notation) {
		client.sendNotation(roomId,notation);

	}

	public synchronized void sendChatMessage(String id, String username,String message){
		if (setSpectator() == true)
		{
			client.sendChatMessage( id, "(Spectator) " + username,  message);
		}
		else
		client.sendChatMessage( id, username,  message);
	}

	@Override
	public synchronized ArrayList<String> getAllChat(String roomId) {
		return client.getAllChat(roomId);
	}

	public synchronized String getNotation(String id) {
		return null;
	}

	/**
	 * NOT BEING USED
	 */
	public synchronized void displayMessage(String msg) {
		client.displayMessage(msg);
	}

	@Override
	public void addListener(PropertyChangeListener listener) {
		property.addPropertyChangeListener(listener);

	}

	@Override
	public void removeListener(PropertyChangeListener listener) {
		property.removePropertyChangeListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		property.firePropertyChange(evt);
	}
}
