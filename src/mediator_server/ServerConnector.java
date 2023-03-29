package mediator_server;



import model_server.ModelServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnector {

	private ServerSocket welcomeSocket;

	private int port;

	private ModelServer modelServer;

	public ServerConnector(ModelServer model, int port) {
		this.port = port;
		this.modelServer = model;
		System.out.println("Server starting ....");
		try
		{
			this.welcomeSocket = new ServerSocket(port);
			System.out.println("Server running: "+ InetAddress.getLocalHost().getHostAddress()+" | "+port);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		start();

	}

	private void start() {
		while	(true)
		try
		{
			Socket socket = welcomeSocket.accept();

			ServerClientHandler serverClientHandler = new ServerClientHandler(modelServer,socket);
			Thread t = new Thread(serverClientHandler);
			t.setDaemon(true);
			t.start();

			System.out.println("Client Connected");
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

	}

	public void close() throws IOException
	{
		welcomeSocket.close();
	}
}
