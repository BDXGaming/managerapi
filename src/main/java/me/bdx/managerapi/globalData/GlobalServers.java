package me.bdx.managerapi.globalData;

import java.util.ArrayList;

public class GlobalServers {
    private ArrayList<String> servers;

    public GlobalServers(){
        this.servers = new ArrayList<>();
    }

    public GlobalServers(ArrayList<String> servers){
        this.servers = servers;
    }


    public void updateGlobalServers(ArrayList<String> servers){
        this.servers = servers;
    }

    public ArrayList<String> getOnlineServers(){
        return this.servers;
    }

}
