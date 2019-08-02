package me.harambe_hotsauce.clans.OperatorClanCommands;

import me.harambe_hotsauce.clans.PlayerClanCommands.PlayerPermission;
import me.harambe_hotsauce.clans.PlayerClanCommands.RemoveFromClanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class DisbandClan {

    private File file = new File(getFilePath());
    private YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    DisbandClan(Player player, String clan) {
        if (checkIfClanExists(clan)) {
            ArrayList<String> memberList = getMemberList(clan);
            for (int i = 0; i < memberList.toArray().length; i++) {
                yamlConfiguration.set("players." + memberList.toArray()[i] + ".Player_Permissions", PlayerPermission.NULL.toString());
                yamlConfiguration.set("players." + memberList.toArray()[i] + ".clan", null);
                save();
                try {
                    Player p = Bukkit.getPlayer((String) memberList.toArray()[i]);
                    p.sendMessage(ChatColor.DARK_RED + "Your clan has been disbanded by " + ChatColor.AQUA + player.getName());
                    player.sendMessage(ChatColor.AQUA + clan + ChatColor.RED + " has been disbanded!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Tried to message a player that isn't logged in");
                }
            }
            yamlConfiguration.set("clans." + clan, null);
            save();
            new RemoveFromClanList(clan);
            player.sendMessage(ChatColor.DARK_RED + "You have disbanded " + ChatColor.AQUA + clan);
        } else {
            player.sendMessage(ChatColor.RED + "Clan does not exist!");
        }
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getMemberList(String clan) {
        return (ArrayList<String>) yamlConfiguration.getStringList("clans." + clan + ".members");
    }

    public boolean checkIfClanExists(String clan) {
        return yamlConfiguration.get("clans." + clan) != null;
    }
}