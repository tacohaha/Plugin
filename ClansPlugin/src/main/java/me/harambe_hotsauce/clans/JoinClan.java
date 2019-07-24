package me.harambe_hotsauce.clans;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JoinClan {

    File file = new File("plugins/Clans/" + "ClanList.yml");
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    ArrayList playerList;

    public JoinClan(String clan, Player player) {
        playerList = (ArrayList<String>) yamlConfiguration.get(clan);
        playerList.add(player.getName());
        yamlConfiguration.set("clans." + getClanSize() + "." + clan + ".members", playerList.toArray());
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        yamlConfiguration.set("players." + player.getUniqueId() + "." + clan, "player_permission." + PlayerPermission.MEMBER.toString());
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getClanSize() {
        try {
            return yamlConfiguration.getConfigurationSection("clans").getKeys(false).size() + 1;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 1;
        }
    }
}
