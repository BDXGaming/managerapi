package me.bdx.managerapi.commands;

import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.config.managerapiconfig;
import me.bdx.managerapi.statusControls.chatStatus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class reloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.hasPermission("managerapi.reload")){

            managerapiconfig.reload();
            chatStatus.loadFromConfig();
            Managerapi.statusController.reload();
            sender.sendMessage(ChatColor.GREEN + "[ManagerApi]: Config has been reloaded!");
            return true;

        }


        return false;
    }
}
