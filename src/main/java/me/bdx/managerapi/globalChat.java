package me.bdx.managerapi;

import me.bdx.managerapi.api.chatApi;
import org.bukkit.Bukkit;

public class globalChat {

    public globalChat(){
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
        chatApi.broadcast(message, permission);
    }

    /**
     * Broadcasts the given message to all connected servers
     * This includes servers listening to other channels
     * @param message String
     */
    public void broadcast(String message){
        Bukkit.broadcastMessage(message);
        chatApi.broadcast(message);
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
        chatApi.channelBroadcast(message, channel, permission);

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
        chatApi.channelBroadcast(message, channel);
    }

    /**
     * Broadcasts the given message to all users on the same channel as the plugin instance
     * @param message String
     */
    public void channelBroadcast(String message){
        Bukkit.broadcastMessage(message);
        chatApi.channelBroadcast(message);
    }

}
