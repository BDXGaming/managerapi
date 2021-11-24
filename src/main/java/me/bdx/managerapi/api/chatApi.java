package me.bdx.managerapi.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.earth2me.essentials.User;
import com.neovisionaries.ws.client.*;
import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.config.managerapiconfig;
import me.bdx.managerapi.customEvents.customPacketSendEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.json.JSONException;
import org.json.JSONObject;


public class chatApi {

    /**
     * The echo server on websocket.org.
     */
    private static final String SERVER = managerapiconfig.get().getString("api-address");

    /**
     * The timeout value in milliseconds for socket connection.
     */
    private static final int TIMEOUT = 5000;
    private static WebSocket ws;

    /**
     * Sends a global chat message
     * @param sender Player
     * @param msg String
     * @param type String
     * @param label String
     * @throws Exception For JSON formatting issues
     */
    public static void sendMsg(Player sender, String msg, String type, String label) throws Exception
    {

        String jsonMsg = new JSONObject()
                .put("playerDisplayName", Managerapi.essentials.getUser(sender).getNick())
                .put("playerRealName", sender.getName())
                .put("playerUUID", sender.getUniqueId().toString())
                .put("server-name", managerapiconfig.get().getString("server-name"))
                .put("chat-label", label)
                .put("type", type)
                .put("content", msg)
                .put("channel", Managerapi.statusController.chatChannel)
                .toString();


        ws.sendText(jsonMsg);

    }

    /**
     * Sends a global chat message
     * @param sender Player
     * @param msg String
     * @param type String
     * @param label String
     * @param chatcolor String
     * @throws Exception For JSON formatting issues
     */
    public static void sendMsg(Player sender, String msg, String type, String label, String chatcolor) throws Exception
    {


        String jsonMsg = new JSONObject()
                .put("playerDisplayName", Managerapi.essentials.getUser(sender).getNick())
                .put("playerRealName", sender.getName())
                .put("playerUUID", sender.getUniqueId().toString())
                .put("server-name", managerapiconfig.get().getString("server-name"))
                .put("chat-label", label)
                .put("type", type)
                .put("content", msg)
                .put("chatColor", chatcolor)
                .put("channel", Managerapi.statusController.chatChannel)
                .toString();


        ws.sendText(jsonMsg);

    }

    /**
     * Sends a globalchat message using String sender
     * @param sender String
     * @param displayname String
     * @param msg String
     * @param type String
     * @param label String
     * @param chatcolor String, formatted to Managerapi string chatcolor standards
     * @throws Exception For JSON formatting issues
     */
    public static void sendCustomMsg(String sender, String displayname, String msg, String type, String label, String chatcolor) throws Exception{

        String jsonMsg = new JSONObject()
                .put("playerDisplayName", displayname)
                .put("playerRealName", sender)
                .put("playerUUID", sender)
                .put("server-name", managerapiconfig.get().getString("server-name"))
                .put("chat-label", label)
                .put("type", type)
                .put("content", msg)
                .put("chatColor", chatcolor)
                .put("channel", Managerapi.statusController.chatChannel)
                .toString();

        ws.sendText(jsonMsg);
    }

    /**
     * Send a globalchat or staffchat or devchat message
     * @param sender String
     * @param msg String
     * @param type String
     * @param label String
     * @param chatcolor String, follows Managerapi ChatColorHelper standards
     * @throws Exception For JSON formatting issues
     */
    public static void sendConMsg(String sender, String msg, String type, String label, String chatcolor) throws Exception
    {

        String jsonMsg = new JSONObject()
                .put("playerDisplayName", sender)
                .put("playerRealName", sender)
                .put("playerUUID", sender)
                .put("server-name", managerapiconfig.get().getString("server-name"))
                .put("chat-label", label)
                .put("type", type)
                .put("content", msg)
                .put("chatColor", chatcolor)
                .put("channel", Managerapi.statusController.chatChannel)
                .toString();


        ws.sendText(jsonMsg);

    }

    /**
     * @param command
     * This method invokes a command on ALL CONNECTED SERVERS
     */
    public static void sendGlobalCommand(String command, String sender) throws JSONException {
        String jsonMsg = new JSONObject()
                .put("command", command)
                .put("type", "globalCommand")
                .put("sender", sender)
                .toString();

        ws.sendText(jsonMsg);
    }

