package mediator_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;
import mediator_server.GamePackage;
import model_server.ModelServer;
import util.Logger;

public class ClientReceiver implements Runnable {

	private BufferedReader in;
	private Gson gson;
	private ClientConnector client;

	public ClientReceiver(ClientConnector client, BufferedReader in) {
		this.client = client;
		this.in = in;
		this.gson = new Gson();



	}
	public void run() {
		while(true){
			try {
				String receive = in.readLine();
				 client.receivedPackage(receive);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
