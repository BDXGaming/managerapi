package me.bdx.managerapi.customEvents;

import org.bukkit.ChatColor;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.json.JSONException;
import org.json.JSONObject;

public class staffChatReceiveEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;
    private final JSONObject rawPacket;
    private String senderName;
    private String senderDisplayName;
    private String messageContent;
    private String formattedMessage;
    private boolean isModified = false;

    public staffChatReceiveEvent(JSONObject rawPacket){
        super(true);
        this.rawPacket= rawPacket;
        try {
            this.senderName = this.rawPacket.getString("playerRealName");
            this.messageContent = this.rawPacket.getString("content");
            this.senderDisplayName = this.rawPacket.getString("playerDisplayName");
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

    public JSONObject getRawPacket() {
        return rawPacket;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * Gets the formatted message which will be sent to those with perms
     * @return String
     */
    public String getFormattedMessage() {
        if(isModified){
            return formattedMessage;
        }
        try {
            this.formattedMessage = ChatColor.GRAY+"(/"+this.rawPacket.getString("chat-label")+") "+"["+this.rawPacket.getString("server-name")+"] "+this.senderDisplayName + ": " + ChatColor.LIGHT_PURPLE + this.messageContent;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.formattedMessage;
    }

    /**
     * Sets the full message that will be sent to all users with the perms
     * No changes are made to the given string before the Bukkit.broadcast()
     * @param formattedMessage String
     */
    public void setFormattedMessage(String formattedMessage) {
        this.formattedMessage = formattedMessage;
        this.isModified = true;
    }
}
