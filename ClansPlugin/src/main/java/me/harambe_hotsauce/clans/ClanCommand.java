package me.harambe_hotsauce.clans;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class ClanCommand implements CommandExecutor {

    ArrayList playerList = new ArrayList();

    public ClanCommand(JavaPlugin plugin) {
        plugin.getServer().broadcastMessage("info");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equalsIgnoreCase("create")) {
            new CreateClan(args[1], (Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("delete")) {
            new DeleteClan((Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("invite")) {
            new InvitePlayer(args[1], (Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("leave")) {
            new LeaveClan((Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("accept")) {
            new AcceptInvite((Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("deny")) {
            new DenyInvite((Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("list")) {
            new ClanList((Player) sender);
            return true;
        }
        return false;
    }

}
