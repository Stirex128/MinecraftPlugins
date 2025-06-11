package cz.cvut.fel.pjv.stolbajakub.bedwars.runnabels;

import cz.cvut.fel.pjv.stolbajakub.bedwars.Bedwars;
import cz.cvut.fel.pjv.stolbajakub.bedwars.SettingsLoader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

import static org.bukkit.Bukkit.getLogger;

/**
 * Manages the scheduled spawning of items in specific locations within the game world.
 * This class is designed to facilitate dynamic item drops, enhancing gameplay by providing resources or equipment at fixed intervals.
 */
public class Droppers {
    private Droppers(){
    }
    private static BukkitRunnable itemSpawnTask = null;


    /**
     * Initializes and starts a scheduled task for item spawning at configured locations.
     * Ensures that only one instance of the task is active at any time.
     */
    public static void startItemSpawnScheduler() {
        if (itemSpawnTask != null) { // Zkontroluj, zda již task běží
            return;
        }
        itemSpawnTask = new BukkitRunnable() {
            @Override
            public void run() {
                World world = Bukkit.getServer().getWorld(SettingsLoader.getInstance().getWorld());
                if (world == null) {
                    getLogger().warning("Svět pro spawnování itemů nebyl nalezen!");
                    return;
                }
                for (Map<String, Object> dropper : SettingsLoader.getInstance().getDroppers()) {
                    Material itemMaterial = Material.getMaterial((String) dropper.get("item"));
                    if (itemMaterial == null) {getLogger().info((String)dropper.get("item"));
                        continue;}

                    int amount = (int) dropper.get("amount");
                    double x = (double) dropper.get("x");
                    double y = (double) dropper.get("y");
                    double z = (double) dropper.get("z");
                    Location location = new Location(world, x, y, z);

                    ItemStack itemStack = new ItemStack(itemMaterial, amount);
                    world.dropItemNaturally(location, itemStack);

                }
            }
        };
        itemSpawnTask.runTaskTimer(Bedwars.getInstance(), 0, 20 * 10);
    }

    /**
     * Terminates the item spawning task if it is currently active, preventing any further item drops.
     */
    public static void stopItemSpawnScheduler() {
        if (itemSpawnTask != null) {
            itemSpawnTask.cancel();
            //getLogger().info("zruseno dropovani");
            itemSpawnTask = null;
        }
    }
}
