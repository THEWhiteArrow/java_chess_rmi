package model_client;

import mediator_client.Client;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class modelManager implements ModelClient
{
  private Client client;

  @Override public boolean createGameRoom(String id)
  {

    return true;
  }

  @Override public boolean setSpectator()
  {
    return false;
  }

  @Override public boolean joinGameRoom(String id)
  {
    return false;
  }

  @Override public boolean leaveGameRoom(String id)
  {
    return false;
  }

  @Override public void sendNotation(String roomId, String notation)

  {

  }

  @Override public String getNotation(String id)
  {
    return null;
  }

  @Override public void displayMessage(String msg)
  {

  }

  @Override public boolean connectToServer(String host, int port)
  {
    return false;
  }

  @Override public void sendChatMessage(String roomId, String name, String s)
  {

  }

  @Override public ArrayList<String> getAllChat(String roomId)
  {
    return null;
  }
}
