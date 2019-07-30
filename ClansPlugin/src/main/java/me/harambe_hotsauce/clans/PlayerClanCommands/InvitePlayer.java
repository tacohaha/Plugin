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

public class InvitePlayer {

    Collection onlinePlayers;
    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    ArrayList<String> playerList;


    public InvitePlayer(String invPlayer, Player player) {
        if (canPlayerInvitePlayers(player)) {
            if (player == Bukkit.getPlayer(invPlayer)) {
                player.sendMessage("Nice try, but you can't invite yourself!");
            } else {
                onlinePlayers = Bukkit.getOnlinePlayers();
                if (onlinePlayers.contains(Bukkit.getPlayer(invPlayer))) {
                    Player invitedPlayer = Bukkit.getPlayer(invPlayer);
                    invitedPlayer.sendMessage(ChatColor.GREEN + "You have been invited to join the clan " + ChatColor.BLUE + getClan(player));
                    invitedPlayer.sendMessage(ChatColor.GREEN + "Type" + ChatColor.AQUA + " /clans accept " + ChatColor.GREEN + "to accept this invite or" + ChatColor.AQUA + " /clans deny "  + ChatColor.GREEN + "to deny the invite.");
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

    public String getClan(Player player) {
        return (String) yamlConfiguration.get("players." + player.getName() + ".clan");
    }

    public void createInvite(Player sender, Player invitedPlayer, String clan, long time) {
        yamlConfiguration.set("Invite." + invitedPlayer.getName() + ".Sender", sender.getName());
        yamlConfiguration.set("Invite." + invitedPlayer.getName() + ".Clan", clan);
        yamlConfiguration.set("Invite." + invitedPlayer.getName() + ".Timestamp", time);
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean canPlayerInvitePlayers(Player player) {
        String name = player.getName();
        try {
            if (getPermission(name)) {
                String clan = getClan(player);
                if (getSpaceAvailable(clan)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getPermission(String name) {
        if (yamlConfiguration.get("players." + name + ".Player_Permissions").equals("LEADER")) {
            return true;
        } else {
            return false;
        }
    }


    public boolean getSpaceAvailable(String clan) {
        playerList = (ArrayList<String>) yamlConfiguration.getStringList("clans." + clan + "." + "members");
        if (playerList.size() == (int) yamlConfiguration.get("clans." + clan + ".limit")) {
            return false;
        } else {
            return true;
        }
    }
}