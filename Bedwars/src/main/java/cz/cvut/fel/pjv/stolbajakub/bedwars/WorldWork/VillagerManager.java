package cz.cvut.fel.pjv.stolbajakub.bedwars.WorldWork;

import cz.cvut.fel.pjv.stolbajakub.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import java.io.File;
import java.util.Set;

import static org.bukkit.Bukkit.getLogger;

/**
 * Manages and creates villager shops for the Bedwars game.
 */
public class VillagerManager {
    /**
     * Creates villager shops based on configuration specified in the settings.yml file.
     * It reads configuration, checks if it exists, and creates villager NPCs at specified locations.
     */
    public void createShopVillagers() {
        File file = new File(Bedwars.getInstance().getDataFolder(), "settings.yml");
        if (!file.exists()) {
            Bedwars.getInstance().saveResource("settings.yml", false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection villagersSection = config.getConfigurationSection("Bedwars.Villagers");
        if (villagersSection == null) {
            getLogger().warning("Villagers section not found in configuration file!");
            return;
        }

        Set<String> villagersKeys = villagersSection.getKeys(false);
        for (String key : villagersKeys) {
            String name = villagersSection.getString(key + ".Name");
            World world = Bukkit.getWorld("world");
            int x = villagersSection.getInt(key + ".x");
            int y = villagersSection.getInt(key + ".y");
            int z = villagersSection.getInt(key + ".z");
            createShopVillager(world, x, y, z, name);
        }
    }

    /**
     * Helper method to create a single shop villager at a specified location.
     * @param world The world in which the villager will be spawned.
     * @param x The x-coordinate of the villager's location.
     * @param y The y-coordinate of the villager's location.
     * @param z The z-coordinate of the villager's location.
     * @param name The custom name to be assigned to the villager.
     */
    private void createShopVillager(World world, double x, double y, double z, String name) {
        Location location = new Location(world, x, y, z);
        Villager villager = (Villager) world.spawnEntity(location, EntityType.VILLAGER);
        villager.setAI(false);
        villager.setCustomName(name);
        villager.setCustomNameVisible(true);
    }
}