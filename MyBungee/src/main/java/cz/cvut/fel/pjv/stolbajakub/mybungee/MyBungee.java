package cz.cvut.fel.pjv.stolbajakub.mybungee;

import cz.cvut.fel.pjv.stolbajakub.mybungee.listeners.BedInfoListen;
import cz.cvut.fel.pjv.stolbajakub.mybungee.listeners.BungeeEconomyListen;
import net.md_5.bungee.api.plugin.Plugin;
/**
 * Main plugin class for MyBungee, responsible for initializing and managing plugin functionality.
 */
public final class MyBungee extends Plugin {
    /**
     * Called when the plugin is enabled.
     * Registers plugin channels and event listeners.
     */

    @Override
    public void onEnable() {
        //getProxy().getPluginManager().registerListener(this, new LoginLogoutPerms(this));
        this.getProxy().registerChannel("bedwars:bungeeeconomy");
        this.getProxy().registerChannel("bedwars:tolobbyinfo");
        getProxy().getPluginManager().registerListener(this, new BedInfoListen());
        getProxy().getPluginManager().registerListener(this, new BungeeEconomyListen());
    }
    /**
     * Called when the plugin is disabled.
     * Performs any necessary cleanup or shutdown logic.
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
