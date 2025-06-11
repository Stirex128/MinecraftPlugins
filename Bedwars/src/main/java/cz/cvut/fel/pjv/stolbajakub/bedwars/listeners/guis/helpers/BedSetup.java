package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.helpers;
import cz.cvut.fel.pjv.stolbajakub.bedwars.SettingsLoader;
import cz.cvut.fel.pjv.stolbajakub.bedwars.TeamManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

import java.util.Collections;

/**
 * Utility class to set up beds in a GUI for different teams in the BedWars game.
 * This class is not meant to be instantiated but provides static methods to manipulate bed items in an inventory.
 */
public class BedSetup {
    private BedSetup(){

    }
    /**
     * Sets up a bed item in an inventory for a specific team.
     *
     * @param inventory The inventory where the bed will be placed.
     * @param bedType The type of bed (Material) representing the team color.
     * @param color The chat color associated with the team.
     * @param teamName The name of the team.
     * @param slot The inventory slot where the bed will be placed.
     */
    private static void setupBed(Inventory inventory, Material bedType, ChatColor color, String teamName, int slot) {
        ItemStack bed = new ItemStack(bedType);
        ItemMeta bedMeta = bed.getItemMeta();

        if (bedMeta != null) {
            bedMeta.setDisplayName(color + teamName);
            bedMeta.setLore(Collections.singletonList(
                    TeamManager.getInstance().getTeamSize(teamName)
                            + "/" +
                            SettingsLoader.getInstance().getMaxInTeam()));
            bed.setItemMeta(bedMeta);
        }

        inventory.setItem(slot, bed);
    }

    /**
     * Initializes the inventory with bed items for each team.
     * This method setups different colored beds for the teams specified and places them in the respective slots.
     *
     * @param inventory The inventory in which the beds are to be initialized.
     */
    public static void initBeds(Inventory inventory) {
        setupBed(inventory, Material.BLUE_BED, ChatColor.BLUE, "Tým Modrý", 0);
        setupBed(inventory, Material.RED_BED, ChatColor.DARK_RED, "Tým Červený", 1);
        setupBed(inventory, Material.GREEN_BED, ChatColor.DARK_GREEN, "Tým Zelení", 2);
        setupBed(inventory, Material.YELLOW_BED, ChatColor.YELLOW, "Tým Žlutí", 3);
    }
}