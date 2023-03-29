package util;

import com.google.gson.Gson;

public class ChatPackageFactory extends PackageFactory {
    @Override
    protected String createJsonPackage(String type,String notation, String message,String roomId, String username,String error) {
        return (new Gson()).toJson( new ChatPackage(type,roomId,username,message) );
    }
}
