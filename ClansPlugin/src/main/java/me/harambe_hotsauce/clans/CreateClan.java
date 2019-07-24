package me.harambe_hotsauce.clans;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CreateClan {

    File file = new File("plugins/Clans/" + "ClanList.yml");
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    ArrayList playerList;

    public CreateClan(String name, ArrayList playerList, Player player) {
        this.playerList = playerList;
        yamlConfiguration.set("clans." + getClanSize() + "." + name + ".members", playerList.toArray());
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        yamlConfiguration.set("players." + player.getUniqueId() + "." + name, "player_permission." + PlayerPermission.LEADER.toString());
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
