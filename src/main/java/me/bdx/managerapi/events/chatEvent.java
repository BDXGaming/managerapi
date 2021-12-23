package me.bdx.managerapi.events;

import me.bdx.managerapi.api.chatApi;
import me.bdx.managerapi.config.managerapiconfig;
import me.bdx.managerapi.customEvents.GlobalChatEvent;
import me.bdx.managerapi.utils.ChatColorHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.bdx.managerapi.statusControls.chatStatus;

public class chatEvent implements Listener {

    public void prepMsg(Player p, String label, String msg){

        String chatcolor = ChatColorHelper.playerToChatColor(p);

        try {

            ChatColor c = ChatColorHelper.stringToColor(chatcolor);

            String fullmsg = ChatColor.GRAY +"[" + managerapiconfig.get().getString("server-name")+"] " + p.getDisplayName() + ": " + c + msg;

            GlobalChatEvent event = new GlobalChatEvent(p, msg, fullmsg, chatcolor);
            Bukkit.getServer().getPluginManager().callEvent(event);

            if(!event.isCancelled()){
                msg = event.getMessage();
                chatcolor = event.getChatColorString();
                fullmsg = event.getFullMessage();

                if(!(chatStatus.getOutgoingChatStatus())){
                    if(p.hasPermission("managerapi.chat.bypass")){
                        chatApi.sendMsg(p, msg, "chat-"+label, label, chatcolor);
                        Bukkit.broadcast(fullmsg, "managerapi.chat");
                    }
                }
                else{
                    chatApi.sendMsg(p, msg, "chat-"+label, label, chatcolor);
                    Bukkit.broadcast(fullmsg, "managerapi.chat");
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent chatEvent){
        if(chatStatus.getGlobalStatus()){
            chatEvent.setCancelled(true);
            prepMsg(chatEvent.getPlayer(), "g", chatEvent.getMessage());
        }
        else if(chatEvent.getPlayer().hasPermission("managerapi.chat.bypass")){
            chatEvent.setCancelled(true);
            prepMsg(chatEvent.getPlayer(), "g", chatEvent.getMessage());
        }

    }
}
