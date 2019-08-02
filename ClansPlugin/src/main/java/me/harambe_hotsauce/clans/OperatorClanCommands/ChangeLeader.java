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

class ChangeLeader {

    private File file = new File(getFilePath());
    private YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    ChangeLeader(Player player, String clan, String playerName) {
        if (checkIfClanExists(clan)) {
            if (isPlayerInClan(clan, playerName)) {
                if (isPlayerLeader(clan, playerName)) {
                    player.sendMessage(ChatColor.AQUA + playerName + ChatColor.RED + " is already the leader!");
                } else {
                    updateOldLeader(clan);
                    updateNewLeader(clan, playerName);
                    player.sendMessage(ChatColor.AQUA + playerName + ChatColor.RED + " is the new leader of " + ChatColor.AQUA + clan);
                }
            } else {
                player.sendMessage(ChatColor.AQUA + playerName + ChatColor.RED + " is not in the clan " + ChatColor.AQUA + clan);
            }
        } else {
            player.sendMessage(ChatColor.RED + "Clan does not exist!");
        }
    }

    private void updateOldLeader(String clan) {
        String oldLeader = (String) yamlConfiguration.get("clans." + clan + ".leader");
        yamlConfiguration.set("clans." + clan + ".leader", null);
        yamlConfiguration.set("players." + oldLeader + ".Player_Permissions", PlayerPermission.MEMBER.toString());
        save();
        try {
            assert oldLeader != null;
            Player player = Bukkit.getPlayer(oldLeader);
            player.sendMessage(ChatColor.RED + "You have been replaced as leader!");
        } catch (NullPointerException e) {
            System.out.println("Tried to send a player a message that wasn't logged on!");
        }
    }

    private void updateNewLeader(String clan, String playerName) {
        yamlConfiguration.set("clans." + clan + ".leader", playerName);
        yamlConfiguration.set("players." + playerName + ".Player_Permissions", PlayerPermission.LEADER.toString());
        save();
        try {
            Player player = Bukkit.getPlayer(playerName);
            player.sendMessage(ChatColor.GREEN + "You are now the new leader of " + ChatColor.AQUA + clan);
        } catch (NullPointerException e) {
            System.out.println("Tried to send a player a message that wasn't logged on!");
        }
    }

    private boolean checkIfClanExists(String clan) {
        return yamlConfiguration.get("clans." + clan) != null;
    }

    private boolean isPlayerLeader(String clan, String playerName) {
        return yamlConfiguration.get("clans." + clan + ".leader").equals(playerName);
    }

    private boolean isPlayerInClan(String clan, String playerName) {
        ArrayList<String> memberList = (ArrayList<String>) yamlConfiguration.getStringList("clans." + clan + ".members");
        return memberList.contains(playerName);
    }

    private void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}