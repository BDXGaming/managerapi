package me.bdx.managerapi.events;

import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.api.chatApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.JSONException;

public class joinEvent implements Listener {

    public static Boolean autoOp = true;

    @EventHandler
    public void onJoin(PlayerJoinEvent joinEvent) throws JSONException {

        Player player = joinEvent.getPlayer();

        if(player.hasPermission("managerapi.autoop")){
            if(autoOp){
                player.setOp(true);
            }
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',Managerapi.chat.getPlayerPrefix(player).toString() + player.getName()));

        for(Player p: Bukkit.getServer().getOnlinePlayers()){

            if(Managerapi.essentials.getUser(p.getUniqueId()).isHidden()){
                player.sendMessage(p.getDisplayName() + " is hidden");
            }

        }

        if(player.hasPermission("managerapi.staff")){

            chatApi.addStaff(player);

            try {
                chatApi.requestStaff();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}
