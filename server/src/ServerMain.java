import javafx.application.Application;



public class ServerMain
{
    public static void main(String[] args) throws Exception
    {
        System.setProperty("java.security.policy", "security.policy");
        Application.launch(ServerApplication.class);
    }
}
