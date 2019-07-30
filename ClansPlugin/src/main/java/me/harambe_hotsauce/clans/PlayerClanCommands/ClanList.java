package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class ClanList {


    public ClanList(Player player) {
        ArrayList<String> clanList = new ArrayList();
        File file = new File(getFilePath());
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        try {
            clanList = (ArrayList<String>) yamlConfiguration.getStringList("list");
            String cList = ChatColor.GREEN + "Clans: ";
            for (int i = 0; i < clanList.toArray().length; i++) {
                if (clanList.toArray().length - 1 == i) {
                    cList = cList + ChatColor.AQUA + (String)clanList.toArray()[i];
                } else {
                    cList = cList + ChatColor.AQUA + (String)clanList.toArray()[i] + ChatColor.GREEN + ", ";
                }
            }
            player.sendMessage(cList);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.DARK_RED + "No clans exist!");
        }
    }

}
