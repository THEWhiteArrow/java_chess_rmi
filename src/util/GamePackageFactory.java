package util;

import com.google.gson.Gson;
import mediator_server.GamePackage;

public class GamePackageFactory extends PackageFactory {
    @Override
    protected String createJsonPackage(String type,String notation, String message,String roomId, String username,String error) {
        return (new Gson()).toJson( new GamePackage(type, roomId,notation,error) );
    }
}
