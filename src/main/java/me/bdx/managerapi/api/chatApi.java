package me.bdx.managerapi.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.neovisionaries.ws.client.*;
import me.bdx.managerapi.Managerapi;
import me.bdx.managerapi.config.managerapiconfig;
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
     * The entry point of this command line application.
     */
    public static void sendMsg(Player sender, String msg, String type, String label) throws Exception
    {


        String jsonMsg = new JSONObject()
                .put("playerDisplayName", sender.getDisplayName())
                .put("playerRealName", sender.getName())
                .put("playerUUID", sender.getUniqueId().toString())
                .put("server-name", managerapiconfig.get().getString("server-name"))
                .put("chat-label", label)
                .put("type", type)
                .put("content", msg)
                .toString();


        ws.sendText(jsonMsg);

    }

    public static void sendMsg(Player sender, String msg, String type, String label, String chatcolor) throws Exception
    {


        String jsonMsg = new JSONObject()
                .put("playerDisplayName", sender.getDisplayName())
                .put("playerRealName", sender.getName())
                .put("playerUUID", sender.getUniqueId().toString())
                .put("server-name", managerapiconfig.get().getString("server-name"))
                .put("chat-label", label)
                .put("type", type)
                .put("content", msg)
                .put("chatColor", chatcolor)
                .toString();


        ws.sendText(jsonMsg);

    }

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
                .toString();


        ws.sendText(jsonMsg);

    }

    public static void requestStaff() throws JSONException {
        String jsonMsg = new JSONObject()
                .put("type", "getStaff")
                .toString();

        ws.sendText(jsonMsg);

    }

    public static void removeStaff(Player staff) throws JSONException {

        String jsonMsg = new JSONObject()
                .put("staffRealName", staff.getName())
                .put("server", managerapiconfig.get().getString("server-name"))
                .put("type", "removeStaff")
                .toString();

        ws.sendText(jsonMsg);

    }


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

    public static void createSocketConnection() throws IOException, WebSocketException, JSONException {

        ws = connect();

        // A text read from the standard input.
        String jsonString = new JSONObject()
                .put("uuid", "0b32abc9-d5e1-11eb-aaa4-000272a242dc")
                .toString();

        ws.sendText(jsonString);

    }

    public static void closeConn(){
        ws.sendClose();
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
