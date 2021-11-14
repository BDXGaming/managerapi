package me.bdx.managerapi.statusControls;

import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.config.managerapiconfig;

public class statusController {
    public boolean autoOp;
    public boolean deopOnJoin;
    public boolean showAltChannels;
    public String chatChannel;

    /**
     * Creates a new status controller for Managerapi
     */
    public statusController(){
        this.autoOp = managerapiconfig.get().getBoolean("autoOP");
        this.deopOnJoin = managerapiconfig.get().getBoolean("deopOnJoin");
        this.showAltChannels = managerapiconfig.get().getBoolean("showAltChannels");
        this.chatChannel = managerapiconfig.get().getString("chatChannel");
    }

    /**
     * Reloads the values from the config, allows for config values to be changed during runtime
     */
    public void reload(){
        this.autoOp = managerapiconfig.get().getBoolean("autoOp");
        this.deopOnJoin = managerapiconfig.get().getBoolean("deopOnJoin");
        this.showAltChannels = managerapiconfig.get().getBoolean("showAltChannels");
        this.chatChannel = managerapiconfig.get().getString("chatChannel");
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
