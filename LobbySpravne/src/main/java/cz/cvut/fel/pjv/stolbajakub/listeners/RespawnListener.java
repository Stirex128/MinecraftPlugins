package cz.cvut.fel.pjv.stolbajakub.listeners;


import cz.cvut.fel.pjv.stolbajakub.SettingsLoader;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import static org.bukkit.Bukkit.getServer;
/**
 * Listens for player respawn events and sets their respawn location to the configured spawn location.
 */
public class RespawnListener implements Listener {
    /**
     * Sets the player's respawn location to the configured spawn location.
     *
     * @param event The player respawn event.
     */

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Location spawnLocation = new Location(getServer().getWorld("world"),
                SettingsLoader.getInstance().getSpawnX(),
                SettingsLoader.getInstance().getSpawnY(),
                SettingsLoader.getInstance().getSpawnZ());
        event.setRespawnLocation(spawnLocation);
    }
}
