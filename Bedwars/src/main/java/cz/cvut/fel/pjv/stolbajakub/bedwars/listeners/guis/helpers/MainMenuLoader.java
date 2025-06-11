package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.helpers;

import cz.cvut.fel.pjv.stolbajakub.bedwars.Bedwars;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * Handles loading of menu items from a configuration file into the main GUI of the Bedwars game.
 * This class facilitates dynamic configuration of the items displayed in the main menu.
 */
public class MainMenuLoader {
    /**
     * Loads menu items from the "shop.yml" configuration file.
     * Each item is represented by a name and an icon, and is encapsulated in a {@link MainMenuItem} object.
     *
     * @return A list of {@link MainMenuItem} objects, each representing an item in the main GUI.
     */
    public List<MainMenuItem> loadItems() {
        File file = new File(Bedwars.getInstance().getDataFolder(), "shop.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<MainMenuItem> itemsList = new ArrayList<>();

        ConfigurationSection itemsSection = config.getConfigurationSection("items");
        if (itemsSection == null) return itemsList;

        Set<String> keys = itemsSection.getKeys(false);
        for (String key : keys) {
            String name = itemsSection.getString(key + ".name");
            String icon = itemsSection.getString(key + ".icon");

            MainMenuItem item = new MainMenuItem(name, icon);
            itemsList.add(item);
        }

        return itemsList;
    }
}
