package model_server;


import mediator_server.ServerClientHandler;
import util.Logger;
import util.PkgType;

import java.util.ArrayList;


public class Chess {

	private String notation;

	private ArrayList<String> chatLogs;


	private ArrayList<ServerClientHandler> serverClientHandler;
	private ArrayList<ServerClientHandler> spectatorList;

	public Chess(){

		serverClientHandler = new ArrayList<>();
		spectatorList = new ArrayList<>();
		chatLogs = new ArrayList<>();
		chatLogs.add("SYSTEM: Welcome all players!");
	}
	public void setNotation(String notation) {
		this.notation = notation;
		notifyPlayers(PkgType.NOTATION);
		notifySpectators(PkgType.NOTATION);



	}

	public String getNotation() {
		return null;
	}


	public void addPlayer(ServerClientHandler player)
	{
		serverClientHandler.add(player);
	}
	public void addSpectator(ServerClientHandler spectator)
	{
		spectatorList.add(spectator);
		for (int i = 0; i < spectatorList.size();i++){
			spectatorList.get(i).setSpectator();
		}
	}
	public int getPlayers()
	{
		return serverClientHandler.size();
	}

	public void addChatMessage(String username, String message){
		chatLogs.add(0,username+" : "+ message);
		notifyPlayers(PkgType.CHAT);
		//notifySpectators(PkgType.CHAT);
	}

	public void notifyPlayers(PkgType chat){
		for (ServerClientHandler player : serverClientHandler) {
			if(player!=null)
				if(chat == PkgType.NOTATION)
					player.sendNotationPackage(notation);
				else if(chat == PkgType.CHAT)
					player.sendChatPackage(chatLogs.get(0));
		}
	}
	public void notifySpectators(PkgType chat)
	{
		for (ServerClientHandler player : spectatorList)
		{
			if (player != null)
				if (chat == PkgType.NOTATION)
					player.sendNotationPackage(notation);
				else if (chat == PkgType.CHAT)
					player.sendChatPackage(chatLogs.get(0));
		}
	}


	public synchronized ArrayList<String> getChatLogs(){
		return chatLogs;
	}

}
