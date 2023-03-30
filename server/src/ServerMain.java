import javafx.application.Application;

public class ServerMain
{
    public static void main(String[] args)
    {
        Application.launch(ServerApplication.class);
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
    }
}
