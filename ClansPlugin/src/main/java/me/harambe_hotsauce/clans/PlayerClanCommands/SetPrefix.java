package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class SetPrefix {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public SetPrefix(Player player, String prefix) {
        if (checkIfClanExists(getClan(player))) {
            if (getPermission(player)) {
                yamlConfiguration.set("clans." + getClan(player) + ".prefix", prefix);
                save();
                player.sendMessage(prefix);
            }
        }
    }

    public String getClan(Player player) {
        return (String) yamlConfiguration.get("players." + player.getName() + ".clan");
    }

    public boolean checkIfClanExists(String clan) {
        if (yamlConfiguration.get("clans." + clan) == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean getPermission(Player player) {
        if (yamlConfiguration.get("players." + player.getName() + ".Player_Permissions").equals("LEADER")) {
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