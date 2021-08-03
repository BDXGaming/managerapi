package me.bdx.managerapi.config;

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
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("managerapi").getDataFolder(), "config.yml");

        if(!(file.exists())){
            try{
                file.createNewFile();
            }catch (IOException e){
                System.out.println(ChatColor.YELLOW + "[ManagerApi]: "+ e.toString());
            }
        }
        customfile = YamlConfiguration.loadConfiguration(file);
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
        customfile = YamlConfiguration.loadConfiguration(file);
    }

}
