package me.bdx.managerapi.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionbarHelper {

    /**
     * Sends the given text as an actionbar to the specified player
     * @param text String
     * @param p Player
     */
    public void sendActionBar(String text, Player p){
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(text));
    }
}
