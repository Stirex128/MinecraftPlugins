package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.helpers;

import cz.cvut.fel.pjv.stolbajakub.bedwars.Bedwars;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.bukkit.Bukkit.getLogger;
/**
 * Handles inventory click events for secondary GUIs, managing item purchases based on configured shop settings.
 */
public class SecondaryGUIListeners implements Listener {
    /**
     * Processes inventory click events to handle item purchases from GUI-based shops.
     * Checks if the clicked inventory is a shop, cancels default event behavior, and processes the purchase if conditions are met.
     *
     * @param event The event that is triggered when an inventory is clicked.
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        String inventoryName = event.getView().getTitle();

        List<String> shopNames = getShopNames();
        if (!shopNames.contains(inventoryName)) return;

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta() || !clickedItem.getItemMeta().hasLore()) return;

        PlayerInventory inventory = player.getInventory();
        List<String> lore = clickedItem.getItemMeta().getLore();
        if (lore == null || lore.size() < 2) return;

        String currencyLine = lore.get(lore.size() - 1);
        String[] parts = currencyLine.split(": ");
        if (parts.length < 2) return;

        Material currencyMaterial = Material.getMaterial(parts[0]);
        int price;
        try {
            price = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return;
        }

        if (inventory.contains(currencyMaterial, price)) {
            inventory.removeItem(new ItemStack(currencyMaterial, price));

            ItemStack itemToGive = clickedItem.clone();
            ItemMeta meta = itemToGive.getItemMeta();
            if (meta != null) {
                meta.setLore(null);
                itemToGive.setItemMeta(meta);
            }
            inventory.addItem(itemToGive);

            player.sendMessage(ChatColor.GREEN + "Úspěšně jste zakoupil " + itemToGive.getType().toString());
        } else {
            player.sendMessage(ChatColor.RED + "Nemáte dostatek měny (" + currencyMaterial.toString() + ") pro tento nákup.");
        }
    }
    File file = new File(Bedwars.getInstance().getDataFolder(), "shop.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    /**
     * Retrieves the names of all shops configured in the 'shop.yml' file.
     *
     * @return A list of names representing each shop available.
     */
    public List<String> getShopNames() {
        List<String> names = new ArrayList<>();
        if (config == null) {
            getLogger().warning("Konfigurační soubor nebyl načten správně.");
            return names;
        }

        Set<String> keys = config.getConfigurationSection("items").getKeys(false);
        for (String key : keys) {
            String name = config.getString("items." + key + ".name");
            if (name != null && !name.isEmpty()) {
                names.add(name);
            }
        }

        return names;
    }
}
