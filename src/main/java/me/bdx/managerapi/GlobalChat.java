package me.bdx.managerapi;

import me.bdx.managerapi.api.ChatApi;
import org.bukkit.Bukkit;

public class GlobalChat {

    public GlobalChat(){
        //Empty for now
    }

    /**
     * Broadcasts the given message to all connected servers who have the given permission
     * This includes servers listening to other channels
     * @param message String
     * @param permission String
     */
    public void broadcast(String message, String permission){
        Bukkit.broadcast(message, permission);
        ChatApi.broadcast(message, permission);
    }

    /**
     * Broadcasts the given message to all connected servers
     * This includes servers listening to other channels
     * @param message String
     */
    public void broadcast(String message){
        Bukkit.broadcastMessage(message);
        ChatApi.broadcast(message);
    }

    /**
     * Broadcasts the given message to all users with the given permission in the given channel
     * @param message String
     * @param channel String
     * @param permission String
     */
    public void channelBroadcast(String message, String channel, String permission){

        if(Managerapi.statusController.chatChannel.equals(channel)){
            Bukkit.broadcast(message, permission);
        }
        ChatApi.channelBroadcast(message, channel, permission);

    }

    /**
     * Broadcasts the given message to all users on the given channel
     * @param message String
     * @param channel String
     */
    public void channelBroadcast(String message, String channel){
        if(Managerapi.statusController.chatChannel.equals(channel)){
            Bukkit.broadcastMessage(message);
        }
        ChatApi.channelBroadcast(message, channel);
    }

    /**
     * Broadcasts the given message to all users on the same channel as the plugin instance
     * @param message String
     */
    public void channelBroadcast(String message){
        Bukkit.broadcastMessage(message);
        ChatApi.channelBroadcast(message);
    }

}
