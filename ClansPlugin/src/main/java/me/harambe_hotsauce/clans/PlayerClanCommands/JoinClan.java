package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

class JoinClan {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    JoinClan(String clan, Player player) {
        if (checkIfClanExists(clan)) {
            if (!isPlayerInClan(player, clan)) {
                String playerName = player.getName();
                ArrayList<String> playerList = (ArrayList<String>) yamlConfiguration.getStringList("clans." + clan + "." + "members");
                playerList.add(playerName);
                yamlConfiguration.set("clans." + clan + ".members", playerList.toArray());
                playerList.clear();
                yamlConfiguration.set("players." + player.getName() + ".UUID", player.getUniqueId().toString());
                yamlConfiguration.set("players." + player.getName() + ".Player_Permissions", PlayerPermission.MEMBER.toString());
                yamlConfiguration.set("players." + player.getName() + ".clan", clan);
                save();
                player.sendMessage(ChatColor.GREEN + "You have successfully joined: " + ChatColor.BLUE + clan);
                player.sendMessage(ChatColor.GREEN + "You are now a member in: " + ChatColor.BLUE + clan);
            } else {
                player.sendMessage(ChatColor.RED + "You are already in a clan!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Clan does not exist!");
        }
    }

    public boolean checkIfClanExists(String clan) {
        return yamlConfiguration.get("clans." + clan) != null;
    }

    private boolean isPlayerInClan(Player player, String clan) {
        if (yamlConfiguration.get("players." + player.getName()) == null) {
            return false;
        } else return yamlConfiguration.get("players." + player.getName() + ".clan") == clan | yamlConfiguration.get("players." + player.getName() + ".clan") != null;
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}