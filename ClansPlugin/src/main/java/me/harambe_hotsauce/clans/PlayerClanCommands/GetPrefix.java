package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

class GetPrefix {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    GetPrefix(AsyncPlayerChatEvent event) {
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

    private String getClan(String playerName) {
        return (String) yamlConfiguration.get("players." + playerName + ".clan");
    }

    private String getPrefix(String playerName) {
        return (String) yamlConfiguration.get("clans." + getClan(playerName) + ".prefix");
    }
}