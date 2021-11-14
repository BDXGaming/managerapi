package me.bdx.managerapi.commands;

import com.earth2me.essentials.User;
import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.api.chatApi;
import me.bdx.managerapi.config.managerapiconfig;
import me.bdx.managerapi.customEvents.GlobalChatEvent;
import me.bdx.managerapi.utils.ChatColorHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.bdx.managerapi.statusControls.chatStatus;
import me.bdx.managerapi.commands.globalchatcommand;
import me.bdx.managerapi.config.managerapiconfig;

public class globalchatcommand implements CommandExecutor {

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
            msg.append(word + " ");
        }


        try {
            chatApi.sendMsg(p, msg.toString(), "chat-"+label, label);
            Bukkit.broadcast(ChatColor.GRAY +"(/"+ label+ ") " +"[" +managerapiconfig.get().getString("server-name")+"] " + ChatColor.translateAlternateColorCodes('&', name) + ": " + ChatColor.LIGHT_PURPLE + msg, "managerapi.chatcommand.devchat");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void prepGlobalMessage(Player p, String label, String msg){

        User user = Managerapi.essentials.getUser(p);
        String name = user.getNick();

        String chatcolor = "";

        if(p.hasPermission("managerapi.chatcolor.dred")){
            chatcolor = "dred";
        }
        else if(p.hasPermission("managerapi.chatcolor.lred")){
            chatcolor = "lred";
        }else if(p.hasPermission("managerapi.chatcolor.blue")){
            chatcolor = "blue";
        }
        else if(p.hasPermission("managerapi.chatcolor.white")){
            chatcolor = "white";
        }
        else{
            chatcolor = "grey";
        }


        try {

            ChatColor c;

            c = ChatColorHelper.stringToColor(chatcolor);

            String fullmsg = ChatColor.GRAY +"[" + managerapiconfig.get().getString("server-name")+"] " + p.getDisplayName() + ": " + c + msg;

            GlobalChatEvent event = new GlobalChatEvent(p, msg, fullmsg, chatcolor,false);
            Bukkit.getPluginManager().callEvent(event);

            if(!event.isCancelled()){

                if(event.isModified()){
                    msg = event.getMessage();
                    fullmsg = event.getFullMessage();
                    ChatColor cc = event.getChatColor();
                    chatcolor = event.getChatColorString();
                    fullmsg = ChatColor.GRAY +"[" + managerapiconfig.get().getString("server-name")+"] " + p.getDisplayName() + ": " + cc + msg;
                }



                if(!chatStatus.getOutgoingChatStatus()){
                    if(p.hasPermission("managerapi.chat.bypass")){
                        chatApi.sendMsg(p, msg, "chat-"+label, label, chatcolor);
                        Bukkit.broadcast(fullmsg, "managerapi.chat");
                    }

                }
                else{
                    chatApi.sendMsg(p, msg, "chat-"+label, label, chatcolor);
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

            if (chatStatus.getGlobalStatus() | sender.hasPermission("managerapi.chat.bypass")) {

                if (!(sender instanceof Player)) {

                    StringBuilder msg = new StringBuilder();

                    for (String word : args) {
                        msg.append(word + " ");
                    }

                    try {
                        chatApi.sendConMsg("Console", msg.toString(), "chat-" + label, label, "lred");
                        Bukkit.broadcast(ChatColor.GRAY + "(/" + label + ") " + "[" + managerapiconfig.get().getString("server-name") + "] " + ChatColor.WHITE + "Console" + ": " + ChatColor.RED + msg, "managerapi.chat");



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

            if(chatStatus.getStaffChatStatus()){

                if(!(sender instanceof Player)){

                    StringBuilder msg = new StringBuilder();

                    for(String word: args){
                        msg.append(word + " ");
                    }

                    try {
                        chatApi.sendConMsg("Console", msg.toString(),"chat-"+label, label, "lred");

                        for(Player player: Bukkit.getServer().getOnlinePlayers()){

                            if(player.hasPermission("managerapi.chatcommand")){
                                player.sendMessage(ChatColor.GRAY +"(/"+ label+ ") " +"[" +managerapiconfig.get().getString("server-name")+"] " + ChatColor.WHITE+ "Console" + ": " + ChatColor.LIGHT_PURPLE + msg);
                            }

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }

                if(sender.hasPermission("managerapi.chatcommand")){

                    Player p = (Player)sender;

                    StringBuilder msg = new StringBuilder();

                    for(String word: args){
                        msg.append(word + " ");
                    }
                    User user = Managerapi.essentials.getUser(p);
                    String name = user.getNick();

                    try {
                        chatApi.sendMsg(p, msg.toString(), "chat-"+label, label);

                        for(Player player: Bukkit.getServer().getOnlinePlayers()){

                            if(player.hasPermission("managerapi.chatcommand")){
                                player.sendMessage(ChatColor.GRAY +"(/"+ label+ ") " +"[" +managerapiconfig.get().getString("server-name")+"] " + ChatColor.translateAlternateColorCodes('&', name)+ ": " + ChatColor.LIGHT_PURPLE + msg);
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
            }else{
                sender.sendMessage(ChatColor.RED + "StaffChat is currently disabled!");
                return true;
            }

        }

        else if (label.equalsIgnoreCase("dc")){

            if(chatStatus.getDevChatStatus()){

                if (!(sender instanceof Player)) {

                    StringBuilder msg = new StringBuilder();

                    for (String word : args) {
                        msg.append(word).append(" ");
                    }

                    try {
                        chatApi.sendConMsg("Console", msg.toString(), "chat-" + label, label, "lred");

                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {

                            player.sendMessage(ChatColor.GRAY + "(/" + label + ") " + "[" + managerapiconfig.get().getString("server-name") + "] " + ChatColor.WHITE + "Console" + ": " + ChatColor.LIGHT_PURPLE + msg);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
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
