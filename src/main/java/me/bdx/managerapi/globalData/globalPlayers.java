package me.bdx.managerapi.globalData;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class globalPlayers {

    private ArrayList<String> onlinePlayers;
    private HashMap<String, String> onlinePlayerServers;

    /**
     * Creates a new instance of the globalPlayers class
     * Intilizes a new Arraylist for the online players
     */
    public globalPlayers(){
        this.onlinePlayers = new ArrayList<String>();
        this.onlinePlayerServers = new HashMap<String, String>();
    }

    /**
     * Sets the onlinePlayers Arraylist to the arraylist provided
     * @param players Arraylist<String>
     */
    public void setOnlinePlayers(ArrayList<String> players){
        this.onlinePlayers = players;
    }

    /**
     * Gets the arraylist of onlinePlayers
     * This is the list of all online players
     * @return Arraylist<String>
     */
    public ArrayList<String> getOnlinePlayers() {
        return onlinePlayers;
    }

    /**
     * Gets the HashMap of all onlinePlayerServers
     * Data format HashMap<String username>, HashMap<String server, UUID uuid>
     * @return HashMap<String, HashMap<String, UUID>>
     */
    public HashMap<String, String> getOnlinePlayerServers() {
        return onlinePlayerServers;
    }

    /**
     * Sets the HashMap of onlinePlayerList to given value
     * @param dict HashMap<String, HashMap<String, UUID>>
     */
    public void setOnlinePlayerServers(HashMap<String, String> dict){
        this.onlinePlayerServers = dict;
    }

    /**
     * Returns the name of the server for the given player
     * @param p Player
     * @return String
     */
    public String getPlayerServer(Player p){
        return this.onlinePlayerServers.get(p.getName());
    }

    /**
     * Returns the name of the server for the given player
     * @param playerName String
     * @return String
     */
    public String getPlayerServer(String playerName){
        return this.onlinePlayerServers.get(playerName);
    }
}