    /**
     * Websocket implementation to send custom packets
     * @param packet JSONObject
     * @throws JSONException e
     */
    public static void sendCustomPacket(JSONObject packet) throws JSONException {

        String msg = new JSONObject()
                .put("type", "customPacket")
                .put("custompacket", packet)
                .toString();

        customPacketSendEvent event = new customPacketSendEvent(msg,packet);
        Bukkit.getPluginManager().callEvent(event);

        if(!event.isCancelled()){
            ws.sendText(msg);
        }


    }
    /**
     * Websocket implementation to send custom packets
     * @param packet String
     * @throws JSONException e
     */
    public static void sendCustomPacket(String packet) throws JSONException {

        String msg = new JSONObject()
                .put("type", "customPacket-string")
                .put("custompacket", packet)
                .toString();

        customPacketSendEvent event = new customPacketSendEvent(msg, packet);
        Bukkit.getPluginManager().callEvent(event);

        if(!event.isCancelled()){
            ws.sendText(msg);
        }
    }

    /**
     * Requests an updated stafflist from the API
     * @throws JSONException For JSON formatting issues
     */
    public static void requestStaff() throws JSONException {
        String jsonMsg = new JSONObject()
                .put("type", "getStaff")
                .toString();

        ws.sendText(jsonMsg);

    }

    /**
     * Removes a member from the stafflist
     * @param staff Player
     * @throws JSONException For JSON formatting issues
     */
    public static void removeStaff(Player staff) throws JSONException {

        String jsonMsg = new JSONObject()
                .put("staffRealName", staff.getName())
                .put("server", managerapiconfig.get().getString("server-name"))
                .put("type", "removeStaff")
                .toString();

        ws.sendText(jsonMsg);

    }

    /**
     * Adds a player to the stafflist
     * @param staff Player
     * @throws JSONException For JSON formatting issues
     */
    public static void addStaff(Player staff) throws JSONException {

        String jsonMsg = new JSONObject()
                .put("staffDisplayName", Managerapi.chat.getPlayerPrefix(staff).toString() + staff.getName())
                .put("staffRealName", staff.getName())
                .put("server", managerapiconfig.get().getString("server-name"))
                .put("vanishState", Managerapi.essentials.getUser(staff.getUniqueId()).isHidden())
                .put("type", "addStaff")
                .toString();

        ws.sendText(jsonMsg);

    }

    /**
     * Updates a staff members status within the stafflist
     * Used to account for changes in vanish state
     * @param staff Player
     * @param vanishState boolean
     * @throws JSONException For JSON formatting issues
     */
    public static void updateStaff(Player staff, boolean vanishState) throws JSONException {

        String jsonMsg = new JSONObject()
                .put("staffDisplayName", Managerapi.chat.getPlayerPrefix(staff).toString() + staff.getName())
                .put("staffRealName", staff.getName())
                .put("server", managerapiconfig.get().getString("server-name"))
                .put("vanishState", vanishState)
                .put("type", "addStaff")
                .toString();

        ws.sendText(jsonMsg);

    }

    /**
     * Adds an online player to global playerlist
     * @param p Player
     * @throws JSONException For JSON formatting issues
     */
    public static void addPlayer(Player p) throws JSONException {
        String jsonMsg = new JSONObject()
                .put("server", Managerapi.statusController.server)
                .put("playerName", p.getName())
                .put("player-uuid", p.getUniqueId())
                .put("type", "addPlayer")
                .toString();

        ws.sendText(jsonMsg);
    }

    /**
     * Removes a Player from global playerlist
     * @param p Player
     * @throws JSONException For JSON formatting issues
     */
    public static void removePlayer(Player p) throws JSONException {
        String jsonMsg = new JSONObject()
                .put("server", Managerapi.statusController.server)
                .put("playerName", p.getName())
                .put("type", "removePlayer")
                .toString();

        ws.sendText(jsonMsg);
    }

    /**
     * Requests an updated version of the global playerlist
     * @throws JSONException For JSON formatting issues
     */
    public static void getPlayerList() throws JSONException {
        String jsonMsg = new JSONObject()
                .put("type", "getPlayerList")
                .toString();
        ws.sendText(jsonMsg);
    }

