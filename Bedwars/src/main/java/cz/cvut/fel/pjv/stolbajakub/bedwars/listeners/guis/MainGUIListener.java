package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis;

import cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.helpers.MainMenuItem;
import cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.helpers.MainMenuLoader;
import cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.helpers.SecondaryShopGUI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
/**
 * Handles inventory clicks within the main GUI named "Obchod", directing players to secondary shop GUIs based on the item clicked.
 */
public class MainGUIListener implements Listener {
    /**
     * Handles inventory clicks in the main shop GUI. It checks if the clicked item corresponds to an item defined in the menu
     * and, if so, opens a secondary GUI related to the clicked item.
     *
     * @param event The event that occurs when an item in an inventory is clicked by a player.
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null || !(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        String inventoryName = event.getView().getTitle();
        if (!inventoryName.equals("Obchod")) return;

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

        MainMenuLoader loader = new MainMenuLoader();
        List<MainMenuItem> items = loader.loadItems();

        for (MainMenuItem item : items) {
            if (item.getName().equals(itemName)) {
                SecondaryShopGUI secodaryGUI = new SecondaryShopGUI();
                secodaryGUI.loadGui(item.getName(), player);
                break;
            }
        }
    }
}
