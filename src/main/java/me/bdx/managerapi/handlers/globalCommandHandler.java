package me.bdx.managerapi.handlers;

import me.bdx.managerapi.Managerapi;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class globalCommandHandler {
    public ConsoleCommandSender console;

    public globalCommandHandler() {
        console= Bukkit.getConsoleSender();
    }

    public void processCommand(String cmd){
        Bukkit.getScheduler().runTask(Managerapi.managerapi, () -> Bukkit.dispatchCommand(console,cmd));
    }
}
