package cz.cvut.fel.pjv.stolbajakub.myperms.listeners;


import cz.cvut.fel.pjv.stolbajakub.myperms.Myperms;
import cz.cvut.fel.pjv.stolbajakub.myperms.PermsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
/**
 * Listener class responsible for granting permissions to players upon login based on data loaded from a YAML file.
 */

public class PlayerJoinListenerPerms implements Listener {
    final String fileInString = "permissions.yml";

    /**
     * Grants permissions to players upon login based on data loaded from a YAML file.
     *
     * @param event The PlayerJoinEvent instance representing the event.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        File file = new File(Myperms.getInstance().getDataFolder(), fileInString);
        if (!file.exists()) {
            Myperms.getInstance().saveResource(fileInString, false);
        }

        try {
            File configFile = new File(Myperms.getInstance().getDataFolder(), fileInString);

            Yaml yaml = new Yaml();
            InputStream inputStream = new FileInputStream(configFile);
            Map<String, Map<String, Boolean>> data = yaml.load(inputStream);

            Map<String, Boolean> finalMap = new HashMap<>();
            for (Map.Entry<String, Map<String, Boolean>> entry : data.entrySet()) {
                finalMap.putAll(entry.getValue());
            }
            PermsManager.loadPermissionsForPlayer(event.getPlayer(), finalMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
