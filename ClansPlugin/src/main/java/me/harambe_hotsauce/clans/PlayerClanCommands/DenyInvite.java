package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

class DenyInvite {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    DenyInvite(Player player) {
        if (yamlConfiguration.get("Invite." + player.getName()) != null) {
            if (((System.currentTimeMillis() - (long) yamlConfiguration.get("Invite." + player.getName() + ".Timestamp") / 1000) > 60)) {
                String clan = (String) yamlConfiguration.get("Invite." + player.getName() + ".Clan");
                yamlConfiguration.set("Invite." + player.getName() + ".Timestamp", null);
                yamlConfiguration.set("Invite." + player.getName() + ".Clan", null);
                yamlConfiguration.set("Invite." + player.getName() + ".Sender", null);
                yamlConfiguration.set("Invite." + player.getName(), null);
                try {
                    yamlConfiguration.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.sendMessage(ChatColor.RED + "You have denied to join the clan " + ChatColor.AQUA + clan + ChatColor.RED + "!");
                Player newPlayer = Bukkit.getPlayer((String) yamlConfiguration.get("Invite." + player.getName() + ".Sender"));
                try {
                    newPlayer.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + "Has denied your invite request!");
                } catch (NullPointerException e) {
                    System.out.println("Player that sent an invite has logged out!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Your invite has timed out!");
                yamlConfiguration.set("Invite." + player.getName() + ".Timestamp", null);
                yamlConfiguration.set("Invite." + player.getName() + ".Clan", null);
                yamlConfiguration.set("Invite." + player.getName() + ".Sender", null);
                yamlConfiguration.set("Invite." + player.getName(), null);
                try {
                    yamlConfiguration.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "You have not been invited to a clan!");
        }
    }
}