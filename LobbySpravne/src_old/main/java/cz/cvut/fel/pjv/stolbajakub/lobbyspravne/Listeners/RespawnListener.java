package cz.cvut.fel.pjv.stolbajakub.lobbyspravne.Listeners;

import cz.cvut.fel.pjv.stolbajakub.lobbyspravne.SettingsLoader;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import static org.bukkit.Bukkit.getServer;

public class RespawnListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Location spawnLocation = new Location(getServer().getWorld("world"),
                SettingsLoader.getInstance().getSpawnX(),
                SettingsLoader.getInstance().getSpawnY(),
                SettingsLoader.getInstance().getSpawnZ());
        event.setRespawnLocation(spawnLocation);
    }
}
