package me.bdx.managerapi.commands;

import com.earth2me.essentials.User;
import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.api.ChatApi;
import me.bdx.managerapi.config.Managerapiconfig;
import me.bdx.managerapi.customEvents.GlobalChatEvent;
import me.bdx.managerapi.utils.ChatColorHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.bdx.managerapi.statusControls.ChatStatus;

public class Globalchatcommand implements CommandExecutor {

    /**
     * Sends the DevChat Messages
     * @param p Player
     * @param label String
     * @param args String[]
     */
    public void prepMsg(Player p, String label, String[] args){

        StringBuilder msg = new StringBuilder();
        User user = Managerapi.essentials.getUser(p);
        String name = user.getNick();
        for(String word: args){
            msg.append(word).append(" ");
        }


        try {
            ChatApi.sendMsg(p, msg.toString(), "chat-"+label, label);
            Bukkit.broadcast(ChatColor.GRAY +"(/"+ label+ ") " +"[" + Managerapiconfig.get().getString("server-name")+"] " + ChatColor.translateAlternateColorCodes('&', name) + ": " + ChatColor.LIGHT_PURPLE + msg, "managerapi.chatcommand.devchat");


        } catch (Exception e) {
            e.printStackTrace();
            p.sendMessage(ChatColor.RED + "Error sending message!");
        }

    }

    public void prepGlobalMessage(Player p, String label, String msg){

        User user = Managerapi.essentials.getUser(p);
        String name = user.getNick();

        String chatcolor = ChatColorHelper.playerToChatColor(p);

        try {

            ChatColor c;

            c = ChatColorHelper.stringToColor(chatcolor);

            String fullmsg = ChatColor.GRAY +"[" + Managerapiconfig.get().getString("server-name")+"] " + p.getDisplayName() + ": " + c + msg;

            GlobalChatEvent event = new GlobalChatEvent(p, msg, fullmsg, chatcolor,false);
            Bukkit.getPluginManager().callEvent(event);

            if(!event.isCancelled()){

                msg = event.getMessage();
                chatcolor = event.getChatColorString();
                fullmsg = event.getFullMessage();



                if(!ChatStatus.getOutgoingChatStatus()){
                    if(p.hasPermission("managerapi.chat.bypass")){
                        ChatApi.sendMsg(p, msg, "chat-"+label, label, chatcolor);
                        Bukkit.broadcast(fullmsg, "managerapi.chat");
                    }

                }
                else{
                    ChatApi.sendMsg(p, msg, "chat-"+label, label, chatcolor);
                    Bukkit.broadcast(fullmsg, "managerapi.chat");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if(label.equalsIgnoreCase("g")) {

            if (ChatStatus.getGlobalStatus() | sender.hasPermission("managerapi.chat.bypass")) {

                if (!(sender instanceof Player)) {

                    StringBuilder msg = new StringBuilder();

                    for (String word : args) {
                        msg.append(word + " ");
                    }

                    try {
                        ChatApi.sendConMsg("Console", msg.toString(), "chat-" + label, label, "lred");
                        Bukkit.broadcast(ChatColor.GRAY + "(/" + label + ") " + "[" + Managerapiconfig.get().getString("server-name") + "] " + ChatColor.WHITE + "Console" + ": " + ChatColor.RED + msg, "managerapi.chat");



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }

                else{
                    Player player = (Player) sender;

                    StringBuilder msg = new StringBuilder();

                    for (String word : args) {
                        msg.append(word).append(" ");
                    }

                    prepGlobalMessage(player, "g", msg.toString());
                    return true;
                }
            }
        }


        else if(label.equalsIgnoreCase("sc")){

            if(ChatStatus.getStaffChatStatus()){

                if(!(sender instanceof Player)){

                    StringBuilder msg = new StringBuilder();

                    for(String word: args){
                        msg.append(word).append(" ");
                    }

                    try {
                        ChatApi.sendConMsg("Console", msg.toString(),"chat-"+label, label, "lred");
                        Bukkit.broadcast(ChatColor.GRAY +"(/"+ label+ ") " +"[" + Managerapiconfig.get().getString("server-name")+"] " + ChatColor.WHITE+ "Console" + ": " + ChatColor.LIGHT_PURPLE + msg, "managerapi.chatcommand");

                    } catch (Exception e) {
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.RED + "Error sending message!");
                    }

                    return true;
                }

                if(sender.hasPermission("managerapi.chatcommand")){

                    Player p = (Player)sender;

                    StringBuilder msg = new StringBuilder();

                    for(String word: args){
                        msg.append(word).append(" ");
                    }
                    User user = Managerapi.essentials.getUser(p);
                    String name = user.getNick();

                    try {
                        ChatApi.sendMsg(p, msg.toString(), "chat-"+label, label);
                        Bukkit.broadcast(ChatColor.GRAY +"(/"+ label+ ") " +"[" + Managerapiconfig.get().getString("server-name")+"] " + ChatColor.translateAlternateColorCodes('&', name)+ ": " + ChatColor.LIGHT_PURPLE + msg, "managerapi.chatcommand");
                    } catch (Exception e) {
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.RED + "Error sending message!");
                    }

                    return true;
                }
                else{
                    sender.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command!");
                }
            }else{
                sender.sendMessage(ChatColor.RED + "sc is currently disabled!");
                return true;
            }

        }

        else if (label.equalsIgnoreCase("dc")){

            if(ChatStatus.getDevChatStatus()){

                if (!(sender instanceof Player)) {

                    StringBuilder msg = new StringBuilder();

                    for (String word : args) {
                        msg.append(word).append(" ");
                    }

                    try {
                        ChatApi.sendConMsg("Console", msg.toString(), "chat-" + label, label, "lred");
                        Bukkit.broadcast(ChatColor.GRAY + "(/" + label + ") " + "[" + Managerapiconfig.get().getString("server-name") + "] " + ChatColor.WHITE + "Console" + ": " + ChatColor.LIGHT_PURPLE + msg, "managerapi.chatcommand.devchat");

                    } catch (Exception e) {
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.RED + "Error sending message!");
                    }

                    return true;
                }

                if(sender.hasPermission("managerapi.chatcommand.devchat")){

                    Player p = (Player)sender;

                    prepMsg(p, label, args);

                    return true;
                }
                else{
                    sender.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command!");
                    return true;
                }
            }else{
                sender.sendMessage(ChatColor.RED + "DevChat is currently disabled!");
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
