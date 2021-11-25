package me.bdx.managerapi.utils;

import me.bdx.managerapi.Managerapi;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class playerHelper {

    /**
     * Gets the essentials/vault current displayname
     * @param p Player
     * @return String
     */
    public static String getPlayerDisplayName(Player p){
        return ChatColor.translateAlternateColorCodes('&',  Managerapi.essentials.getUser(p).getNick());
    }

    /**
     * Gets the essentials/vault current displayname
     * @param p OfflinePlayer
     * @return String
     */
    public static String getPlayerDisplayName(OfflinePlayer p){
        String name = p.getName();
        return Managerapi.essentials.getUser(name).getDisplayName();
    }

    /**
     * Gets the essentials/vault current displayname
     * @param playerName String
     * @return String
     */
    public static String getPlayerDisplayName(String playerName){
        return Managerapi.essentials.getUser(playerName).getDisplayName();
    }
}
