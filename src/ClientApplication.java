import javafx.application.Application;
import javafx.stage.Stage;
import mediator_client.Client;
//import model_client.ModelManagerClient;
import model_client.ModelClient;
import view_client.ViewHandler;
import viewmodel_client.ViewModelFactory;

import java.io.IOException;
import java.rmi.NotBoundException;

public class ClientApplication extends Application
{
    public void start(Stage primaryStage) throws IOException, NotBoundException
    {
        Client client = new Client();
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");


        ViewModelFactory viewModelFactory = new ViewModelFactory(
            (ModelClient) client);
        ViewHandler view = new ViewHandler(viewModelFactory);
        view.start(primaryStage);
    }
}
