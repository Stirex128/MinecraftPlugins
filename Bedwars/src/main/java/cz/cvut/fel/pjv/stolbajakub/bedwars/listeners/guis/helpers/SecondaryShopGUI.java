package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.helpers;

import cz.cvut.fel.pjv.stolbajakub.bedwars.Bedwars;
import cz.cvut.fel.pjv.stolbajakub.bedwars.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * Manages the creation and display of secondary shop GUIs for players based on their team and specific shop file configuration.
 */
public class SecondaryShopGUI {
    /**
     * Loads a GUI for a specific player based on a GUI file. It populates the GUI with items that are specific to the player's team or general items available to all.
     *
     * @param guiName the name of the GUI file (without extension) that contains the configuration of the GUI.
     * @param player the player for whom the GUI is being loaded.
     */
    public void loadGui(String guiName, Player player) {
        File file = new File(Bedwars.getInstance().getDataFolder(), guiName + ".yml");
        if (!file.exists()) {
            player.sendMessage("GUI soubor nebyl nalezen.");
            return;
        }
        String playerTeam = TeamManager.getInstance().getTeamNameForPlayer(player);

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        Inventory gui = Bukkit.createInventory(player, 9 * 3, guiName);


        ConfigurationSection itemsSection = config.getConfigurationSection("items");
        if (itemsSection == null) {
            player.sendMessage("V GUI souboru nejsou definovány žádné položky.");
            return;
        }

        Set<String> items = itemsSection.getKeys(false);
        for (String itemKey : items) {
            String team = itemsSection.getString(itemKey + ".team");
            if (!team.equals(playerTeam) && !team.equals("None")) continue;
            Material material = Material.getMaterial(itemsSection.getString(itemKey + ".item").toUpperCase());
            if (material == null) continue;

            int amount = itemsSection.getInt(itemKey + ".amount", 1);
            ItemStack item = new ItemStack(material, amount);
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                meta.setDisplayName(itemsSection.getString(itemKey + ".name"));

                List<String> lore = new ArrayList<>();
                lore.add(itemsSection.getString(itemKey + ".lore"));
                String currency = itemsSection.getString(itemKey + ".currency");
                int price = itemsSection.getInt(itemKey + ".price");
                lore.add(currency + ": " + price);
                meta.setLore(lore);

                Enchantment enchantment = Enchantment.getByName(itemsSection.getString(itemKey + ".enchantment").toUpperCase());
                if (enchantment != null) {
                    meta.addEnchant(enchantment, itemsSection.getInt(itemKey + ".lvlOfEnchant"), true);
                }

                item.setItemMeta(meta);
            }

            gui.addItem(item);
        }

        player.openInventory(gui);
    }
}
