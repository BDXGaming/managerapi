package me.bdx.managerapi.commands;

import me.bdx.managerapi.Managerapi;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class viewOnlinePlayers implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("managerapi.online")){
            sender.sendMessage(String.valueOf(Managerapi.globalPlayers.getOnlinePlayers()));
            sender.sendMessage(String.valueOf(Managerapi.globalPlayers.getOnlinePlayerServers()));
            return true;
        }
        sender.sendMessage(ChatColor.RED + "You don't have the perms to use this command!");
        return true;
    }
}
