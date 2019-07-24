package me.harambe_hotsauce.clans;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CreateClan {

    File file = new File("plugins/Clans/" + "ClanList.yml");
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    ArrayList playerList;

    public CreateClan(String name, ArrayList playerList) {
        this.playerList = playerList;
        yamlConfiguration.set(name + ".members", playerList.toArray());
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
