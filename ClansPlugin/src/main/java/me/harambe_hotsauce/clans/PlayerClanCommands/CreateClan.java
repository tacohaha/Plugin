package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class CreateClan {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    ArrayList<String> playerList = new ArrayList();
    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

    public CreateClan(String name, Player player) {
        if (!checkIfClanExists(name)) {
            if (!isPlayerInClan(player, name)) {
                playerList.add(player.getName());
                yamlConfiguration.set("clans." + name + ".members", playerList.toArray());
                yamlConfiguration.set("clans." + name + ".limit", 5);
                yamlConfiguration.set("clans." + name + ".date_created", timeStamp);
                playerList.clear();
                yamlConfiguration.set("clans." + name + ".leader", player.getName());
                yamlConfiguration.set("players." + player.getName() + ".UUID", player.getUniqueId().toString());
                yamlConfiguration.set("players." + player.getName() + ".Player_Permissions", PlayerPermission.LEADER.toString());
                yamlConfiguration.set("players." + player.getName() + ".clan", name);
                save();
                player.sendMessage(ChatColor.GREEN + "You have created the clan: " + ChatColor.AQUA + name);
                player.sendMessage(ChatColor.GREEN + "You are now the leader of: " + ChatColor.AQUA + name);
                new AddToClanList(name);

            } else {
                player.sendMessage(ChatColor.RED + "You are already in a clan!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "This clan already exists!");
        }
    }

    public boolean checkIfClanExists(String clan) {
        if (yamlConfiguration.get("clans." + clan) == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isPlayerInClan(Player player, String clan) {
        if (yamlConfiguration.get("players." + player.getName()) == null) {
            return false;
        } else if (yamlConfiguration.get("players." + player.getName() + ".clan") != null | yamlConfiguration.get("players." + player.getName() + ".clan") == clan) {
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