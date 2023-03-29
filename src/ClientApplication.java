import javafx.application.Application;
import javafx.stage.Stage;
import model_client.ModelClient;
import model_client.ModelManagerClient;
import view_client.ViewHandler;
import viewmodel_client.ViewModelFactory;

import java.io.IOException;

public class ClientApplication extends Application
{
    public void start(Stage primaryStage) throws IOException {
        ModelClient model = new ModelManagerClient();

        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
       ViewHandler view = new ViewHandler(viewModelFactory);
       view.start(primaryStage);
    }
}
