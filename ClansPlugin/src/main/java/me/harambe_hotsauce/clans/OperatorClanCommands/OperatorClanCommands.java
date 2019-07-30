package me.harambe_hotsauce.clans.OperatorClanCommands;

import me.harambe_hotsauce.clans.PlayerClanCommands.SetPrefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OperatorClanCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equalsIgnoreCase("setprefix")) {
            new SetClanPrefix((Player) sender, args[1], args[2]);
            return true;
        } else if (args[0].equalsIgnoreCase("disband")) {
            new DisbandClan((Player) sender, args[1]);
            return true;
        } else if (args[0].equalsIgnoreCase("removemember")) {
            new RemoveClanMember((Player) sender, args[1], args[2]);
            return true;
        } else if (args[0].equalsIgnoreCase("changeleader")) {
            new ChangeLeader((Player) sender, args[1], args[2]);
            return true;
        } else if (args[0].equalsIgnoreCase("setmemberlimit")) {
            new SetMemberLimit((Player) sender, args[1], args[2]);
            return true;
        } else {
            return false;
        }
    }
}
