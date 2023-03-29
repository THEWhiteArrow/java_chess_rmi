package util;

import java.util.ArrayList;

public class
ChatPackageList {

    public final String type= "CHAT-LIST";
    private ArrayList<String> chats;

     public ChatPackageList(ArrayList<String> chats){
         this.chats=chats;
     }

     public ArrayList<String> getChats(){
         return chats;
     }

}
