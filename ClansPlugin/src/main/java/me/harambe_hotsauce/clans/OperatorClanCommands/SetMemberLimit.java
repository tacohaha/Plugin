package me.harambe_hotsauce.clans.OperatorClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class SetMemberLimit {

    private File file = new File(getFilePath());
    private YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    SetMemberLimit(Player player, String clan, String limit) {
        if (checkIfClanExists(clan)) {
            int l = Integer.parseInt(limit);
            yamlConfiguration.set("clans." + clan + ".limit", l);
            save();
            player.sendMessage(ChatColor.AQUA + clan + ChatColor.RED + "'s new limit is " + ChatColor.AQUA + l);
        } else {
            player.sendMessage(ChatColor.RED + "Clan does not exist!");
        }
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