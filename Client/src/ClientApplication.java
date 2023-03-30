import javafx.application.Application;
import javafx.stage.Stage;
import Networking.Client;
import model_client.ModelClient;
import model_client.modelManager;
import view_client.ViewHandler;
import viewmodel_client.ViewModelFactory;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientApplication extends Application
{
    public void start(Stage primaryStage)
        throws RemoteException, MalformedURLException, NotBoundException
    {

        Client client = new Client();
        ModelClient model = new modelManager(client);

        ViewModelFactory viewModelFactory = new ViewModelFactory(
            model);
        ViewHandler view = new ViewHandler(viewModelFactory);
        view.start(primaryStage);
    }
}
