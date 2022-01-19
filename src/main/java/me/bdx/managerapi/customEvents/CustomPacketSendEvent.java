package me.bdx.managerapi.customEvents;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.json.JSONObject;

public class CustomPacketSendEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;
    private String rawPacket;
    private JSONObject customPacket;
    private String customPacketString;

    public CustomPacketSendEvent(String rawPacket, JSONObject customPacket){
        this.rawPacket = rawPacket;
        this.customPacket = customPacket;
    }

    public CustomPacketSendEvent(String rawPacket, String customPacket){
        this.rawPacket = rawPacket;
        this.customPacketString = customPacket;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }


    public String getRawPacket() {
        return rawPacket;
    }

    public JSONObject getCustomPacket() {
        return customPacket;
    }

    public String getCustomPacketString() {
        return customPacketString;
    }
}
