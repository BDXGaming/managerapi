package me.bdx.managerapi;

import me.bdx.managerapi.bungee.handler.PlayerJoinEvent;
import me.bdx.managerapi.bungee.handler.PlayerServerSwitchEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

public class ManagerapiBungee extends Plugin {

    private static ManagerapiBungee managerapiBungee;

    @Override
    public void onEnable() {
        //Sets the instance of the Plugin
        managerapiBungee = this;


        //Registers the listeners
        getProxy().getPluginManager().registerListener(this, new PlayerJoinEvent());
        getProxy().getPluginManager().registerListener(this, new PlayerServerSwitchEvent());

        //Log in console that plugin is online
        getProxy().getConsole().sendMessage(new TextComponent("[Managerapi]: "+ ChatColor.GREEN + "Plugin has loaded!"));
    }

    @Override
    public void onDisable() {

        getProxy().getConsole().sendMessage(new TextComponent("[Managerapi]: "+ChatColor.RED + "Plugin has been disabled!"));
    }

    /**
     * Gets the instance of the ManagerapiBungee class
     * @return ManagerapiBungee instance
     */
    public static ManagerapiBungee getInstance(){

        if(managerapiBungee == null){
            return null;
        }
        return managerapiBungee;
    }
}
