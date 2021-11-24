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
            for(Player p: Bukkit.getOnlinePlayers()){
                try {
                    chatApi.removePlayer(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                chatApi.closeConn();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            managerapiconfig.reload();
            chatStatus.loadFromConfig();
            Managerapi.statusController.reload();
            try {
                chatApi.createSocketConnection();
            } catch (IOException | WebSocketException | JSONException e) {
                e.printStackTrace();
            }

            for(Player p: Bukkit.getOnlinePlayers()){
                try {
                    chatApi.addPlayer(p);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                chatApi.syncPlayerLists();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Bukkit.broadcast(ChatColor.RED + "[ManagerAPI]:"+ChatColor.GREEN+" Reload Complete", "managerapi.reload");

            return true;

        }


        return false;
    }
}
