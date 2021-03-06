package me.bdx.managerapi.commands;

import me.bdx.managerapi.api.ApiResponseHandler;
import me.bdx.managerapi.api.ChatApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class GlobalStaffCommand implements CommandExecutor {

    /**
     * Updates the staff list for the server
     */
    public static void refreshStaff(){

        for(Player p: Bukkit.getServer().getOnlinePlayers()){

            if(p.hasPermission("managerapi.staff")){
                try {
                    ChatApi.addStaff(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender.hasPermission("managerapi.stafflist")){

            GlobalStaffCommand.refreshStaff();

            HashMap<String, String> sortedStaff = new HashMap<>();

            try {
                ChatApi.requestStaff();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String resp = " \n \n"+ChatColor.GREEN + ChatColor.BOLD + " ★ Online Staff ★ \n \n";
            int initLen = resp.length();
            try{
                if(ApiResponseHandler.staff != null){
                    for(int i = 0; i< ApiResponseHandler.staff.names().length(); i++){
                        try {
                            JSONObject user = ApiResponseHandler.staff.getJSONObject(ApiResponseHandler.staff.names().getString(i));

                            if(!sortedStaff.containsKey(user.getString("server"))) {

                                if(user.getBoolean("vanishState")){

                                    if(sender.hasPermission("managerapi.staff.vanish")){
                                        sortedStaff.put(user.getString("server"), "\n      "+ ChatColor.translateAlternateColorCodes('&',user.getString("staffDisplayName") + ChatColor.GRAY + " (Vanished)"));
                                    }

                                }
                                else{
                                    sortedStaff.put(user.getString("server"), "\n      "+ ChatColor.translateAlternateColorCodes('&',user.getString("staffDisplayName")));
                                }

                            }

                            else{

                                if(user.getBoolean("vanishState")){
                                    if(sender.hasPermission("managerapi.staff.vanish")) {
                                        String current = sortedStaff.get(user.getString("server"));
                                        current += "\n      " + ChatColor.translateAlternateColorCodes('&', user.getString("staffDisplayName") + ChatColor.GRAY + " (Vanished)");
                                        sortedStaff.replace(user.getString("server"), current);
                                    }

                                }
                                else{
                                    String current = sortedStaff.get(user.getString("server"));
                                    current += "\n      "+ ChatColor.translateAlternateColorCodes('&',user.getString("staffDisplayName"));
                                    sortedStaff.replace(user.getString("server"), current);
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    for(String key: sortedStaff.keySet()){

                        resp += "\n    &r&e" + key;
                        resp += sortedStaff.get(key);
                        resp += "\n  ";
                    }
                }
                else{
                    resp += "\n    " + ChatColor.RED + "There are no staff online!";
                }
            }catch (NullPointerException e){
                resp += "\n    " + ChatColor.RED + "There are no staff online!";
            }

            if(resp.length() == initLen){
                resp += "\n    " + ChatColor.RED + "There are no staff online!";
            }

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', resp));

        }


        return false;
    }
}
