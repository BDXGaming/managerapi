package me.bdx.managerapi.api;

import java.io.IOException;

import com.neovisionaries.ws.client.*;
import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.config.Managerapiconfig;
import me.bdx.managerapi.customEvents.CustomPacketSendEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatApi {

    /**
     * The echo server on websocket.org.
     */
    private static final String SERVER = Managerapiconfig.get().getString("api-address");

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
                .put("server-name", Managerapiconfig.get().getString("server-name"))
                .put("chat-label", label)
                .put("type", type)
                .put("content", msg)
                .put("channel", Managerapi.statusController.chatChannel)
                .toString();


        Managerapi.managerapi.getChatapi().send(jsonMsg);

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
                .put("server-name", Managerapiconfig.get().getString("server-name"))
                .put("chat-label", label)
                .put("type", type)
                .put("content", msg)
                .put("chatColor", chatcolor)
                .put("channel", Managerapi.statusController.chatChannel)
                .toString();


        Managerapi.managerapi.getChatapi().send(jsonMsg);

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
                .put("server-name", Managerapiconfig.get().getString("server-name"))
                .put("chat-label", label)
                .put("type", type)
                .put("content", msg)
                .put("chatColor", chatcolor)
                .put("channel", Managerapi.statusController.chatChannel)
                .toString();

        Managerapi.managerapi.getChatapi().send(jsonMsg);
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
                .put("server-name", Managerapiconfig.get().getString("server-name"))
                .put("chat-label", label)
                .put("type", type)
                .put("content", msg)
                .put("chatColor", chatcolor)
                .put("channel", Managerapi.statusController.chatChannel)
                .toString();


        Managerapi.managerapi.getChatapi().send(jsonMsg);

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

        Managerapi.managerapi.getChatapi().send(jsonMsg);
    }

    /**
     * Websocket implementation to send custom packets
     * @param packet JSONObject
     */
    public void sendCustomPacket(JSONObject packet)  {

        String msg = null;
        try {
            msg = new JSONObject()
                    .put("type", "customPacket")
                    .put("custompacket", packet)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CustomPacketSendEvent event = new CustomPacketSendEvent(msg,packet);
        Bukkit.getPluginManager().callEvent(event);

        if(!event.isCancelled()){
            send(msg);
        }


    }
    /**
     * Websocket implementation to send custom packets
     * @param packet String
     */
    public void sendCustomPacket(String packet) {

        String msg = null;
        try {
            msg = new JSONObject()
                    .put("type", "customPacket-string")
                    .put("custompacket", packet)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CustomPacketSendEvent event = new CustomPacketSendEvent(msg, packet);
        Bukkit.getPluginManager().callEvent(event);

        if(!event.isCancelled()){
            send(msg);
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

        Managerapi.managerapi.getChatapi().send(jsonMsg);

    }

    /**
     * Removes a member from the stafflist
     * @param staff Player
     * @throws JSONException For JSON formatting issues
     */
    public static void removeStaff(Player staff) throws JSONException {

        String jsonMsg = new JSONObject()
                .put("staffRealName", staff.getName())
                .put("server", Managerapiconfig.get().getString("server-name"))
                .put("type", "removeStaff")
                .toString();

        Managerapi.managerapi.getChatapi().send(jsonMsg);

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
                .put("server", Managerapiconfig.get().getString("server-name"))
                .put("vanishState", Managerapi.essentials.getUser(staff.getUniqueId()).isHidden())
                .put("type", "addStaff")
                .toString();

        Managerapi.managerapi.getChatapi().send(jsonMsg);

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
                .put("server", Managerapiconfig.get().getString("server-name"))
                .put("vanishState", vanishState)
                .put("type", "addStaff")
                .toString();

        Managerapi.managerapi.getChatapi().send(jsonMsg);

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

        Managerapi.managerapi.getChatapi().send(jsonMsg);
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

        Managerapi.managerapi.getChatapi().send(jsonMsg);
    }

    /**
     * Requests an updated version of the global playerlist
     * @throws JSONException For JSON formatting issues
     */
    public static void getPlayerList() throws JSONException {
        String jsonMsg = new JSONObject()
                .put("type", "getPlayerList")
                .toString();
        Managerapi.managerapi.getChatapi().send(jsonMsg);
    }

    /**
     * Requests an updated version of the onlinePlayerServers
     * @throws JSONException For JSON formatting issues
     */
    public static void getonlinePlayerServers() throws JSONException {
        String jsonMsg = new JSONObject()
                .put("type", "getPlayerServers")
                .toString();
        Managerapi.managerapi.getChatapi().send(jsonMsg);
    }

    /**
     * Sends a Sync request to the API to sync all playerlists and onlinePlayerServers
     * @throws JSONException For JSON formatting issues
     */
    public static void syncPlayerLists() throws JSONException {
        String jsonMsg = new JSONObject()
                .put("type", "syncPlayerList")
                .toString();
        Managerapi.managerapi.getChatapi().send(jsonMsg);
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

        Managerapi.managerapi.getChatapi().send(jsonMsg);
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

        Managerapi.managerapi.getChatapi().send(jsonMsg);
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

        Managerapi.managerapi.getChatapi().send(jsonMsg);
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

        Managerapi.managerapi.getChatapi().send(jsonMsg);
    }

    /**
     * Much like the Bukkit.broadcast() method, this allows for broadcasting to all servers on the given channel
     * This method uses the channel to which the current plugin instance is listening
     * @param msg String
     */
    public static void channelBroadcast(String msg){
        JSONObject jsonMsg = null;
        try {
            jsonMsg = new JSONObject()
                    .put("type", "broadcast-channel")
                    .put("channel", Managerapi.statusController.chatChannel)
                    .put("message", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assert jsonMsg != null;
        Managerapi.managerapi.getChatapi().send(jsonMsg);
    }

    /**
     * Creates the connection to the websocket server
     * @return Websocket The instance of the websocket connection
     * @throws IOException Thrown during a connection error
     * @throws WebSocketException Thrown during connection error
     */
    private static WebSocket connect() throws IOException, WebSocketException
    {
        return new WebSocketFactory()
                .setConnectionTimeout(TIMEOUT)
                .createSocket(SERVER)
                .addListener(new WebSocketAdapter() {
                    //Sends all incoming TextMessages to the apiResponseHandler
                    public void onTextMessage(WebSocket websocket, String message) throws JSONException {
                        ApiResponseHandler.handleResponse(message);
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

        //Connects to the websocket server and sends token for connection verification
        JSONObject jsonString = new JSONObject()
                .put("uuid", Managerapi.statusController.apiToken)
                .put("server", Managerapi.statusController.server);

        Managerapi.managerapi.getChatapi().send(jsonString);

    }

    /**
     * Closes the connection with the Websocket server
     * @throws JSONException Thrown if error occurs in creating the JSON message object
     */
    public static void closeConn() throws JSONException {

        String jsonMsg = new JSONObject()
                .put("type", Managerapi.statusController.DisconnectMessage)
                .toString();

        Managerapi.managerapi.getChatapi().send(jsonMsg);
        ws.disconnect();
        ws.sendClose();
    }

    /**
     * Sends the given json object to the websocket server
     * Not recommended for use as handler may not be able to interpret fully custom packets
     * @param json JSONObject
     */
    public void send(JSONObject json){
        ws.sendText(json.toString());
    }

    /**
     * Sends the given jsonString object to the websocket server
     * Not recommended for use as handler may not be able to interpret fully custom packets
     * @param jsonString String
     */
    public void send(String jsonString){
        ws.sendText(jsonString);
    }

}
