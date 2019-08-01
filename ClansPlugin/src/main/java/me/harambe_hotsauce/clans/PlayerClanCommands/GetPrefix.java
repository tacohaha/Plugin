package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.io.File;
import java.io.IOException;
import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class GetPrefix {


    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public GetPrefix(AsyncPlayerChatEvent event) {
        String prefix = getPrefix(event.getPlayer().getName());
        String newPrefix = ChatColor.AQUA + "[" + prefix + "]";
        String prevFormat = event.getFormat();
        String newFormat = newPrefix + ChatColor.RESET + prevFormat;
        try {
            if (prefix.equals("null")) {
                event.setFormat(prevFormat);
            } else {
                event.setFormat(newFormat);
            }
        } catch (NullPointerException e) {
            System.out.println("player prefix is null");
        }
    }

    public String getClan(String playerName) {
        return (String) yamlConfiguration.get("players." + playerName + ".clan");
    }

    public String getPrefix(String playerName) {
        return (String) yamlConfiguration.get("clans." + getClan(playerName) + ".prefix");
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}