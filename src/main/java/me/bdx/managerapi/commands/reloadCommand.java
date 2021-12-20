package me.bdx.managerapi.commands;

import com.neovisionaries.ws.client.WebSocketException;
import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.api.chatApi;
import me.bdx.managerapi.config.managerapiconfig;
import me.bdx.managerapi.statusControls.chatStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONException;

import java.io.IOException;

public class reloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.hasPermission("managerapi.reload")){

            Bukkit.broadcast(ChatColor.RED + "[ManagerAPI]: "+ChatColor.YELLOW+"Plugin is reloading", "managerapi.reload");
            //Removes all players who are on the server from the online player list
            for(Player p: Bukkit.getOnlinePlayers()){
                try {
                    chatApi.removePlayer(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //Terminates the websocket connection (if exists)
            try {
                chatApi.closeConn();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Gets the updated instance of the Config File
            managerapiconfig.reload();
            //Reloads the stored values for the chatStatus trackers
            chatStatus.loadFromConfig();
            //Reloads the stored values for general statusController values
            Managerapi.statusController.reload();

            //Creates a new socket connection
            try {
                chatApi.createSocketConnection();
            } catch (IOException | WebSocketException | JSONException e) {
                e.printStackTrace();
            }

            //Adds all online players to the online player list
            for(Player p: Bukkit.getOnlinePlayers()){
                try {
                    chatApi.addPlayer(p);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //Ensures that playerlists are synced across all connected servers
            try {
                chatApi.syncPlayerLists();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Bukkit.broadcast(ChatColor.RED + "[ManagerAPI]:"+ChatColor.GREEN+" Reload Complete", "managerapi.reload");

            return true;

        }else{
            sender.sendMessage(ChatColor.RED + "You do not have the required permissions to run this command!");
        }


        return true;
    }
}
