package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

class GetHeldBanner {

    ItemStack getBanner(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        String clan = getClan(player);
        if (item.getType().toString().contains("BANNER")){
            player.sendMessage(ChatColor.GREEN + "You have set your clans banner!");
            setName(item, clan);
            return item;
        } else {
            player.sendMessage(ChatColor.RED + "You must be holding the banner you want to set in your main hand!");
            return null;
        }
    }

    private String getClan(Player player) {
        File file = new File(getFilePath());
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        return (String) yamlConfiguration.get("players." + player.getName() + ".clan");
    }

    public ItemStack setName(ItemStack is, String name){
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
        return is;
    }
}