package me.harambe_hotsauce.clans;

import org.bukkit.plugin.java.JavaPlugin;

public final class ClanPlugin extends JavaPlugin {

    private static JavaPlugin clanPlugin;

    @Override
    public void onEnable() {
        clanPlugin = this;

        new GenerateFile("Clans", "ClanList.yml");

        this.getCommand("clans").setExecutor(new ClanCommand(this));
    }

    @Override
    public void onDisable() {
    }

    public static JavaPlugin getInstance() {
        return clanPlugin;
    }
}

