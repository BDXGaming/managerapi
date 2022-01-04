package me.bdx.managerapi.commands;

import me.bdx.managerapi.Managerapi;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.management.MemoryNotificationInfo;

public class toggleChannels implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender.hasPermission("managerapi.chat.viewother")){
            if(sender instanceof Player){
                Player p = (Player) sender;

                if(Managerapi.managerapi.getChannelListeners().checkForListener(p.getUniqueId())){
                    Managerapi.managerapi.getChannelListeners().removeListener(p);
                    sender.sendMessage(ChatColor.YELLOW + "You are no longer listening to other channels!");
                }
                else{
                    Managerapi.managerapi.getChannelListeners().addPlayerListener(p);
                    sender.sendMessage(ChatColor.GREEN + "You are now listening to all chat channels!");
                }
                return true;

            }
        }
        sender.sendMessage(ChatColor.RED+ "You do not have the required permissions to use this command!");

        return true;
    }
}
