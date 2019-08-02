package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

class GetClanBanner {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    GetClanBanner(Player player) {
        String clan = getClan(player);
        if (checkIfClanExists(clan)) {
            if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                ItemStack newBanner = (ItemStack) yamlConfiguration.get("clans." + clan + ".banner");
                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), newBanner);
            } else {
                player.sendMessage(ChatColor.RED + "Your selected hand must be empty!");
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
}