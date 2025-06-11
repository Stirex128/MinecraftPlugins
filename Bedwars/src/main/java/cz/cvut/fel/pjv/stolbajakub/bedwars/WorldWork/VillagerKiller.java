package cz.cvut.fel.pjv.stolbajakub.bedwars.WorldWork;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.World;

/**
 * This class provides a method to remove all villager entities from a specified world.
 * It is designed to be used statically.
 */
public class VillagerKiller {

    // Private constructor to prevent instantiation
    private VillagerKiller() {
    }

    /**
     * Removes all villagers from the specified world.
     * This method iterates over all entities in the world and removes those that are villagers.
     *
     * @param world The world from which all villagers will be removed.
     */
    public static void killAllVillagers(World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getType() == EntityType.VILLAGER) {
                entity.remove();
            }
        }
    }
}