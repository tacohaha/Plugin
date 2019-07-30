package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerClanCommands implements CommandExecutor {

    public PlayerClanCommands(JavaPlugin plugin) {
        plugin.getServer().broadcastMessage("info");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ((Player) sender instanceof Player) {
            if (args[0].equalsIgnoreCase("Create")) {
                new CreateClan(args[1], (Player) sender);
                return true;
            } else if (args[0].equalsIgnoreCase("Delete")) {
                new DeleteClan((Player) sender);
                return true;
            } else if (args[0].equalsIgnoreCase("Invite")) {
                new InvitePlayer(args[1], (Player) sender);
                return true;
            } else if (args[0].equalsIgnoreCase("Leave")) {
                new LeaveClan((Player) sender);
                return true;
            } else if (args[0].equalsIgnoreCase("Accept")) {
                new AcceptInvite((Player) sender);
                return true;
            } else if (args[0].equalsIgnoreCase("Deny")) {
                new DenyInvite((Player) sender);
                return true;
            } else if (args[0].equalsIgnoreCase("List")) {
                new ClanList((Player) sender);
                return true;
            } else if (args[0].equalsIgnoreCase("AddSlots")) {
                new AddSlots((Player) sender);
                return true;
            } else if (args[0].equalsIgnoreCase("Info")) {
                try {
                    if (args[1] != null) {
                        new ClanInfo((Player) sender, args[1]);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    new ClanInfo((Player) sender);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("Kick")) {
                new KickPlayer((Player) sender, args[1]);
                return true;
            } else if (args[0].equalsIgnoreCase("setprefix")) {
                new SetPrefix((Player) sender, args[1]);
                return true;
            }
            return false;
        } else {
            sender.sendMessage("You must be a player to use this command!");
            return true;
        }
    }
}