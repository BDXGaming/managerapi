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



    @EventHandler
    public void onJoin(PlayerJoinEvent joinEvent) throws JSONException {
        boolean autoOp = Managerapi.statusController.autoOp;
        boolean deopOnJoin = Managerapi.statusController.deopOnJoin;

        Player player = joinEvent.getPlayer();

        if(deopOnJoin){
            player.setOp(false);
        }

        if(autoOp){
            if(player.hasPermission("managerapi.autoop")){
                player.setOp(true);
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
