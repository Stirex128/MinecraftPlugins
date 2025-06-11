package cz.cvut.fel.pjv.stolbajakub.lobbyspravne;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class HungerControl {

    private JavaPlugin plugin;

    public HungerControl(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void preventHunger() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.setFoodLevel(20);
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }
}
