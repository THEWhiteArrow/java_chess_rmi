package model_server;


import mediator_client.ClientConnector;
import util.PkgType;

import java.util.ArrayList;


public class Chess {

	private String notation;




	private ArrayList<Client> serverClientHandler;


	public Chess(){

		clients = new ArrayList<>();


	}
	public void setNotation(String notation) {
		this.notation = notation;
		notifyPlayers(PkgType.NOTATION);

	}

	public String getNotation() {
		return null;
	}


	public void addPlayer(Client player)
	{
		serverClientHandler.add(player);
	}

	public int getPlayers()
	{
		return serverClientHandler.size();
	}



	public void notifyPlayers(){
		for (Client player : serverClientHandler) {
			if(player!=null)
				if(chat == PkgType.NOTATION)
					player.sendNotationPackage(notation);
				else if(chat == PkgType.CHAT)
					player.sendChatPackage(chatLogs.get(0));
		}
	}





}
