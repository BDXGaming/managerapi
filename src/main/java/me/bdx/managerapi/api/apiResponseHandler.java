package me.bdx.managerapi.api;

import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.commands.globalStaffCommand;
import me.bdx.managerapi.customEvents.onGlobalCommandReceive;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

                    Bukkit.broadcast(ChatColor.GRAY+"["+response.getString("server-name")+"] "+ChatColor.translateAlternateColorCodes('&',response.getString("playerDisplayName")) + ": " + c + response.getString("content"), "managerapi.chat");
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

            onGlobalCommandReceive event = new onGlobalCommandReceive(response.getString("command"), response.getString("sender"));
            Bukkit.getScheduler().runTaskAsynchronously(Managerapi.managerapi, () -> Bukkit.getPluginManager().callEvent(event));

            if(!event.isCancelled()){
                Managerapi.commandHandler.processCommand(response.getString("command"));
                Bukkit.broadcast( ChatColor.GRAY + "[GC Log] "+response.getString("sender")+ " used the command /"+response.getString("command"), "managerapi.globalcommand.notify");
                }
            }

    }


}
