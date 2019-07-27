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
        if (args[0].equalsIgnoreCase("Create") | args[0].equalsIgnoreCase("create")) {
            new CreateClan(args[1], (Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("Delete") |  args[0].equalsIgnoreCase("delete")) {
            new DeleteClan((Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("Invite") | args[0].equalsIgnoreCase("invite")) {
            new InvitePlayer(args[1], (Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("Leave") | args[0].equalsIgnoreCase("leave")) {
            new LeaveClan((Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("Accept") | args[0].equalsIgnoreCase("accept")) {
            new AcceptInvite((Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("Deny") | args[0].equalsIgnoreCase("deny")) {
            new DenyInvite((Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("List") | args[0].equalsIgnoreCase("list")) {
            new ClanList((Player) sender);
            return true;
        } else if (args[0].equalsIgnoreCase("AddSlots") | args[0].equalsIgnoreCase("addSlots") | args[0].equalsIgnoreCase("addslots") | args[0].equalsIgnoreCase("Addslots")) {
            new AddSlots((Player) sender);
            return true;
        }
        return false;
    }

}
