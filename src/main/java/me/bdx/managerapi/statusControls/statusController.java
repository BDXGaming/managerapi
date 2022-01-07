package me.bdx.managerapi.statusControls;

import me.bdx.managerapi.config.managerapiconfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class statusController {
    public boolean autoOp;
    public boolean deopOnJoin;
    public boolean showAltChannels;
    public String chatChannel;
    public String server;
    public String DisconnectMessage;
    public boolean globalPlayerList;
    public String apiToken;

    /**
     * Creates a new status controller for Managerapi
     */
    public statusController(){
        FileConfiguration configuration = managerapiconfig.get();
        this.autoOp = configuration.getBoolean("autoOP");
        this.deopOnJoin = configuration.getBoolean("deopOnJoin");
        this.showAltChannels = configuration.getBoolean("showAltChannels");
        this.chatChannel = configuration.getString("chatChannel");
        this.globalPlayerList = configuration.getBoolean("globalPlayerList");
        this.server = managerapiconfig.get().getString("server-name");
        this.DisconnectMessage = configuration.getString("disconnect-message");
        this.apiToken = configuration.getString("api-token");
    }

    /**
     * Reloads the values from the config, allows for config values to be changed during runtime
     */
    public void reload(){
        FileConfiguration configuration = managerapiconfig.get();
        this.autoOp = configuration.getBoolean("autoOP");
        this.deopOnJoin = configuration.getBoolean("deopOnJoin");
        this.showAltChannels = configuration.getBoolean("showAltChannels");
        this.chatChannel = configuration.getString("chatChannel");
        this.globalPlayerList = configuration.getBoolean("globalPlayerList");
        this.server = configuration.getString("server-name");
        this.DisconnectMessage = configuration.getString("disconnect-message");
        this.apiToken = configuration.getString("api-token");
    }

    /**
     * This function returns the current autoOp status
     * @return boolean autoOP; the autoOp status
     */
    public boolean getAutoOp(){
        return this.autoOp;
    }

    /**
     * This function returns the current deopOnJoin status
     * @return boolean deopOnJoin; the deopOnJoin status
     */
    public boolean getDeopOnJoin(){
        return this.deopOnJoin;
    }

}
