package cz.cvut.fel.pjv.stolbajakub.listeners;

import cz.cvut.fel.pjv.stolbajakub.Lobby;
import cz.cvut.fel.pjv.stolbajakub.PermsManager;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerQuitListener implements Listener {

    private Lobby plugin;

    public PlayerQuitListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Map<String, Boolean> permissionsMap = new HashMap<>();
        for (PermissionAttachment attachment : PermsManager.getPlayerPermissions(event.getPlayer().getUniqueId())) {
            permissionsMap.putAll(attachment.getPermissions());
        }


        File file = new File(Lobby.getInstance().getDataFolder(), "permissions.yml");
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
