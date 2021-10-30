package me.bdx.managerapi.commands;

import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.api.chatApi;
import me.bdx.managerapi.customEvents.onGlobalCommandEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.JSONException;

public class globalCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender.hasPermission("managerapi.globalcommand")){

            StringBuilder command = new StringBuilder();
            if(args.length > 0){
                for(String word: args){
                    command.append(word).append(" ");
                }
                onGlobalCommandEvent event = new onGlobalCommandEvent(command.toString(), sender.getName());
                Bukkit.getServer().getPluginManager().callEvent(event);

                if(!event.isCancelled()){
                    Managerapi.commandHandler.processCommand(command.toString());
                    try {
                        chatApi.sendGlobalCommand(command.toString(), sender.getName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Bukkit.broadcast( ChatColor.GRAY + "[GC Log] "+sender.getName()+ " used the command /"+command.toString(), "managerapi.globalcommand.notify");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "GlobalCommand has been cancelled");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "You need to enter a command to use!");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command!");
        return true;
    }
}
