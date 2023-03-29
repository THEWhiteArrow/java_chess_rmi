package util;

import java.io.Serializable;

public class ChatPackage extends NetworkPackage implements Serializable {
    public final static String CHAT = "CHAT";
    public final static String GET = "GET";

    public ChatPackage(String type,String roomId,String userName, String message){
        super(type,null ,roomId,userName,message,null);
    }



}
