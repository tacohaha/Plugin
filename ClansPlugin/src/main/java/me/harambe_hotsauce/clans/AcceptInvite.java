package me.harambe_hotsauce.clans;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static me.harambe_hotsauce.clans.GenerateFile.getFilePath;

public class AcceptInvite {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public AcceptInvite(Player player) {
        playerInvite(player);
    }

    public void playerInvite(Player player) {
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
                new JoinClan(clan, player);
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
