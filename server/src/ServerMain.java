import networking.RemoteModel;
import networking.Server;
import model_server.ModelManagerServer;
import model_server.ModelServer;

public class ServerMain
{
    public static void main(String[] args) throws Exception {
        ModelServer model = new ModelManagerServer();
        RemoteModel server = new Server(model);
    }
}
