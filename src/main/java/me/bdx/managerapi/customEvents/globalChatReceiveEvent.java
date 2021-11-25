package me.bdx.managerapi.customEvents;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class globalChatReceiveEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;
    private final JSONObject globalChatPacket;
    private String messageContent;
    private String senderName;
    private UUID senderUUID;
    private String server;
    private String channel;

    /**
     * Creates a new instance of globalChatReceiveEvent
     * This event is called whenever a global chat is received regardless of the channel of the message
     * @param packet JSONObject
     */
    public globalChatReceiveEvent(JSONObject packet){
        this.globalChatPacket = packet;
        try {
            this.messageContent = packet.getString("content");
            this.senderName = packet.getString("playerRealName");
            this.senderUUID = UUID.fromString(packet.getString("playerUUID"));
            this.server = packet.getString("server-name");
            this.channel = packet.getString("channel");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    /**
     * Gets the globalChatPacket containing all information
     * @return JSONObject
     */
    public JSONObject getGlobalChatPacket() {
        return globalChatPacket;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getSenderName() {
        return senderName;
    }

    public UUID getSenderUUID() {
        return senderUUID;
    }

    public String getChannel() {
        return channel;
    }

    public String getServer() {
        return server;
    }
}