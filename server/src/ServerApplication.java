import javafx.application.Application;
import javafx.stage.Stage;
import Networking.Server;

public class ServerApplication extends Application
{
    public void start(Stage primaryStage) throws Exception {
      Server server = new Server();
    }
}
