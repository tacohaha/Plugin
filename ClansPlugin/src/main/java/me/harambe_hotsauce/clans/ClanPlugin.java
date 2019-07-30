package me.harambe_hotsauce.clans;

import me.harambe_hotsauce.clans.OperatorClanCommands.OperatorClanCommands;
import me.harambe_hotsauce.clans.PlayerClanCommands.GenerateFile;
import me.harambe_hotsauce.clans.PlayerClanCommands.PlayerClanCommands;
import org.bukkit.plugin.java.JavaPlugin;

public final class ClanPlugin extends JavaPlugin {

    private static JavaPlugin clanPlugin;

    @Override
    public void onEnable() {

        new GenerateFile("Clans", "ClanList.yml");
        this.getCommand("clans").setExecutor(new PlayerClanCommands(this));
        this.getCommand("opclans").setExecutor(new OperatorClanCommands());
    }

    @Override
    public void onDisable() {
    }

}

