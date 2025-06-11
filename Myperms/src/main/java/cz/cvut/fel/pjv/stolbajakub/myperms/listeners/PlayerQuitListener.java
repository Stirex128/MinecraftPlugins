package cz.cvut.fel.pjv.stolbajakub.myperms.listeners;


import cz.cvut.fel.pjv.stolbajakub.myperms.Myperms;
import cz.cvut.fel.pjv.stolbajakub.myperms.PermsManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Listener for handling player quit events to save permissions.
 */
public class PlayerQuitListener implements Listener {

    /**
     * Saves player permissions on logout.
     * @param event The player quit event.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Map<String, Boolean> permissionsMap = new HashMap<>();
        for (PermissionAttachment attachment : PermsManager.getPlayerPermissions(event.getPlayer().getUniqueId())) {
            permissionsMap.putAll(attachment.getPermissions());
        }

        File file = new File(Myperms.getInstance().getDataFolder(), "permissions.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set(event.getPlayer().getUniqueId().toString(), permissionsMap);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PermsManager.freeSpace(event.getPlayer());
    }

}
