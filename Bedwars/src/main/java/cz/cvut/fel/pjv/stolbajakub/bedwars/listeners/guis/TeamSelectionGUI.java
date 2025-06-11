package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis;

import cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.helpers.BedSetup;
import cz.cvut.fel.pjv.stolbajakub.bedwars.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Listener class for handling team selection through a GUI in the BedWars game.
 * This class responds to player interactions and updates team assignments accordingly.
 */
public class TeamSelectionGUI implements Listener {
    private static final Map<Material, String> teamMap = new HashMap<>();
    static {
        teamMap.put(Material.BLUE_BED, "Tým Modrý");
        teamMap.put(Material.RED_BED, "Tým Červený");
        teamMap.put(Material.GREEN_BED, "Tým Zelení");
        teamMap.put(Material.YELLOW_BED, "Tým Žlutí");
    }

    /**
     * Handles the event where a player right-clicks with a book to open the team selection GUI.
     * This method sets up the GUI with bed icons representing the available teams.
     *
     * @param event The player interaction event that triggers opening the team selection GUI.
     */
    @EventHandler
    public void onPlayerRightClickWithBook(PlayerInteractEvent event) {
        //aby to vnímalo otevření jed daného menu
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemInHand = event.getItem();
            if (itemInHand != null && itemInHand.getType() == Material.BOOK) {
                String title = ChatColor.GOLD.toString() + ChatColor.BOLD + "Výběr týmů";
                Inventory inventory = Bukkit.createInventory(event.getPlayer(), 9, title);
                BedSetup.initBeds(inventory);
                event.getPlayer().openInventory(inventory);
            }
        }
    }

    /**
     * Handles clicks within the team selection GUI to manage team assignments.
     * Prevents item movement within the GUI and processes team selections based on the item clicked.
     *
     * @param event The inventory click event within the team selection GUI.
     */

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem.getType() == Material.COMPASS ||
                clickedItem.getType() == Material.BOOK ||
                clickedItem.getType() == Material.YELLOW_BED ||
                clickedItem.getType() == Material.RED_BED ||
                clickedItem.getType() == Material.GREEN_BED ||
                clickedItem.getType() == Material.BLUE_BED) {
            event.setCancelled(true);
        }

        String title = event.getView().getTitle();
        String cleanTitle = removeColorCodes(title);

        if (cleanTitle.equals("Výběr týmů")) {
            //getLogger().info("tu");
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        Material clickedMaterial = event.getCurrentItem().getType();
        Player player = (Player) event.getWhoClicked();

        if (teamMap.containsKey(clickedMaterial)) {
            String teamName = teamMap.get(clickedMaterial);
            if (TeamManager.getInstance().hasTeam(player)) {
                TeamManager.getInstance().switchTeams(player, teamName);
                player.sendMessage("Přestoupili jste do týmu: " + teamName);

            } else if (TeamManager.getInstance().addPlayerToTeam(player, teamName)) {
                player.sendMessage("Jste v týmu: " + teamName);
            }
        }
        }
        //potenciální problém koukat jestli jde o správný inevtář


    }
    /**
     * Removes color codes from the string using regular expressions.
     * This method is used to clean Minecraft color codes from GUI titles.
     *
     * @param input The string from which color codes should be removed.
     * @return The cleaned string without color codes.
     */
    private String removeColorCodes(String input) {
        final Pattern colorCodePattern = Pattern.compile("(?i)§[0-9A-FK-OR]");
        return colorCodePattern.matcher(input).replaceAll("");
    }

}
