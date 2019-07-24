package me.harambe_hotsauce.clans;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ClanCommand implements CommandExecutor {

    public ClanCommand(JavaPlugin plugin) {
        plugin.getServer().broadcastMessage("info");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equalsIgnoreCase("create")) {
            System.out.println(args[0]);
            return true;
        } else if (args[0].equalsIgnoreCase("delete")) {
            System.out.println(args[0]);
            return true;
        } else if (args[0].equalsIgnoreCase("join")) {
            System.out.println(args[0]);
            return true;
        }
        return false;
    }

}
