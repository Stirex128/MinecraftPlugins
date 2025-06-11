package cz.cvut.fel.pjv.stolbajakub;

import cz.cvut.fel.pjv.stolbajakub.commands.Permissions;
import cz.cvut.fel.pjv.stolbajakub.commands.Spawn;
import cz.cvut.fel.pjv.stolbajakub.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lobby extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockProtectionListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryLock(), this);
        getServer().getPluginManager().registerEvents(new ListenForMenuOpen(), this);
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("myperms").setExecutor(new Permissions());
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        SettingsLoader.getInstance().load();
        getLogger().info("@Lobby custom plugin loaded.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
    public static Lobby getInstance(){
        return getPlugin(Lobby.class);
    }
}
