package me.harambe_hotsauce.clans.OperatorClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class SetClanPrefix {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public SetClanPrefix(Player player, String clan, String prefix) {
        if (checkIfClanExists(clan)) {
            yamlConfiguration.set("clans." + clan + ".prefix", prefix);
            save();
        } else {
            player.sendMessage(ChatColor.RED + "Clan does not exist!");
        }
    }

    public boolean checkIfClanExists(String clan) {
        if (yamlConfiguration.get("clans." + clan) == null) {
            return false;
        } else {
            return true;
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