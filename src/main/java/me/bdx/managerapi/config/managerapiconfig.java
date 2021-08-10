package me.bdx.managerapi.config;

import me.bdx.managerapi.Managerapi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class managerapiconfig {

    private static File file;
    private static FileConfiguration customfile;

    //Finds or generates config file thing
    public static void setup(){

        Managerapi.managerapi.saveDefaultConfig();

        customfile = Managerapi.managerapi.getConfig();
    }

    public static FileConfiguration get(){
        return customfile;
    }

    public static void save(){
        try {
            customfile.save(file);
        }catch (IOException e){
            System.out.println("Could not save file");
        }
    }

    public static void reload(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("managerapi").getDataFolder(), "config.yml");
        customfile = YamlConfiguration.loadConfiguration(file);
    }

}
