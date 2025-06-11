package cz.cvut.fel.pjv.stolbajakub.bedwars.WorldWork;

import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * Manages the PvP settings for specific worlds in a Minecraft server.
 */
public class PvpManager {

    /**
     * Toggles the PvP setting for a specified world. If the world is not found, no action is performed.
     *
     * @param worldName The name of the world to toggle PvP settings.
     * @param pvpEnabled True to enable PvP, false to disable it.
     */
    public void setPvPInWorld(String worldName, boolean pvpEnabled) {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            world.setPVP(pvpEnabled);
        } else {
            // Optionally, log a warning or notify the caller that the specified world does not exist.
            Bukkit.getLogger().warning("Attempted to toggle PvP for a non-existent world: " + worldName);
        }
    }
}