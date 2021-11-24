package me.bdx.managerapi.chatManagers;

import me.bdx.managerapi.api.chatApi;

public class ChatSender {

    private String Name;
    private String DisplayName;
    private String chatcolor;

    public ChatSender(String name, String DisplayName){
        this.Name = name;
        this.DisplayName = DisplayName;
        this.chatcolor = "white";
    }

    public ChatSender(String name){
        this.DisplayName = name;
        this.Name = name;
        this.chatcolor = "white";
    }

    public ChatSender(String name, String DisplayName, String color){
        this.Name = name;
        this.DisplayName = DisplayName;
        this.chatcolor = color;
    }

    public void setChatColor(String chatColor){
        this.chatcolor = chatColor;
    }

    public void sendGlobalMessage(String message) throws Exception {
        chatApi.sendCustomMsg(this.Name, this.DisplayName, message, "chat-g", "g", chatcolor);
    }

}
