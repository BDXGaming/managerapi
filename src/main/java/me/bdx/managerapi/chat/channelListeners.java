package me.bdx.managerapi.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.UUID;

public class channelListeners {

    private ArrayList<UUID> listeners;

    public channelListeners(){
        this.listeners = new ArrayList<UUID>();
    }

    public void addPlayerListener(Player p){
        this.listeners.add(p.getUniqueId());
    }

    public void addPlayerListener(UUID uuid){
        this.listeners.add(uuid);
    }

    public void removeListener(Player p){
        this.listeners.remove(p.getUniqueId());
    }

    public void removeListeners(UUID uuid){
        this.listeners.remove(uuid);
    }

    public ArrayList<UUID> getListeners(){
        return this.listeners;
    }

    public boolean checkForListener(UUID uuid){
        return listeners.contains(uuid);
    }
}
