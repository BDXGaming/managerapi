package me.bdx.managerapi.customEvents;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GlobalCommandSendEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;
    private String command;
    private String commandSenderName;


    public GlobalCommandSendEvent(String command_string, String sender){
        super(false);
        this.command = command_string;
        this.commandSenderName = sender;
        this.isCancelled = false;
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

    public String getCommand(){
        return this.command;
    }

    public String getCommandSenderName(){
        return this.commandSenderName;
    }
}
