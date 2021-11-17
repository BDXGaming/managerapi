package me.bdx.managerapi.api;

import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.commands.globalStaffCommand;
import me.bdx.managerapi.customEvents.GlobalCommandReceive;
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

                if(chatStatus.getStaffChatStatus()){
                    Bukkit.broadcast(ChatColor.GRAY+"(/"+response.getString("chat-label")+") "+"["+response.getString("server-name")+"] "+response.getString("playerDisplayName") + ": " + ChatColor.LIGHT_PURPLE + response.getString("content"), "managerapi.chatcommand");
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

                    if(Managerapi.statusController.chatChannel.equalsIgnoreCase(response.getString("channel"))){

                        Bukkit.broadcast(ChatColor.GRAY+"["+response.getString("server-name")+"] "+ChatColor.translateAlternateColorCodes('&',response.getString("playerDisplayName")) + ": " + c + response.getString("content"), "managerapi.chat");
                    }
                    else{
                        for(UUID uuid: Managerapi.channelListeners.getListeners()){
                            try {
                                Player p = Bukkit.getPlayer(uuid);
                                if (p != null) {
                                    p.sendMessage(ChatColor.GRAY + "[" + response.getString("channel") + "]" + "[" + response.getString("server-name") + "] " + ChatColor.translateAlternateColorCodes('&', response.getString("playerDisplayName")) + ": " + c + response.getString("content"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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

    }


}
