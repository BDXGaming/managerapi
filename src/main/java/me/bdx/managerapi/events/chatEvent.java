package me.bdx.managerapi.events;

import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.api.chatApi;
import me.bdx.managerapi.config.managerapiconfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chatEvent implements Listener {

    public void prepMsg(Player p, String label, String msg){

        String chatcolor = "";

        if(p.hasPermission("managerapi.chatcolor.dred")){
            chatcolor = "dred";
        }
        else if(p.hasPermission("managerapi.chatcolor.lred")){
            chatcolor = "lred";
        }else if(p.hasPermission("managerapi.chatcolor.blue")){
            chatcolor = "blue";
        }
        else if(p.hasPermission("managerapi.chatcolor.white")){
            chatcolor = "white";
        }
        else{
            chatcolor = "grey";
        }


        try {
            chatApi.sendMsg(p, msg, "chat-"+label, label, chatcolor);

            for(Player player: Bukkit.getServer().getOnlinePlayers()){

                if(player.hasPermission("managerapi.chat")){

                    ChatColor c;

                    if(chatcolor.equalsIgnoreCase("dred")){
                        c = ChatColor.DARK_RED;
                    }else if (chatcolor.equalsIgnoreCase("lred")){
                        c = ChatColor.RED;
                    }else if(chatcolor.equalsIgnoreCase("blue")){
                        c = ChatColor.AQUA;
                    }
                    else if (chatcolor.equalsIgnoreCase("white")){
                        c = ChatColor.WHITE;
                    }else{
                        c = ChatColor.GRAY;
                    }

                    player.sendMessage(ChatColor.GRAY +"[" + managerapiconfig.get().getString("server-name")+"] " + p.getDisplayName() + ": " + c + msg);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent chatEvent){

        chatEvent.setCancelled(true);
        prepMsg(chatEvent.getPlayer(), "g", chatEvent.getMessage());

    }
}
