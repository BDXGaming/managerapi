package me.bdx.managerapi.commands;

import com.neovisionaries.ws.client.WebSocketException;
import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.api.ChatApi;
import me.bdx.managerapi.config.Managerapiconfig;
import me.bdx.managerapi.statusControls.ChatStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONException;

import java.io.IOException;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.hasPermission("managerapi.reload")){

            Bukkit.broadcast(ChatColor.RED + "[ManagerAPI]: "+ChatColor.YELLOW+"Plugin is reloading", "managerapi.reload");
            //Removes all players who are on the server from the online player list
            for(Player p: Bukkit.getOnlinePlayers()){
                try {
                    ChatApi.removePlayer(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //Terminates the websocket connection (if exists)
            try {
                ChatApi.closeConn();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Sets up the config files for the plugin
            Managerapiconfig.setup();

            //Reloads the stored values for the chatStatus trackers
            ChatStatus.loadFromConfig();

            //Reloads the stored values for general statusController values
            Managerapi.statusController.reload();

            Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(Managerapi.class), new Runnable() {
                public void run() {
                    //Creates a new socket connection
                    try {
                        ChatApi.createSocketConnection();
                    } catch (IOException | WebSocketException | JSONException e) {
                        e.printStackTrace();
                    }

                    //Adds all online players to the online player list
                    for(Player p: Bukkit.getOnlinePlayers()){
                        try {
                            ChatApi.addPlayer(p);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //Ensures that playerlists are synced across all connected servers
                    try {
                        ChatApi.syncPlayerLists();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Bukkit.broadcast(ChatColor.RED + "[ManagerAPI]:"+ChatColor.GREEN+" Reload Complete", "managerapi.reload");

                }
            }, 25L);

            return true;

        }else{
            sender.sendMessage(ChatColor.RED + "You do not have the required permissions to run this command!");
        }


        return true;
    }
}
