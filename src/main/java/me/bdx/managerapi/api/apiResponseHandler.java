package me.bdx.managerapi.api;

import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.commands.globalStaffCommand;
import me.bdx.managerapi.customEvents.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import me.bdx.managerapi.statusControls.chatStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class apiResponseHandler {

    public static JSONObject staff;


    public static void handleResponse(String resp) throws JSONException {

        JSONObject response = new JSONObject(resp);

        if(response.getString("type").contains("chat")){

            if(response.getString("type").equalsIgnoreCase("chat-sc")){

                staffChatReceiveEvent scEvent = new staffChatReceiveEvent(response);
                Bukkit.getServer().getPluginManager().callEvent(scEvent);

                if(!scEvent.isCancelled()){
                    if(chatStatus.getStaffChatStatus()){
                        Bukkit.broadcast(scEvent.getFormattedMessage(), "managerapi.chatcommand");
                    }
                }

            }

            else if(response.getString("type").equalsIgnoreCase("chat-dc")){

                if(chatStatus.getDevChatStatus()){
                    Bukkit.broadcast(ChatColor.GRAY+"(/"+response.getString("chat-label")+") "+"["+response.getString("server-name")+"] "+response.getString("playerDisplayName") + ": " + ChatColor.LIGHT_PURPLE + response.getString("content"), "managerapi.chatcommand.devchat");
                }


            }

            else if(response.getString("type").equalsIgnoreCase("chat-g")){

                if((chatStatus.getGlobalStatus()) && chatStatus.getIncomingChatStatus()){

                    ChatColor c;
                    String chatcolor = response.getString("chatColor");
                    if(chatcolor.equalsIgnoreCase("dred")){
                        c = ChatColor.DARK_RED;
                    }else if (chatcolor.equalsIgnoreCase("lred")){
                        c = ChatColor.RED;
                    }else if(chatcolor.equalsIgnoreCase("blue")){
                        c = ChatColor.AQUA;
                    }
                    else if (chatcolor.equalsIgnoreCase("white")){
                        c = ChatColor.WHITE;
                    }else{
                        c = ChatColor.GRAY;
                    }

                    globalChatReceiveEvent globalEvent = new globalChatReceiveEvent(response);
                    Bukkit.getServer().getPluginManager().callEvent(globalEvent);

                    if(!globalEvent.isCancelled()){
                        if(Managerapi.statusController.chatChannel.equalsIgnoreCase(response.getString("channel"))){

                            String formattedMessage = ChatColor.GRAY+"["+response.getString("server-name")+"] "+ChatColor.translateAlternateColorCodes('&',response.getString("playerDisplayName")) + ": " + c + response.getString("content");
                            chatChannelReceiveEvent event = new chatChannelReceiveEvent(formattedMessage, response.getString("content"), response.getString("server-name"), response.getString("playerDisplayName"), response.getString("playerRealName"), response.getString("channel"), c);
                            Bukkit.getServer().getPluginManager().callEvent(event);

                            if(!event.isCancelled()){
                                Bukkit.broadcast(event.getFormattedMessage(), "managerapi.chat");
                            }


                        }
                        else{
                            String msg = ChatColor.GRAY + "[" + response.getString("channel") + "]" + "[" + response.getString("server-name") + "] " + ChatColor.translateAlternateColorCodes('&', response.getString("playerDisplayName")) + ": " + c + response.getString("content");
                            altChatChannelReceiveEvent altChannelEvent = new altChatChannelReceiveEvent(msg, response.getString("content"),response.getString("server"), response.getString("playerDisplayName"),response.getString("playerName"), response.getString("channel"), c);
                            Bukkit.getServer().getPluginManager().callEvent(altChannelEvent);

                            if(!altChannelEvent.isCancelled()){
                                msg = altChannelEvent.getFormattedMessage();
                                for(UUID uuid: Managerapi.channelListeners.getListeners()){
                                    Player p = Bukkit.getPlayer(uuid);
                                    if (p != null) {
                                        p.sendMessage(msg);
                                    }
                                }
                            }


                        }
                    }

                }

            }

        }

        else if (response.getString("type").contains("staffList")){

            apiResponseHandler.staff = response.getJSONObject("staffList");

        }

        else if(response.getString("type").contains("updateStaff")){

            globalStaffCommand.refreshStaff();

        }

        else if(response.getString("type").contains("globalCommand")){

            GlobalCommandReceive event = new GlobalCommandReceive(response.getString("command"), response.getString("sender"));
            Bukkit.getScheduler().runTaskAsynchronously(Managerapi.managerapi, () -> Bukkit.getPluginManager().callEvent(event));

            if(!event.isCancelled()){
                Managerapi.commandHandler.processCommand(response.getString("command"));
                Bukkit.broadcast( ChatColor.GRAY + "[GC Log] "+response.getString("sender")+ " used the command /"+response.getString("command"), "managerapi.globalcommand.notify");
                }
            }

        else if(response.getString("type").contains("getPlayerList")){
            ArrayList<String> list = new ArrayList<>();
            JSONArray j = response.getJSONArray("playerList");
            for(int i= 0; i < response.getJSONArray("playerList").length(); i++){
                list.add((String) j.get(i));
            }
            Managerapi.globalPlayers.setOnlinePlayers(list);
        }

        else if (response.getString("type").contains("getPlayerServers")){
            JSONObject j = response.getJSONObject("playerServers");
            HashMap<String, String> dict = new HashMap<>();
            for (int i =0; i < Managerapi.globalPlayers.getOnlinePlayers().size(); i++){
                dict.put(Managerapi.globalPlayers.getOnlinePlayers().get(i), j.getString(Managerapi.globalPlayers.getOnlinePlayers().get(i)));
            }
            Managerapi.globalPlayers.setOnlinePlayerServers(dict);
        }

        //Handles all message broadcasts
        else if(response.getString("type").contains("broadcast")){

            //Global permission based broadcasts
            if(response.getString("type").contains("broadcast-perm")){
                String msg = response.getString("message");
                String perm = response.getString("permission");
                Bukkit.broadcast(msg, perm);
            }

            //Handles non-perm based broadcasts
            else if(response.getString("type").contains("broadcast-all")){
                String msg = response.getString("message");
                Bukkit.broadcastMessage(msg);
            }

            //Handles all incoming channel-based message broadcasts
            else if(response.getString("type").contains("broadcast-channel")){
                if(response.getString("channel").equals(Managerapi.statusController.chatChannel)){
                    if(response.getString("type").equals("broadcast-channel-perm")){
                        Bukkit.broadcast(response.getString("message"), response.getString("permission"));
                    }
                    Bukkit.broadcastMessage(response.getString("message"));
                }
            }
        }

        else if(response.getString("type").contains("customPacket") || response.getString("type").contains("customPacket-string")){
            Bukkit.getLogger().info("[ManagerAPI]: Custom Packet Received" + resp); //Logs all incoming custom packets
            if(response.getString("type").contains("customPacket-string")){
                customPacketReceiveEvent event = new customPacketReceiveEvent(resp,response);
                Bukkit.getServer().getPluginManager().callEvent(event);

            }else{
                customPacketReceiveEvent event = new customPacketReceiveEvent(resp, response.getJSONObject("custompacket"));
                Bukkit.getServer().getPluginManager().callEvent(event);
            }

        }

        else if (response.getString("type").contains("updateServers")){
            ArrayList<String> list = new ArrayList<>();
            JSONArray onlineServers = response.getJSONArray("onlineServers");
            for(int i= 0; i < onlineServers.length(); i++){
                list.add((String) onlineServers.get(i));
            }
            Managerapi.globalServers.updateGlobalServers(list);
        }

    }


}
