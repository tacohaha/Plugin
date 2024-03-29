package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

class LeaveClan {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    private ArrayList<String> playerList;

    LeaveClan(Player player) {
        String playerName = player.getName();
        String clan = getClan(player);
        playerList = (ArrayList<String>) yamlConfiguration.getStringList("clans." + clan + "." + "members");
        if (checkIfLeader(player)) {
            if (checkIfLastPlayer()) {
                player.sendMessage(ChatColor.RED + "You are the last player in: " + ChatColor.BLUE + clan + ChatColor.RED + " If you wish to leave, please delete the clan!");
            } else {
                playerList.remove(playerName);
                yamlConfiguration.set("players." + playerName + ".Player_Permissions", PlayerPermission.NULL.toString());
                yamlConfiguration.set("clans." + clan + ".members", playerList.toArray());
                yamlConfiguration.set("players." + playerName + ".clan", null);
                yamlConfiguration.set("players." + playerList.toArray()[0] + ".Player_Permissions", PlayerPermission.LEADER.toString());
                yamlConfiguration.set("clans." + clan + ".leader", null);
                yamlConfiguration.set("clans." + clan + ".leader", playerList.toArray()[0]);
                save();
                playerList.clear();
                player.sendMessage(ChatColor.RED + "You have left the clan!");
            }
        } else {
            playerList.remove(playerName);
            yamlConfiguration.set("players." + playerName + ".Player_Permissions", PlayerPermission.NULL.toString());
            yamlConfiguration.set("clans." + clan + ".members", playerList.toArray());
            yamlConfiguration.set("players." + playerName + ".clan", null);
            save();
            playerList.clear();
            player.sendMessage(ChatColor.RED + "You have left the clan!");
        }
    }

    private String getClan(Player player) {
        return (String) yamlConfiguration.get("players." + player.getName() + ".clan");
    }

    private boolean checkIfLeader(Player player) {
        return yamlConfiguration.get("players." + player.getName() + ".Player_Permissions") != PlayerPermission.LEADER.toString();
    }

    private boolean checkIfLastPlayer() {
        return playerList.toArray().length == 1;
    }

    private void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}