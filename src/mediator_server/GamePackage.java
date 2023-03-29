package mediator_server;

import util.NetworkPackage;

import java.io.Serializable;

public class GamePackage extends NetworkPackage implements Serializable
{

	public static final String ERROR = "ERROR";

	public static final String NOTATION = "NOTATION";

	public static final String JOIN = "JOIN";
	public static final String CREATE = "CREATE";
	public static final String SPECTATE = "SPECTATE";




	public GamePackage(String type,String roomID,String notation, String error) {
		super(type,notation,roomID,null,null,error);
	}

}
