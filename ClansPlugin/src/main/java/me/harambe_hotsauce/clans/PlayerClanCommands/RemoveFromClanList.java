package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class RemoveFromClanList {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public RemoveFromClanList(String clan) {
        ArrayList<String> clanList = (ArrayList<String>) yamlConfiguration.getStringList("list");
        clanList.remove(clan);
        yamlConfiguration.set("list", clanList.toArray());
        clanList.clear();
        save();
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}