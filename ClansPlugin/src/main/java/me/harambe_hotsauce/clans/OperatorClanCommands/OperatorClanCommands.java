package me.harambe_hotsauce.clans.OperatorClanCommands;

import me.harambe_hotsauce.clans.PlayerClanCommands.SetPrefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class OperatorClanCommands implements CommandExecutor, TabCompleter {

    private static final List<String> COMMANDS = Arrays.asList("setprefix", "disband", "removemember", "changeleader", "setmemberlimit");
    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length ==  1) {
            return (args.length == 1) ? StringUtil.copyPartialMatches(args[0], COMMANDS, new ArrayList<>()) : null;
        } else {
            return null;
        }
    }
}
