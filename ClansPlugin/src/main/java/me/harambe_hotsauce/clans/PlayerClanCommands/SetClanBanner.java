package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

class SetClanBanner {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    SetClanBanner(Player player) {
        String clan = getClan(player);
        if (checkIfClanExists(clan)) {
            if (getPermission(player.getName())) {
                yamlConfiguration.set("clans." + clan + ".banner", new GetHeldBanner().getBanner(player));
                save();
            } else {
                player.sendMessage(ChatColor.RED + "You are not the clan leader!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You are not in a clan!");
        }
    }

    private String getClan(Player player) {
        return (String) yamlConfiguration.get("players." + player.getName() + ".clan");
    }

    private boolean getPermission(String name) {
        return yamlConfiguration.get("players." + name + ".Player_Permissions").equals("LEADER");
    }

    private boolean checkIfClanExists(String clan) {
        return yamlConfiguration.get("clans." + clan) != null;
    }

    private void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}