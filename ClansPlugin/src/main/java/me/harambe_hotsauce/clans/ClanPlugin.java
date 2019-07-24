package me.harambe_hotsauce.clans;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class ClanPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        try {
            File file = new File("plugins/Clans/" + "ClanList.yml");
            if (file.createNewFile()) {
                System.out.println("ClanList.yml created!");
            } else {
                System.out.println("ClanList.yml exists!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.getCommand("clans").setExecutor(new ClanCommand(this));
//        ArrayList playerList = new ArrayList();
//        playerList.add("Harambe_Hotsauce");
//        new CreateClan("Epic_Clan", playerList);
    }

    @Override
    public void onDisable() {
    }
}

