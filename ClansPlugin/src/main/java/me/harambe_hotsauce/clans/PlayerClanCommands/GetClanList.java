package me.harambe_hotsauce.clans.PlayerClanCommands;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

import static me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile.getFilePath;

public class GetClanList {

    File file = new File(getFilePath());
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public List<String> GetClanList() {
        return yamlConfiguration.getStringList("list");
    }
}
