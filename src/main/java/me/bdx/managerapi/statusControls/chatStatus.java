package me.bdx.managerapi.statusControls;
import me.bdx.managerapi.config.managerapiconfig;

public class chatStatus {

    private static boolean globalChatStatus;
    private static boolean staffChatStatus;
    private static boolean devChatStatus;
    private static boolean incomingChatStatus;
    private static boolean outgoingChatStatus;

    /**
    Sets all chat status values to true
     **/
    public static void setTrue(){

        globalChatStatus = true;
        staffChatStatus = true;
        devChatStatus = true;
        incomingChatStatus = true;
        outgoingChatStatus = true;

    }

    /**
     Sets all chat status values to false
     **/
    public static void setFalse(){

        globalChatStatus = false;
        staffChatStatus = false;
        devChatStatus = false;
        incomingChatStatus = false;
        outgoingChatStatus = false;

    }

    /**
     * Loads the status values from the config file
     */
    public static void loadFromConfig(){
        globalChatStatus = managerapiconfig.get().getBoolean("globalChatStatus");
        staffChatStatus = managerapiconfig.get().getBoolean("staffChatStatus");
        devChatStatus = managerapiconfig.get().getBoolean("devChatStatus");
        incomingChatStatus = managerapiconfig.get().getBoolean("incomingChatStatus");
        outgoingChatStatus = managerapiconfig.get().getBoolean("outgoingChatStatus");
    }

    /**
     * Sets the status of global chat. When false all chats are disabled EXCEPT for those with managerapi.chat.bypass
     * @param status boolean
     */
    public static void setGlobalChat(boolean status){
        globalChatStatus = status;
        incomingChatStatus = status;
        outgoingChatStatus = status;
    }

    /**
     * Sets that status of /sc.
     * @param status boolean
     */
    public static void setStaffChat(boolean status){
        staffChatStatus = status;
    }

    /**
     * Sets the status of /dc
     * @param status boolean
     */
    public static void setDevChat(boolean status){
        devChatStatus = status;
    }

    /**
     * sets the status of all incoming chat messages for globalChat
     * @param status boolean
     */
    public static void setIncoming(boolean status){
        incomingChatStatus = status;
    }

    /**
     * sets the status of all outgoing messages for globalChat
     * @param status boolean
     */
    public static void setOutgoing(boolean status){
        outgoingChatStatus = status;
    }

    /**
     * Returns the current globalChat status
     * @return boolean
     */
    public static boolean getGlobalStatus(){
        return globalChatStatus;
    }

    /**
     *
     * @return boolean staffChatStatus
     */
    public static boolean getStaffChatStatus(){
        return staffChatStatus;
    }

    /**
     * Returns the status of devChat
     * @return boolean
     */
    public static boolean getDevChatStatus(){
        return devChatStatus;
    }

    /**
     * Returns the status of incoming globalChats
     * @return boolean
     */
    public static boolean getIncomingChatStatus(){
        return incomingChatStatus;
    }

    /**
     * Returns the status of outgoing globalChats
     * @return boolean
     */
    public static boolean getOutgoingChatStatus(){
        return outgoingChatStatus;
    }

}
