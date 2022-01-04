package me.bdx.managerapi.customEvents;

import org.bukkit.ChatColor;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class altChatChannelReceiveEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;
    private String formattedMessage;
    private String messageContent;
    private String server;
    private String senderDisplayName;
    private String senderName;
    private String channel;
    private ChatColor chatColor;
    private boolean isFormatModified = false;

    /**
     * Creates a new instance of the altChatChannelReceiveEvent
     * This event is called whenever a message arrives on any channel other than the active listening channel
     * @param formattedMessage String
     * @param messageContent String
     * @param server String
     * @param senderDisplayName String
     * @param senderName String
     * @param channel String
     * @param chatColor ChatColor
     */
    public altChatChannelReceiveEvent(String formattedMessage, String messageContent, String server, String senderDisplayName, String senderName, String channel, ChatColor chatColor){
        super(true);
        this.formattedMessage = formattedMessage;
        this.messageContent = messageContent;
        this.server = server;
        this.senderDisplayName = senderDisplayName;
        this.senderName = senderName;
        this.channel = channel;
        this.chatColor = chatColor;
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

    public String getFormattedMessage() {
        if(!isFormatModified){
            return ChatColor.GRAY+ "(" + this.channel + ")"+"["+this.server+"] "+ChatColor.translateAlternateColorCodes('&',this.senderDisplayName )+ ": " + this.chatColor + this.messageContent;
        }
        return formattedMessage;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * This method directly sets the formatted message displayed to users
     * Recommended to use setMessageContent() to update only chat message content
     * @param formattedMessage String
     */
    public void setFormattedMessage(String formattedMessage){
        this.formattedMessage = formattedMessage;
        this.isFormatModified = true;
    }

    public String getServer() {
        return server;
    }

    public String getSenderDisplayName() {
        return senderDisplayName;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getChannel() {
        return channel;
    }
}