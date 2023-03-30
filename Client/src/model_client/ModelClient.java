package model_client;

import java.util.ArrayList;

public interface ModelClient
{
  public boolean createGameRoom(String id);
  public boolean setSpectator () ;
  public boolean joinGameRoom(String id) ;
  public boolean leaveGameRoom(String id);
  public void sendNotation(String roomId, String notation);
  public String getNotation(String id) ;
  public void displayMessage(String msg );
  public boolean connectToServer(String host, int port);
  public void sendChatMessage(String roomId, String name, String s);
  public ArrayList<String> getAllChat(String roomId);

}
