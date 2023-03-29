import javafx.application.Application;
import javafx.stage.Stage;
import mediator_server.Server;
import model_server.ModelManagerServer;
import model_server.ModelServer;

import java.io.IOException;

public class ServerApplication extends Application
{
    public void start(Stage primaryStage) throws IOException {
        ModelServer model = new ModelManagerServer();

        Server serverConnector = new Server();
//        ModelClient model = new ModelManagerClient();
//
//        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
//        ViewHandler view = new ViewHandler(viewModelFactory);
//        view.start(primaryStage);
    }
}
