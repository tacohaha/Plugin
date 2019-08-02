package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

class GetHeldBanner {

    ItemStack getBanner(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().toString().contains("BANNER")){
            player.sendMessage(ChatColor.GREEN + "You have set your clans banner!");
            return item;
        } else {
            player.sendMessage(ChatColor.RED + "You must be holding the banner you want to set in your main hand!");
            return null;
        }
    }
}