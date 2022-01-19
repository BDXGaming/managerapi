package me.bdx.managerapi;

import com.earth2me.essentials.Essentials;
import com.neovisionaries.ws.client.WebSocketException;
import me.bdx.managerapi.api.ChatApi;
import me.bdx.managerapi.chatManagers.ChatSender;
import me.bdx.managerapi.chatManagers.channelListeners;
import me.bdx.managerapi.commands.*;
import me.bdx.managerapi.config.Managerapiconfig;
import me.bdx.managerapi.globalData.GlobalPlayers;
import me.bdx.managerapi.globalData.GlobalServers;
import me.bdx.managerapi.statusControls.StatusController;
import me.bdx.managerapi.events.ChatEventHandler;
import me.bdx.managerapi.events.CommandEventHandler;
import me.bdx.managerapi.events.JoinEventHandler;
import me.bdx.managerapi.events.LeaveEventHandler;
import me.bdx.managerapi.handlers.GlobalCommandHandler;
import me.bdx.managerapi.statusControls.ChatStatus;
import me.bdx.managerapi.tabcomplete.GlobalchatStatusTabComplete;
import me.bdx.managerapi.utils.ActionbarHelper;
import me.bdx.managerapi.utils.PlayerHelper;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
    public static GlobalCommandHandler commandHandler;
    public static StatusController statusController;
    public static channelListeners channelListeners;
    public static GlobalPlayers globalPlayers;
    public static GlobalServers globalServers;
    public static me.bdx.managerapi.GlobalChat GlobalChat;
    public static me.bdx.managerapi.utils.PlayerHelper PlayerHelper;
    public static ActionbarHelper actionbarHelper;
    private static ChatApi chatapi;

    @Override
    public void onEnable() {

        //Hooks into the essentials plugin
        Plugin essentialsPlugin = Bukkit.getPluginManager().getPlugin("Essentials");

        managerapi = this;
        commandHandler = new GlobalCommandHandler();

        //Sets up the config files for the plugin
        Managerapiconfig.setup();

        //Loads the chatStatus values from the config
        ChatStatus.loadFromConfig();

        statusController = new StatusController();
        channelListeners = new channelListeners();
        globalPlayers = new GlobalPlayers();
        globalServers = new GlobalServers();
        GlobalChat = new GlobalChat();
        PlayerHelper = new PlayerHelper();
        actionbarHelper = new ActionbarHelper();
        chatapi = new ChatApi();

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
            ChatApi.createSocketConnection();
        } catch (IOException | WebSocketException | JSONException e) {
            e.printStackTrace();
        }

        if(statusController.globalPlayerList){
            for(Player p: Bukkit.getOnlinePlayers()){
                try {
                    ChatApi.addPlayer(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                ChatApi.syncPlayerLists();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //Registers commands
        getCommand("chatcommand").setExecutor(new Globalchatcommand());
        getCommand("managerapireload").setExecutor(new ReloadCommand());
        getCommand("staff").setExecutor(new GlobalStaffCommand());
        getCommand("playerinfo").setExecutor(new PlayerInfoCommand());
        getCommand("gchat").setExecutor(new GlobalChatStatusCommand());
        getCommand("globalcommand").setExecutor(new GlobalCommand());
        getCommand("togglealtchannels").setExecutor(new ToggleChannels());
        getCommand("viewOnline").setExecutor(new ViewOnlinePlayers());

        //assigns the tab complete classes
        getCommand("gchat").setTabCompleter(new GlobalchatStatusTabComplete());

        //Registers Listeners
        getServer().getPluginManager().registerEvents(new ChatEventHandler(), this);
        getServer().getPluginManager().registerEvents(new JoinEventHandler(), this);
        getServer().getPluginManager().registerEvents(new LeaveEventHandler(), this);
        getServer().getPluginManager().registerEvents(new CommandEventHandler(),this);

        //Prints to console that the plugin is online
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[ManagerApi]: The Plugin is online");

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic

        if(statusController.globalPlayerList){
            for(Player p: Bukkit.getOnlinePlayers()){
                try {
                    ChatApi.removePlayer(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        //Closes the API connection
        try {
            ChatApi.closeConn();
        } catch (JSONException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("[ManagerAPI]: "+ChatColor.RED + "Websocket disconnect failed!");
        }
        Bukkit.getServer().getConsoleSender().sendMessage("[ManagerAPI]: Connection Terminated!");

    }

    public static Chat getChat() {
        return chat;
    }
    public Chat getChatInstance(){ return chat;}
    public Permission getPermission(){return  permission;}
    public StatusController getStatusController(){return statusController;}
    public Essentials getEssentials(){
        return essentials;
    }
    public channelListeners getChannelListeners(){return channelListeners;}
    public GlobalPlayers getGlobalPlayers(){
        return globalPlayers;
    }
    public GlobalServers getGlobalServers(){
        return globalServers;
    }
    public me.bdx.managerapi.GlobalChat getGlobalChat(){
        return GlobalChat;
    }
    public me.bdx.managerapi.utils.PlayerHelper getPlayerHelper(){
        return PlayerHelper;
    }
    public ActionbarHelper getActionbarHelper(){
        return actionbarHelper;
    }
    public ChatApi getChatapi(){ return chatapi; }

}
