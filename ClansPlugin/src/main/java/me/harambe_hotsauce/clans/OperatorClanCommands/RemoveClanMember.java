package me.harambe_hotsauce.clans.OperatorClanCommands;

import me.harambe_hotsauce.clans.PlayerClanCommands.PlayerPermission;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class RemoveClanMember {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public RemoveClanMember(Player player, String clan, String member) {
        if (checkIfClanExists(clan)){
            if (isPlayerInClan(clan, member)) {
                if (isLastPlayerInClan(clan)) {
                    player.sendMessage(ChatColor.RED + "You cannot remove a member from a clan with only one person remaining!");
                } else {
                    removePlayerFromMemberList(clan, member);
                    updatePlayer(member);
                }
            } else {
                player.sendMessage(ChatColor.AQUA + member + ChatColor.RED + " is not in the clan " + ChatColor.AQUA + clan);
            }
        } else {
            player.sendMessage(ChatColor.RED + "This clan does not exist!");
        }
    }

    public boolean isPlayerInClan(String clan, String playerName) {
        ArrayList<String> memberList = (ArrayList<String>) yamlConfiguration.get("clans." + clan + ".members");
        if (memberList.contains(playerName)) {
            return true;
        } else {
            return false;
        }
    }

    public void removePlayerFromMemberList(String clan, String playerName) {
        ArrayList<String> memberList = (ArrayList<String>) yamlConfiguration.get("clans." + clan + ".members");
        memberList.remove(playerName);
        yamlConfiguration.set("clans." + clan + ".members", memberList.toArray());
        save();
    }

    public void updatePlayer(String playerName) {
        yamlConfiguration.set(".players" + playerName + ".Player_Permissions", PlayerPermission.NULL.toString());
        yamlConfiguration.set(".players" + playerName + ".clan", null);
        save();
    }

    public boolean isLastPlayerInClan(String clan) {
        ArrayList<String> memberList = (ArrayList<String>) yamlConfiguration.get("clans." + clan + ".members");
        if (memberList.toArray().length == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfClanExists(String clan) {
        if (yamlConfiguration.get("clans." + clan) == null) {
            return false;
        } else {
            return true;
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