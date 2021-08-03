package me.bdx.managerapi.commands;

import me.bdx.managerapi.api.chatApi;
import me.bdx.managerapi.config.managerapiconfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.bdx.managerapi.commands.globalchatcommand;
import me.bdx.managerapi.config.managerapiconfig;

public class globalchatcommand implements CommandExecutor {

    public void prepMsg(Player p, String label, String[] args){

        StringBuilder msg = new StringBuilder();

        for(String word: args){
            msg.append(word + " ");
        }


        try {
            chatApi.sendMsg(p, msg.toString(), "chat-"+label, label);

            for(Player player: Bukkit.getServer().getOnlinePlayers()){

                if(player.hasPermission("managerapi.chatcommand.devchat")){
                    player.sendMessage(ChatColor.GRAY +"(/"+ label+ ") " +"[" +managerapiconfig.get().getString("server-name")+"] " + p.getDisplayName() + ": " + ChatColor.LIGHT_PURPLE + msg);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if(label.equalsIgnoreCase("sc")){

            if(sender.hasPermission("managerapi.chatcommand")){

                Player p = (Player)sender;

                StringBuilder msg = new StringBuilder();

                for(String word: args){
                    msg.append(word + " ");
                }


                try {
                    chatApi.sendMsg(p, msg.toString(), "chat-"+label, label);

                    for(Player player: Bukkit.getServer().getOnlinePlayers()){

                        if(player.hasPermission("managerapi.chatcommand")){
                            player.sendMessage(ChatColor.GRAY +"(/"+ label+ ") " +"[" +managerapiconfig.get().getString("server-name")+"] " + p.getDisplayName() + ": " + ChatColor.LIGHT_PURPLE + msg);
                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            }
            else{
                sender.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command!");
            }
        }

        else if (label.equalsIgnoreCase("dc")){

            if(sender.hasPermission("managerapi.chatcommand.devchat")){

                Player p = (Player)sender;

                prepMsg(p, label, args);

                return true;
            }
            else{
                sender.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command!");
                return true;
            }
        }

        else{
            sender.sendMessage(ChatColor.RED + "Please use one of the labeled chats!");
            return true;
        }


        return false;
    }
}
