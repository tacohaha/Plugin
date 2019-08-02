package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

class InvitePlayer {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);


    InvitePlayer(String invPlayer, Player player) {
        if (canPlayerInvitePlayers(player)) {
            if (player == Bukkit.getPlayer(invPlayer)) {
                player.sendMessage("Nice try, but you can't invite yourself!");
            } else {
                Collection onlinePlayers = Bukkit.getOnlinePlayers();
                if (onlinePlayers.contains(Bukkit.getPlayer(invPlayer))) {
                    Player invitedPlayer = Bukkit.getPlayer(invPlayer);
                    assert invitedPlayer != null;
                    invitedPlayer.sendMessage(ChatColor.GREEN + "You have been invited to join the clan " + ChatColor.BLUE + getClan(player));
                    invitedPlayer.sendMessage(ChatColor.GREEN + "Type" + ChatColor.AQUA + " /clans accept " + ChatColor.GREEN + "to accept this invite or" + ChatColor.AQUA + " /clans deny " + ChatColor.GREEN + "to deny the invite.");
                    invitedPlayer.sendMessage("This invite will time out in 60 seconds.");
                    createInvite(player, invitedPlayer, getClan(player), System.currentTimeMillis());
                } else {
                    player.sendMessage(ChatColor.RED + invPlayer + ChatColor.GREEN + " is not logged in!");
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to invite players to your clan!");
        }
    }

    private String getClan(Player player) {
        return (String) yamlConfiguration.get("players." + player.getName() + ".clan");
    }

    private void createInvite(Player sender, Player invitedPlayer, String clan, long time) {
        yamlConfiguration.set("Invite." + invitedPlayer.getName() + ".Sender", sender.getName());
        yamlConfiguration.set("Invite." + invitedPlayer.getName() + ".Clan", clan);
        yamlConfiguration.set("Invite." + invitedPlayer.getName() + ".Timestamp", time);
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean canPlayerInvitePlayers(Player player) {
        String name = player.getName();
        try {
            if (getPermission(name)) {
                String clan = getClan(player);
                return getSpaceAvailable(clan);
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean getPermission(String name) {
        return yamlConfiguration.get("players." + name + ".Player_Permissions").equals("LEADER");
    }


    private boolean getSpaceAvailable(String clan) {
        ArrayList<String> playerList = (ArrayList<String>) yamlConfiguration.getStringList("clans." + clan + "." + "members");
        return playerList.size() != (int) yamlConfiguration.get("clans." + clan + ".limit");
    }
}