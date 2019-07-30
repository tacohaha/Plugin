package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class AddSlots {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public AddSlots(Player player) {
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

    public boolean getPermission(String name) {
        if (yamlConfiguration.get("players." + name + ".Player_Permissions").equals("LEADER")) {
            return true;
        } else {
            return false;
        }
    }
}