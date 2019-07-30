package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class KickPlayer {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public KickPlayer(Player player, String kickedPlayer) {
        if (checkIfClanExists(getClan(player))) {
            if (getPermission(player)) {
                if (checkIfPlayerInClan(getClan(player), kickedPlayer)) {
                        if (player.getName().equals(kickedPlayer)) {
                            player.sendMessage(ChatColor.DARK_RED + "You cannot kick yourself!");
                        } else {
                            ArrayList<String> memberList = (ArrayList<String>) yamlConfiguration.get("clans." + getClan(player) + ".members");
                            memberList.remove(kickedPlayer);
                            yamlConfiguration.set("clans." + getClan(player) + ".members", memberList.toArray());
                            yamlConfiguration.set("players." + kickedPlayer + ".Player_Permissions", PlayerPermission.NULL.toString());
                            yamlConfiguration.set("players." + kickedPlayer + ".clan", null);
                            save();
                            player.sendMessage(ChatColor.AQUA + kickedPlayer + ChatColor.RED + " has been kicked from your clan!");
                            try {
                                Player kPlayer = Bukkit.getPlayer(kickedPlayer);
                                kPlayer.sendMessage(ChatColor.DARK_RED + "You have been kicked from " + ChatColor.AQUA + getClan(player));
                            } catch (NullPointerException e) {
                                System.out.println("Attempted to message " + kickedPlayer + " but they weren't logged in.");
                            }
                        }
                    } else {
                    player.sendMessage(ChatColor.DARK_RED + "Player is not in your clan!");
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + "You do not have permission to kick people from your clan!");
            }
        } else {
            player.sendMessage(ChatColor.DARK_RED + "You are not in a clan!");
        }
    }

    public boolean checkIfPlayerInClan(String clan, String player) {
        ArrayList<String> memberList = (ArrayList<String>) yamlConfiguration.getStringList("clans." + clan + ".members");
        if (memberList.contains(player)) {
            return true;
        } else {
            return false;
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

    public boolean getPermission(Player player) {
        if (yamlConfiguration.get("players." + player.getName() + ".Player_Permissions").equals("LEADER")) {
            return true;
        } else {
            return false;
        }
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
