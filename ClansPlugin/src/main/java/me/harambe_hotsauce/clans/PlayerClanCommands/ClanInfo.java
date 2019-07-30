package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class ClanInfo {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public ClanInfo(Player player, String clan) {
        if (yamlConfiguration.get("clans." + clan) != null) {
            getInfo(player, clan);
        } else {
            player.sendMessage(ChatColor.AQUA + clan + ChatColor.RED + " does not exist!");
        }
    }

    public ClanInfo(Player player) {
        String clan = getClan(player);
        if (clan != null) {
            getInfo(player, clan);
        } else {
            player.sendMessage(ChatColor.RED + "You are not in a clan!");
        }
    }

    public String getClan(Player player) {
        return (String) yamlConfiguration.get("players." + player.getName() + ".clan");
    }

    public List<String> getMembers(String clan) {
        return  yamlConfiguration.getStringList("clans." + clan + "." + "members");
    }

    public int getMemberLimit(String clan) {
        return (int) yamlConfiguration.get("clans." + clan + ".limit");
    }

    public int getCurrentMemberCount(String clan) {
        ArrayList<String> playerList = (ArrayList<String>) yamlConfiguration.getStringList("clans." + clan + "." + "members");
        return playerList.size();
    }

    public String getDateCreated(String clan) {
        String date = (String) yamlConfiguration.get("clans." + clan + ".date_created");
        String[] newDate = date.split("\\.");
        date = newDate[1] + ChatColor.GREEN + "/" + ChatColor.AQUA + newDate[2] + ChatColor.GREEN + "/" + ChatColor.AQUA + newDate[0] + ChatColor.GREEN + " at " + ChatColor.AQUA  + newDate[3] + ChatColor.GREEN + ":" + ChatColor.AQUA  + newDate[4];
        return date;
    }

    public String getClanLeader(String clan) {
        return (String) yamlConfiguration.get("clans." + clan + ".leader");
    }

    public void getInfo(Player player, String clan) {
        player.sendMessage(ChatColor.GREEN + "Clan Name: " + ChatColor.AQUA + clan);
        player.sendMessage(ChatColor.GREEN + "Total Members: " + ChatColor.AQUA + getCurrentMemberCount(clan) + ChatColor.GREEN + "/" + ChatColor.AQUA + getMemberLimit(clan));
        player.sendMessage(ChatColor.GREEN + "Clan Leader: " + ChatColor.AQUA + getClanLeader(clan));
        player.sendMessage(ChatColor.GREEN + "Members:" + ChatColor.AQUA + getMembers(clan).toString().replace("[", "").replace("]", ""));
        player.sendMessage(ChatColor.GREEN + "Date Created: " + ChatColor.AQUA + getDateCreated(clan));
    }
}