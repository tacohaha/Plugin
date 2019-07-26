package me.harambe_hotsauce.clans;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static me.harambe_hotsauce.clans.GenerateFile.getFilePath;

public class LeaveClan {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    ArrayList<String> playerList;

    public LeaveClan(Player player) {
        String playerName = player.getName();
        String clan = getClan(player);
        playerList = (ArrayList<String>) yamlConfiguration.getStringList("clans." + clan + "." + "members");
        if (checkIfLeader(player)) {
            if (checkIfLastPlayer()) {
                player.sendMessage("You are the last player in: " + clan + " If you wish to leave, please delete the clan!");
            } else {
                playerList.remove(playerName);
                yamlConfiguration.set("players." + playerName + ".Player_Permissions", PlayerPermission.NULL.toString());
                yamlConfiguration.set("clans." + clan + ".members", playerList.toArray());
                yamlConfiguration.set("players." + playerName + ".clan", null);
                yamlConfiguration.set("players." + playerList.toArray()[0] + ".Player_Permissions", PlayerPermission.LEADER.toString());
                yamlConfiguration.set("clans." + clan + ".leader", null);
                yamlConfiguration.set("clans." + clan + ".leader", playerList.toArray()[0]);
                save();
                Player newPlayer = Bukkit.getPlayer((String) playerList.toArray()[0]);
                player.sendMessage("You have left the clan!");
                playerList.clear();
                try {
                    newPlayer.sendMessage(playerName + " Has left the clan! You are now the new leader!");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        } else {
            playerList.remove(playerName);
            yamlConfiguration.set("players." + playerName + ".Player_Permissions", PlayerPermission.NULL.toString());
            yamlConfiguration.set("clans." + clan + ".members", playerList.toArray());
            yamlConfiguration.set("players." + playerName + ".clan", null);
            save();
            playerList.clear();
            player.sendMessage("You have left the clan!");
        }
    }

    public String getClan(Player player) {
        return (String) yamlConfiguration.get("players." + player.getName() + ".clan");
    }

    public boolean checkIfLeader(Player player) {
        return yamlConfiguration.get("players." + player.getName() + ".Player_Permissions") != PlayerPermission.LEADER.toString();
    }

    public boolean checkIfLastPlayer() {
        if (playerList.toArray().length == 1) {
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
