package me.bdx.managerapi.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class globalchatStatusTabComplete implements TabCompleter {

    private String[] subcommands = {"enable", "disable", "status"};
    private String[] options = {"all","incoming", "outgoing"};

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("gchat")){

            ArrayList<String> tab = new ArrayList<>();

            if(sender.hasPermission("managerapi.chat.disable")){

                if (args.length == 1) {
                    if (!args[0].equals("")) {
                        for (String scmd : subcommands) {
                            if (scmd.startsWith(args[0].toLowerCase())) {
                                tab.add(scmd);
                            }
                        }
                    } else {
                        tab.addAll(Arrays.asList(subcommands));
                    }
                    return tab;
                }

                if (args.length == 2) {

                    if ((args[0].equalsIgnoreCase("enable"))|| args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("status")){
                        for (String t : options) {
                            tab.add(t);
                        }
                    }
                    return tab;
                }

            }
            return tab;
        }

        return null;
    }
}
