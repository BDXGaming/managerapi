package me.bdx.managerapi.customEvents;

import me.bdx.managerapi.chat.ChatSender;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GlobalChatEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;
    private boolean isModified;
    private Player player;
    private String message;
    private String fullMessage;
    private String chat_channel;
    private String sender;
    private String ChatColor;

    /**
     *
     * @param sender The Player sender
     * @param msgContent The message content the player is sending
     * @param fullMessage The full message being sent on the local server
     */
    public GlobalChatEvent(Player sender, String msgContent, String fullMessage, String ChatColor){
        super(true);
        this.player = sender;
        this.message = msgContent;
        this.chat_channel = "g";
        this.isCancelled = false;
        this.isModified = false;
        this.fullMessage = fullMessage;
        this.ChatColor = ChatColor;
    }

    /**
     * Constructs a new GlobalChatEvent supporting either sync or async
     * @param sender The player who is sending a message
     * @param msgContent The content of the message
     * @param fullMessage The full message
     * @param ChatColor The color  of the message
     * @param async boolean, whether the meth id async
     */
    public GlobalChatEvent(Player sender, String msgContent, String fullMessage, String ChatColor, boolean async){
        super(async);
        this.player = sender;
        this.message = msgContent;
        this.chat_channel = "g";
        this.isCancelled = false;
        this.isModified = false;
        this.fullMessage = fullMessage;
        this.ChatColor = ChatColor;
    }

    /**
     * Construct a new GlobalChatEvent using String sender rather than player
     *
     * @param sender The string sender of the chat
     * @param msgContent The string message that is being sent
     * @param fullMessage The full message being sent
     */
    public GlobalChatEvent(String sender, String msgContent, String fullMessage, String ChatColor){
        super(true);
        this.sender = sender;
        this.message = msgContent;
        this.chat_channel = "g";
        this.isCancelled = false;
        this.isModified = false;
        this.fullMessage = fullMessage;
        this.ChatColor = ChatColor;
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

    /**
     * Sets the full string message to be sent only on the local server
     * Network/Webhook message remains unchanged
     *
     * @param fullMessage The new full String Message
     */
    @Deprecated
    public void setFullMessage(String fullMessage){
        this.fullMessage = fullMessage;
        this.isModified = true;
    }

    /**
     * Gets the full String message that is being broadcast on the server
     *
     * @return void
     */
    public String getFullMessage(){
        return this.fullMessage;
    }

    public boolean isModified(){
        return this.isModified;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer(){
        return this.player;
    }

    public String getMessage(){
        return this.message;
    }

    public String getChatChannel(){
        return this.chat_channel;
    }

    /**
     * Updates the message that is being sent
     *
     * @param msg The new String message
     */
    public void editMessage(String msg){
        this.message = msg;
        this.isModified = true;
    }

    public ChatColor stringToColor(String chatcolor){
        ChatColor c;

        if(chatcolor.equalsIgnoreCase("dred")){
            c = org.bukkit.ChatColor.DARK_RED;
        }else if (chatcolor.equalsIgnoreCase("lred")){
            c = org.bukkit.ChatColor.RED;
        }else if(chatcolor.equalsIgnoreCase("blue")){
            c = org.bukkit.ChatColor.AQUA;
        }
        else if (chatcolor.equalsIgnoreCase("white")){
            c = org.bukkit.ChatColor.WHITE;
        }else{
            c = org.bukkit.ChatColor.GRAY;
        }

        return c;
    }

    public String getChatColorString(){
        return this.ChatColor;
    }

    public ChatColor getChatColor(){
        return stringToColor(this.ChatColor);
    }

    public void setChatColor(String chatColor) {
        this.ChatColor = chatColor;
        this.isModified = true;
    }

    public void setChatColor(ChatColor chatColor){
        String c;

        if(chatColor.equals(org.bukkit.ChatColor.DARK_RED)){
            c = "dred";
        }else if (chatColor.equals(org.bukkit.ChatColor.RED)){
            c = "lred";
        }else if(chatColor.equals(org.bukkit.ChatColor.AQUA)){
            c = "blue";
        }
        else if (chatColor.equals(org.bukkit.ChatColor.WHITE)){
            c = "white";
        }else{
            c = "grey";
        }

        this.ChatColor = c;
        this.isModified = true;
    }
}
