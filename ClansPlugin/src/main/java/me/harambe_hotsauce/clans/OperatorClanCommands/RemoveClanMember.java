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

    private File file = new File(getFilePath());
    private YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    RemoveClanMember(Player player, String clan, String member) {
        if (checkIfClanExists(clan)) {
            if (isPlayerInClan(clan, member)) {
                if (isLastPlayerInClan(clan)) {
                    player.sendMessage(ChatColor.RED + "You cannot remove a member from a clan with only one person remaining!");
                } else {
                    removePlayerFromMemberList(clan, member);
                    updatePlayer(member);
                    player.sendMessage(ChatColor.AQUA + member + ChatColor.RED + " has been removed from the clan " + ChatColor.AQUA + clan);
                }
            } else {
                player.sendMessage(ChatColor.AQUA + member + ChatColor.RED + " is not in the clan " + ChatColor.AQUA + clan);
            }
        } else {
            player.sendMessage(ChatColor.RED + "This clan does not exist!");
        }
    }

    private boolean isPlayerInClan(String clan, String playerName) {
        ArrayList<String> memberList = (ArrayList<String>) yamlConfiguration.getStringList("clans." + clan + ".members");
        return memberList.contains(playerName);
    }

    private void removePlayerFromMemberList(String clan, String playerName) {
        ArrayList<String> memberList = (ArrayList<String>) yamlConfiguration.getStringList("clans." + clan + ".members");
        memberList.remove(playerName);
        yamlConfiguration.set("clans." + clan + ".members", memberList.toArray());
        save();
    }

    private void updatePlayer(String playerName) {
        yamlConfiguration.set(".players" + playerName + ".Player_Permissions", PlayerPermission.NULL.toString());
        yamlConfiguration.set(".players" + playerName + ".clan", null);
        save();
    }

    private boolean isLastPlayerInClan(String clan) {
        ArrayList<String> memberList = (ArrayList<String>) yamlConfiguration.getStringList("clans." + clan + ".members");
        return memberList.toArray().length == 1;
    }

    public boolean checkIfClanExists(String clan) {
        return yamlConfiguration.get("clans." + clan) != null;
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}