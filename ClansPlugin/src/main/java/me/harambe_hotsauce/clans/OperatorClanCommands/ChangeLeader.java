package me.harambe_hotsauce.clans.OperatorClanCommands;

import me.harambe_hotsauce.clans.PlayerClanCommands.PlayerPermission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class ChangeLeader {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public ChangeLeader(Player player, String clan, String playerName) {
        if (checkIfClanExists(clan)) {
            if (isPlayerInClan(clan, playerName)) {
                if (isPlayerLeader(clan, playerName)) {
                    player.sendMessage(ChatColor.AQUA + playerName + ChatColor.RED + " is already the leader!");
                } else {
                    updateOldLeader(clan, playerName);
                    updateNewLeader(clan, playerName);
                }
            } else {
                player.sendMessage(ChatColor.AQUA + playerName + ChatColor.RED + " is not in the clan " + ChatColor.AQUA + clan);
            }
        } else {
            player.sendMessage(ChatColor.RED + "Clan does not exist!");
        }
    }

    public void updateOldLeader(String clan, String playerName) {
        String oldLeader =  (String) yamlConfiguration.get("clans." + clan + ".leader");
        yamlConfiguration.set("clans." + clan + ".leader", null);
        yamlConfiguration.set("players." + oldLeader + ".Player_Permissions", PlayerPermission.MEMBER.toString());
        save();
        try {
            Player player = Bukkit.getPlayer(oldLeader);
            player.sendMessage(ChatColor.RED + "You have been replaced as leader!");
        } catch (NullPointerException e) {
            System.out.println("Tried to send a player a message that wasn't logged on!");
        }
    }

    public void updateNewLeader(String clan, String playerName) {
        yamlConfiguration.set("clans." + clan + ".leader", playerName);
        yamlConfiguration.set("players." + playerName  + ".Player_Permissions", PlayerPermission.LEADER.toString());
        save();
        try {
            Player player = Bukkit.getPlayer(playerName);
            player.sendMessage(ChatColor.GREEN + "You are now the new leader of " + ChatColor.AQUA + clan);
        } catch (NullPointerException e) {
            System.out.println("Tried to send a player a message that wasn't logged on!");
        }
    }

    public boolean checkIfClanExists(String clan) {
        if (yamlConfiguration.get("clans." + clan) == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isPlayerLeader(String clan, String playerName) {
        if (yamlConfiguration.get("clans." + clan + ".leader").equals(playerName)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPlayerInClan(String clan, String playerName) {
        ArrayList<String> memberList = (ArrayList<String>) yamlConfiguration.get("clans." + clan + ".members");
        if (memberList.contains(playerName)) {
            return true;
        } else {
            return false;
        }
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
