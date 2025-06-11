package cz.cvut.fel.pjv.stolbajakub.myperms;

import cz.cvut.fel.pjv.stolbajakub.myperms.listeners.PlayerJoinListenerPerms;
import cz.cvut.fel.pjv.stolbajakub.myperms.listeners.PlayerQuitListener;
import org.bukkit.plugin.java.JavaPlugin;
/**
 * Main plugin class responsible for enabling and disabling the plugin, as well as registering event listeners and commands.
 */
public final class Myperms extends JavaPlugin {
    /**
     * Called when the plugin is enabled. Registers event listeners and command executor.
     */
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListenerPerms(), this);
        getCommand("myperms").setExecutor(new Permissions());
    }
    /**
     * Called when the plugin is disabled. Performs plugin shutdown logic.
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static Myperms getInstance(){
        return getPlugin(Myperms.class);
    }
}

