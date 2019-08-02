package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

class GetHeldBanner {

    boolean getHeldItem(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        try {
            System.out.println(item.getType());
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}