    /**
     * Requests an updated version of the onlinePlayerServers
     * @throws JSONException For JSON formatting issues
     */
    public static void getonlinePlayerServers() throws JSONException {
        String jsonMsg = new JSONObject()
                .put("type", "getPlayerServers")
                .toString();
        ws.sendText(jsonMsg);
    }

    /**
     * Sends a Sync request to the API to sync all playerlists and onlinePlayerServers
     * @throws JSONException For JSON formatting issues
     */
    public static void syncPlayerLists() throws JSONException {
        String jsonMsg = new JSONObject()
                .put("type", "syncPlayerList")
                .toString();
        ws.sendText(jsonMsg);
    }

    /**
     * Much like the Bukkit.broadcast() method, this allows for network-wide message broadcasting
     * @param msg String
     * @param permission String
     */
    public static void broadcast(String msg, String permission) {
        String jsonMsg = null;
        try {
            jsonMsg = new JSONObject()
                    .put("type", "broadcast-perm")
                    .put("message", msg)
                    .put("permission", permission)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ws.sendText(jsonMsg);
    }

    /**
     * Much like the Bukkit.broadcast() method, this allows for network-wide message broadcasting
     * @param msg String
     */
    public static void broadcast(String msg){
        String jsonMsg = null;
        try {
            jsonMsg = new JSONObject()
                    .put("type", "broadcast-all")
                    .put("message", msg)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ws.sendText(jsonMsg);
    }

    /**
     * Much like the Bukkit.broadcast() method, this allows for broadcasting to all servers on the given channel with the given permission
     * @param msg String
     */
    public static void channelBroadcast(String msg, String channel, String permission){
        String jsonMsg = null;
        try {
            jsonMsg = new JSONObject()
                    .put("type", "broadcast-channel-perm")
                    .put("channel", channel)
                    .put("permission", permission)
                    .put("message", msg)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ws.sendText(jsonMsg);
    }

    /**
     * Much like the Bukkit.broadcast() method, this allows for broadcasting to all servers on the given channel
     * @param msg String
     */
    public static void channelBroadcast(String msg, String channel){
        String jsonMsg = null;
        try {
            jsonMsg = new JSONObject()
                    .put("type", "broadcast-channel")
                    .put("channel", channel)
                    .put("message", msg)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ws.sendText(jsonMsg);
    }

    /**
     * Much like the Bukkit.broadcast() method, this allows for broadcasting to all servers on the given channel
     * This method uses the channel to which the current plugin instance is listening
     * @param msg String
     */
    public static void channelBroadcast(String msg){
        String jsonMsg = null;
        try {
            jsonMsg = new JSONObject()
                    .put("type", "broadcast-channel")
                    .put("channel", Managerapi.statusController.chatChannel)
                    .put("message", msg)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ws.sendText(jsonMsg);
    }


    /**
     * Connect to the server.
     */
    private static WebSocket connect() throws IOException, WebSocketException
    {
        return new WebSocketFactory()
                .setConnectionTimeout(TIMEOUT)
                .createSocket(SERVER)
                .addListener(new WebSocketAdapter() {
                    // A text message arrived from the server.
                    public void onTextMessage(WebSocket websocket, String message) throws JSONException {
                        apiResponseHandler.handleResponse(message);
                    }
                })
                .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                .connect();
    }

    /**
     * This method authorizes the connection to the API
     * @throws IOException For socket issues
     * @throws WebSocketException For Socket Issues
     * @throws JSONException For JSON formatting issues
     */
    public static void createSocketConnection() throws IOException, WebSocketException, JSONException {

        ws = connect();

        // A text read from the standard input.
        String jsonString = new JSONObject()
                .put("uuid", "0b32abc9-d5e1-11eb-aaa4-000272a242dc")
                .toString();

        ws.sendText(jsonString);

    }

    public static void closeConn() throws JSONException {

        String jsonMsg = new JSONObject()
                .put("type", Managerapi.statusController.DisconnectMessage)
                .toString();

        ws.sendText(jsonMsg);
        ws.disconnect();
    }


    /**
     * Wrap the standard input with BufferedReader.
     */
    private static BufferedReader getInput() throws IOException
    {
        return new BufferedReader(new InputStreamReader(System.in));
    }

}
