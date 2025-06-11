package cz.cvut.fel.pjv.stolbajakub.bedwars.runnabels;

import cz.cvut.fel.pjv.stolbajakub.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * This class provides functionality to continuously maintain daytime and clear weather conditions in all server worlds.
 */
public class ControlWeather {

    /**
     * Schedules a repeating task to set the time to daytime and ensure clear weather conditions in every world managed by the server.
     * The task is executed every 5 seconds to reset the world time to 6000 ticks (daytime) and set the weather to clear for a short duration.
     */
    public void keepDaytimeAndClearWeather() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Bedwars.getInstance(), () -> {
            for (World world : Bukkit.getServer().getWorlds()) {
                world.setTime(6000); // Set time to daytime
                world.setClearWeatherDuration(12000); // Ensure weather is clear for the next 10 minutes
            }
        }, 0L, 100L); // Schedule the task to run every 5 seconds (100 ticks)
    }
}
