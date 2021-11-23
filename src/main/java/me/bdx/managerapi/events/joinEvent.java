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

        //The autoOp status from config file, stored in statusController
        boolean autoOp = Managerapi.statusController.autoOp;
        boolean deopOnJoin = Managerapi.statusController.deopOnJoin;
        boolean playerList = Managerapi.statusController.globalPlayerList;

        Player player = joinEvent.getPlayer();

        //Deops all players on join, if enabled in config
        if(deopOnJoin){
            player.setOp(false);
        }

        //If autoOP is enabled and player has permission, player is op'd using player.setOp(boolean)
        if(autoOp){
            if(player.hasPermission("managerapi.autoop")){
                player.setOp(true);
            }
        }

        //Adds the player to the stafflist if they have the permission managerapi.staff
        if(player.hasPermission("managerapi.staff")){

            chatApi.addStaff(player);

            try {
                chatApi.requestStaff();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if(Managerapi.statusController.globalPlayerList){
            try {
                chatApi.addPlayer(joinEvent.getPlayer());
                chatApi.syncPlayerLists();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
