package me.harambe_hotsauce.clans.OperatorClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class SetClanPrefix {

    private File file = new File(getFilePath());
    private YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    SetClanPrefix(Player player, String clan, String prefix) {
        if (checkIfClanExists(clan)) {
            if (prefix.length() <= 5) {
                yamlConfiguration.set("clans." + clan + ".prefix", prefix);
                save();
                player.sendMessage(ChatColor.AQUA + clan + ChatColor.GREEN + "'s new prefix is " + ChatColor.AQUA + prefix);
            } else {
                player.sendMessage(ChatColor.RED + "Prefixes cannot be more than 5 characters!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Clan does not exist!");
        }
    }

    public boolean checkIfClanExists(String clan) {
        return yamlConfiguration.get("clans." + clan) != null;
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}