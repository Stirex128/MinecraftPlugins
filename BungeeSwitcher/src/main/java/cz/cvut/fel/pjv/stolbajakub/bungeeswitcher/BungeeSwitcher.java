package cz.cvut.fel.pjv.stolbajakub.bungeeswitcher;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for BungeeSwitcher.
 * Handles registration of outgoing plugin channels and commands.
 */
public final class BungeeSwitcher extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getCommand("switch").setExecutor(new SwitchCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static BungeeSwitcher getInstance(){
        return getPlugin(BungeeSwitcher.class);
    }
}
