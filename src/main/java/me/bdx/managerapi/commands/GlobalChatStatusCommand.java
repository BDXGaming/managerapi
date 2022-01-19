package me.bdx.managerapi.commands;

import me.bdx.managerapi.statusControls.ChatStatus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GlobalChatStatusCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender.hasPermission("managerapi.chat.disable")){

            if(args.length >= 0 ){

                if(args[0].equalsIgnoreCase("disable")){

                    try{
                        if(args[1].equalsIgnoreCase("all")){
                            ChatStatus.setGlobalChat(false);
                            sender.sendMessage(ChatColor.RED + "GlobalChat has been disabled!");
                        }

                        else if (args[1].equalsIgnoreCase("incoming")){
                            ChatStatus.setIncoming(false);
                            sender.sendMessage(ChatColor.RED + "Incoming GlobalChat messages have been disabled!");
                        }

                        else if (args[1].equalsIgnoreCase("outgoing")){
                            ChatStatus.setOutgoing(false);
                            sender.sendMessage(ChatColor.RED + "Outgoing GlobalChat messages have been disabled!");
                        }
                    }catch (IndexOutOfBoundsException e){
                        sender.sendMessage(ChatColor.YELLOW + "Please be sure to include what you wish to disable");
                    }

                    return true;
                }

                else if(args[0].equalsIgnoreCase("enable")){

                    try{

                       if(args[1].equalsIgnoreCase("all")){
                            ChatStatus.setGlobalChat(true);
                           sender.sendMessage(ChatColor.GREEN + "GlobalChat has been enabled!");
                        }

                        else if (args[1].equalsIgnoreCase("incoming")){
                            ChatStatus.setIncoming(true);
                           sender.sendMessage(ChatColor.GREEN + "Incoming GlobalChat messages have been enabled!");
                        }

                        else if (args[1].equalsIgnoreCase("outgoing")){
                            ChatStatus.setOutgoing(true);
                           sender.sendMessage(ChatColor.GREEN + "Outgoing GlobalChat messages have been enabled!");
                        }

                    }
                    catch (IndexOutOfBoundsException e){
                        sender.sendMessage(ChatColor.YELLOW + "Please be sure to include what you wish to enable");
                    }

                    return true;
                }

                else if(args[0].equalsIgnoreCase("status")){

                    try{
                       if(args[1].equalsIgnoreCase("all")){
                            sender.sendMessage("All: "+ String.valueOf(ChatStatus.getGlobalStatus()));
                        }

                        else if (args[1].equalsIgnoreCase("incoming")){
                            sender.sendMessage("Incoming: " + String.valueOf(ChatStatus.getIncomingChatStatus()));
                        }

                        else if (args[1].equalsIgnoreCase("outgoing")){
                            sender.sendMessage("Outgoing: "  +String.valueOf(ChatStatus.getOutgoingChatStatus()));
                        }


                    }
                    catch (IndexOutOfBoundsException e){
                        String msg =  ChatColor.BOLD + ""+ChatColor.GREEN + "GlobalChat Status List" + ChatColor.RESET + "\n \n"+ "All: " + String.valueOf(ChatStatus.getGlobalStatus()) +"\nIncoming: " + String.valueOf(ChatStatus.getIncomingChatStatus()) + "\nOutgoing: " +String.valueOf(ChatStatus.getOutgoingChatStatus());
                        sender.sendMessage(msg);
                    }
                    return true;
                }

            }

        }

        return false;
    }
}
