package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

class AddSlots {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    AddSlots(Player player) {
        String clan = getClan(player);
        if (checkIfClanExists(clan)) {
            if (getPermission(player.getName())) {
                int prevLimit = (int) yamlConfiguration.get("clans." + clan + ".limit");
                int newLimit = prevLimit + 5;
                yamlConfiguration.set("clans." + clan + ".limit", newLimit);
                player.sendMessage(ChatColor.GREEN + "The new limit of members in your clan is " + ChatColor.AQUA + newLimit);
                try {
                    yamlConfiguration.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                player.sendMessage(ChatColor.RED + "You are not the leader in this clan!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You are not in a clan!");
        }
    }

    private String getClan(Player player) {
        return (String) yamlConfiguration.get("players." + player.getName() + ".clan");
    }

    private boolean checkIfClanExists(String clan) {
        return yamlConfiguration.get("clans." + clan) != null;
    }

    private boolean getPermission(String name) {
        return yamlConfiguration.get("players." + name + ".Player_Permissions").equals("LEADER");
    }
}