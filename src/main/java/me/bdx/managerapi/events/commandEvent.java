package me.bdx.managerapi.events;

import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.api.chatApi;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.json.JSONException;

public class commandEvent implements Listener {

    @EventHandler
    public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event){

        //Sends an update to stafflist if user vanishes
        if(event.getMessage().equalsIgnoreCase("/v") || event.getMessage().equalsIgnoreCase("/vanish")){

            boolean value = Managerapi.essentials.getUser(event.getPlayer().getUniqueId()).isHidden();

            try {
                chatApi.updateStaff(event.getPlayer(), !value);
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
