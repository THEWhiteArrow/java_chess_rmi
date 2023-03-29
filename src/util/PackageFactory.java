package util;

public abstract class PackageFactory {
    protected abstract String createJsonPackage(String type,String notation, String message,String roomId, String username,String error);
    public String getJsonPackage(String type,String notation, String message,String roomId, String username,String error){
       return createJsonPackage(type,notation,message,roomId, username, error);
    }
}
