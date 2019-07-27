package me.harambe_hotsauce.clans;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static me.harambe_hotsauce.clans.GenerateFile.getFilePath;

public class AddSlots {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public AddSlots(Player player) {
        String clan = getClan(player);
        if (getPermission(player.getName())) {
            int prevLimit = (int) yamlConfiguration.get("clans." + clan + ".limit");
            int newLimit = prevLimit + 5;
            yamlConfiguration.set("clans." + clan + ".limit", newLimit);
            try {
                yamlConfiguration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getClan(Player player) {
        return (String) yamlConfiguration.get("players." + player.getName() + ".clan");
    }

    public boolean getPermission(String name) {
        if (yamlConfiguration.get("players." + name + ".Player_Permissions").equals("LEADER")) {
            return true;
        } else {
            return false;
        }
    }
}
