package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

class AddToClanList {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    AddToClanList(String clan) {
        ArrayList<String> clanList = new ArrayList();
        try {
            clanList = (ArrayList<String>) yamlConfiguration.getStringList("list");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        clanList.add(clan);
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