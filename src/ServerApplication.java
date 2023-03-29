import javafx.application.Application;
import javafx.stage.Stage;
import mediator_server.ServerConnector;
import model_client.ModelClient;
import model_client.ModelManagerClient;
import model_server.ModelManagerServer;
import model_server.ModelServer;
import view_client.ViewHandler;
import viewmodel_client.ViewModelFactory;

import java.io.IOException;

public class ServerApplication extends Application
{
    public void start(Stage primaryStage) throws IOException {
        ModelServer model = new ModelManagerServer();

        ServerConnector serverConnector = new ServerConnector(model, 6789);
//        ModelClient model = new ModelManagerClient();
//
//        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
//        ViewHandler view = new ViewHandler(viewModelFactory);
//        view.start(primaryStage);
    }
}
