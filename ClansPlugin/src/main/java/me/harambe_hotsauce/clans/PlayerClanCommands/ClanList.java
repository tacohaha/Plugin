package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

class ClanList {

    ClanList(Player player) {
        ArrayList<String> clanList;
        File file = new File(getFilePath());
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        try {
            clanList = (ArrayList<String>) yamlConfiguration.getStringList("list");
            StringBuilder cList = new StringBuilder(ChatColor.GREEN + "Clans: ");
            for (int i = 0; i < clanList.toArray().length; i++) {
                if (clanList.toArray().length - 1 == i) {
                    cList.append(ChatColor.AQUA).append(clanList.toArray()[i]);
                } else {
                    cList.append(ChatColor.AQUA).append(clanList.toArray()[i]).append(ChatColor.GREEN).append(", ");
                }
            }
            player.sendMessage(cList.toString());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.DARK_RED + "No clans exist!");
        }
    }
}