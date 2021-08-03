package me.bdx.managerapi.events;

import me.bdx.managerapi.api.chatApi;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.json.JSONException;

public class leaveEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

        if(event.getPlayer().hasPermission("managerapi.staff")){
            try {
                chatApi.removeStaff(event.getPlayer());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                chatApi.requestStaff();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
