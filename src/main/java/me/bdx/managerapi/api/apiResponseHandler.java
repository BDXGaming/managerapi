package me.bdx.managerapi.api;

import me.bdx.managerapi.commands.globalStaffCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;
import me.bdx.managerapi.statusControls.chatStatus;

public class apiResponseHandler {

    public static JSONObject staff;


    public static void handleResponse(String resp) throws JSONException {

        JSONObject response = new JSONObject(resp);

        if(response.getString("type").contains("chat")){

            if(response.getString("type").equalsIgnoreCase("chat-sc")){

                if(chatStatus.getStaffChatStatus()){
                    for(Player p: Bukkit.getServer().getOnlinePlayers()){

                        if(p.hasPermission("managerapi.chatcommand")){

                            p.sendMessage(ChatColor.GRAY+"(/"+response.getString("chat-label")+") "+"["+response.getString("server-name")+"] "+response.getString("playerDisplayName") + ": " + ChatColor.LIGHT_PURPLE + response.getString("content"));
                        }

                    }
                }

            }

            else if(response.getString("type").equalsIgnoreCase("chat-dc")){

                if(chatStatus.getDevChatStatus()){
                    for(Player p: Bukkit.getServer().getOnlinePlayers()){

                        if(p.hasPermission("managerapi.chatcommand.devchat")){

                            p.sendMessage(ChatColor.GRAY+"(/"+response.getString("chat-label")+") "+"["+response.getString("server-name")+"] "+response.getString("playerDisplayName") + ": " + ChatColor.LIGHT_PURPLE + response.getString("content"));
                        }

                    }
                }


            }

            else if(response.getString("type").equalsIgnoreCase("chat-g")){

                if((chatStatus.getGlobalStatus()) && chatStatus.getIncomingChatStatus() == true){
                    for(Player p: Bukkit.getServer().getOnlinePlayers()){

                        if(p.hasPermission("managerapi.chat")){

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


                            p.sendMessage(ChatColor.GRAY+"["+response.getString("server-name")+"] "+response.getString("playerDisplayName") + ": " + c + response.getString("content"));
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

    }


}
