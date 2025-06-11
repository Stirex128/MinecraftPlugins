package cz.cvut.fel.pjv.stolbajakub;


import cz.cvut.fel.pjv.stolbajakub.BungeeCommunication.BedwarsCommunication;
import cz.cvut.fel.pjv.stolbajakub.commands.Spawn;
import cz.cvut.fel.pjv.stolbajakub.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for the Lobby plugin.
 * This class extends JavaPlugin and handles plugin initialization, registration of listeners and commands,
 * and plugin channel communication setup.
 */
public final class Lobby extends JavaPlugin {
    /**
     * Called when the plugin is enabled.
     * This method registers event listeners, plugin message listeners, and commands,
     * sets up plugin channel communication, prevents hunger, and loads settings.
     */
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockProtectionListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryLock(), this);
        getServer().getPluginManager().registerEvents(new ListenForMenuOpen(), this);
        getServer().getPluginManager().registerEvents(new FireDamageListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "bedwars:tolobbyinfo", new BedwarsCommunication());
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BedwarsCommunication());

        HungerControl hungerControl = new HungerControl(this);
        hungerControl.preventHunger();
        SettingsLoader.getInstance().load();
        getCommand("spawn").setExecutor(new Spawn());
        ArenaManager.getInstance();
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
    public static Lobby getInstance(){
        return getPlugin(Lobby.class);
    }
}
