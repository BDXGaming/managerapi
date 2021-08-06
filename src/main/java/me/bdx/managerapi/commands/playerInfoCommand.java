package me.bdx.managerapi.commands;

import me.bdx.managerapi.Managerapi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class playerInfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender.hasPermission("managerapi.player.info")){

            String info = "\n \n" + ChatColor.BOLD + ChatColor.translateAlternateColorCodes('&',"&e&lPlayer &r&a" + args[0] + "&r&e&l information \n \n");

            Player p = (Player) Bukkit.getOfflinePlayer(args[0]);
            info += ChatColor.RESET +""+ ChatColor.YELLOW + "Username: " +ChatColor.WHITE+ p.getName() + "\n";
            info += ChatColor.YELLOW + "Displayname: " + ChatColor.RESET + "" +ChatColor.translateAlternateColorCodes('&', p.getDisplayName())  + "\n";
            info += ChatColor.YELLOW + "UUID: " + ChatColor.WHITE + p.getUniqueId() + "\n";
            info += ChatColor.YELLOW + "User Prefix: " + ChatColor.translateAlternateColorCodes('&', Managerapi.chat.getPlayerPrefix(p))+ "\n";
            info += ChatColor.YELLOW + "Group: " + Arrays.toString(Managerapi.chat.getPlayerGroups(p))+ "\n";

            if(p.isOp()){
                info += ChatColor.YELLOW + "OP: " + ChatColor.GREEN + "true" + "\n";
            } else{
                info += ChatColor.YELLOW + "OP: " + ChatColor.RED + "false" + "\n";
            }

            if(p.isFlying()){
                info += ChatColor.YELLOW + "Flying: " + ChatColor.GREEN + "true" + "\n";
            } else{
                info += ChatColor.YELLOW + "Flying: " + ChatColor.RED + "false" + "\n";
            }

            if(Managerapi.essentials.getUser(p.getUniqueId()).isGodModeEnabled()){
                info += ChatColor.YELLOW + "God: " + ChatColor.GREEN + "true" + "\n";
            }else{
                info += ChatColor.YELLOW + "God: " + ChatColor.RED + "false" + "\n";
            }

            if(Managerapi.essentials.getUser(p.getUniqueId()).isHidden()){
                info += ChatColor.YELLOW + "Vanished: " + ChatColor.GREEN + "true" + "\n";
            }else{
                info += ChatColor.YELLOW + "Vanished: " + ChatColor.RED + "false" + "\n";
            }

            if(Managerapi.essentials.getUser(p.getUniqueId()).isAfk()){
                info += ChatColor.YELLOW + "AFK: " + ChatColor.GREEN + "true" + "\n";
            }else{
                info += ChatColor.YELLOW + "AFK: " + ChatColor.RED + "false" + "\n";
            }

            sender.sendMessage(info + "\n \n");


        }



        return false;
    }
}
