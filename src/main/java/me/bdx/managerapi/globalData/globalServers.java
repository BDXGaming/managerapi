package me.bdx.managerapi.globalData;

import java.util.ArrayList;

public class globalServers {
    private ArrayList<String> servers;

    public globalServers(){
        this.servers = new ArrayList<>();
    }

    public globalServers(ArrayList<String> servers){
        this.servers = servers;
    }


    public void updateGlobalServers(ArrayList<String> servers){
        this.servers = servers;
    }

    public ArrayList<String> getOnlineServers(){
        return this.servers;
    }

}
