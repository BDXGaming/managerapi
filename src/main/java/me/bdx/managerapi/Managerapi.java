package me.bdx.managerapi;

import com.earth2me.essentials.Essentials;
import com.neovisionaries.ws.client.WebSocketException;
import me.bdx.managerapi.api.chatApi;
import me.bdx.managerapi.chat.ChatSender;
import me.bdx.managerapi.chat.channelListeners;
import me.bdx.managerapi.commands.*;
import me.bdx.managerapi.config.managerapiconfig;
import me.bdx.managerapi.globalData.globalPlayers;
import me.bdx.managerapi.statusControls.statusController;
import me.bdx.managerapi.events.chatEvent;
import me.bdx.managerapi.events.commandEvent;
import me.bdx.managerapi.events.joinEvent;
import me.bdx.managerapi.events.leaveEvent;
import me.bdx.managerapi.handlers.globalCommandHandler;
import me.bdx.managerapi.statusControls.chatStatus;
import me.bdx.managerapi.tabcomplete.globalchatStatusTabComplete;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONException;

import java.io.IOException;

public final class Managerapi extends JavaPlugin {

    public static Essentials essentials;
    public static Chat chat;
    public static Permission permission;
    public static Managerapi managerapi;
    public static ChatSender chatSender;
    public static globalCommandHandler commandHandler;
    public static statusController statusController;
    public static channelListeners channelListeners;
    public static globalPlayers globalPlayers;

    @Override
    public void onEnable() {

        //Hooks into the essentials plugin
        Plugin essentialsPlugin = Bukkit.getPluginManager().getPlugin("Essentials");

        managerapi = this;
        commandHandler = new globalCommandHandler();

        //Sets up the config files for the plugin
        managerapiconfig.setup();

        //Loads the chatStatus values from the config
        chatStatus.loadFromConfig();

        statusController = new statusController();
        channelListeners = new channelListeners();
        globalPlayers = new globalPlayers();

        if (essentialsPlugin.isEnabled() && (essentialsPlugin instanceof Essentials)) {
            essentials = (Essentials) essentialsPlugin;

        }
        else{
            // Disable the plugin
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[ManagerApi][ERROR]: Essentials not found!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        //Gets the Vault Chat dependency
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp != null) {
            chat = rsp.getProvider();
        }

        //Gets the Vault Permission dependency
        RegisteredServiceProvider<Permission> rsp2 = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp2 != null) {
            permission = rsp2.getProvider();
        }

        int[] coords = {0,0,0};

        //Creates the API connection for the chat system
        try {
            chatApi.createSocketConnection();
        } catch (IOException | WebSocketException | JSONException e) {
            e.printStackTrace();
        }

        //Registers commands
        getCommand("chatcommand").setExecutor(new globalchatcommand());
        getCommand("managerapireload").setExecutor(new reloadCommand());
        getCommand("staff").setExecutor(new globalStaffCommand());
        getCommand("playerinfo").setExecutor(new playerInfoCommand());
        getCommand("gchat").setExecutor(new globalChatStatusCommand());
        getCommand("globalcommand").setExecutor(new globalCommand());
        getCommand("togglealtchannels").setExecutor(new toggleChannels());
        getCommand("viewOnline").setExecutor(new viewOnlinePlayers());

        //assigns the tab complete classes
        getCommand("gchat").setTabCompleter(new globalchatStatusTabComplete());

        //Registers Listeners
        getServer().getPluginManager().registerEvents(new chatEvent(), this);
        getServer().getPluginManager().registerEvents(new joinEvent(), this);
        getServer().getPluginManager().registerEvents(new leaveEvent(), this);
        getServer().getPluginManager().registerEvents(new commandEvent(),this);

        //Prints to console that the plugin is online
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[ManagerApi]: The Plugin is online");

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //Closes the API connection
        chatApi.closeConn();
        Bukkit.getServer().getConsoleSender().sendMessage("[ManagerAPI]: Connection Terminated!");

    }


    public static Chat getChat() {
        return chat;
    }

}
