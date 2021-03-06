package me.bdx.managerapi.events;

import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.api.ChatApi;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.json.JSONException;

public class LeaveEventHandler implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

        //Updates the stafflist, sends remove request on any player leave (fixes ex-staff persistence issues)
        try {
            ChatApi.removeStaff(event.getPlayer());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            ChatApi.requestStaff();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(Managerapi.statusController.globalPlayerList){
            try {
                ChatApi.removePlayer(event.getPlayer());
                ChatApi.syncPlayerLists();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Removes the player from the Arraylist of AltChannelListeners when they leave the server
        if(Managerapi.channelListeners.getListeners().contains(event.getPlayer().getUniqueId())){
            Managerapi.channelListeners.removeListeners(event.getPlayer().getUniqueId());
        }
    }

}
