package me.bdx.managerapi.utils;

import org.bukkit.ChatColor;

public class ChatColorHelper {

    public static ChatColor stringToColor(String chatcolor){
        ChatColor c;

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

        return c;
    }

    public static String ChatColorToString(ChatColor chatColor){
        String c;

        if(chatColor.equals(org.bukkit.ChatColor.DARK_RED)){
            c = "dred";
        }else if (chatColor.equals(org.bukkit.ChatColor.RED)){
            c = "lred";
        }else if(chatColor.equals(org.bukkit.ChatColor.AQUA)){
            c = "blue";
        }
        else if (chatColor.equals(org.bukkit.ChatColor.WHITE)){
            c = "white";
        }else{
            c = "grey";
        }

        return c;
    }
}
