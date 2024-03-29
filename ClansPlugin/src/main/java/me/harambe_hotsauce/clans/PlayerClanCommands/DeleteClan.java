package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

class DeleteClan {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    DeleteClan(Player player) {
        if (isInClan(player)) {
            if (isLeader(player)) {
                if (isLastPlayer(getClan(player))) {
                    String clan = getClan(player);
                    yamlConfiguration.set("clans." + clan, null);
                    yamlConfiguration.set("players." + player.getName() + ".Player_Permissions", PlayerPermission.NULL.toString());
                    yamlConfiguration.set("players." + player.getName() + ".clan", null);
                    save();
                    new RemoveFromClanList(clan);
                    player.sendMessage(ChatColor.RED + "You have deleted your clan!");
                } else {
                    player.sendMessage(ChatColor.RED + "If you want to delete your clan, you have to kick out all current members " + ChatColor.AQUA + ":(");
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + "Nice try, but you aren't the leader!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You are not in a clan!");
        }
    }

    private String getClan(Player player) {
        return (String) yamlConfiguration.get("players." + player.getName() + ".clan");
    }

    private String getRank(Player player) {
        return (String) yamlConfiguration.get("players." + player.getName() + ".Player_Permissions");
    }

    private int getNumberOfMembers(String clan) {
        ArrayList<String> members = (ArrayList<String>) yamlConfiguration.getStringList("clans." + clan + ".members");
        return members.toArray().length;
    }

    private boolean isInClan(Player player) {
        return getClan(player) != null | getClan(player) != "null";
    }

    private boolean isLeader(Player player) {
        return getRank(player).equals("LEADER");
    }

    private boolean isLastPlayer(String clan) {
        return getNumberOfMembers(clan) == 1;
    }

    private void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}