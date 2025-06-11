package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners;
import cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.helpers.MainMenuItem;
import cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.helpers.MainMenuLoader;
import cz.cvut.fel.pjv.stolbajakub.bedwars.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
/**
 * Listens for interactions with Villagers and loads the shop GUI if the player interacts with one.
 */
public class ClickOnVillagerListener implements Listener {
    /**
     * Loads the shop GUI when a player interacts with a Villager.
     *
     * @param event The PlayerInteractEntityEvent instance.
     */
    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager) {
            Player player = event.getPlayer();
            String teamName = TeamManager.getInstance().getTeamNameForPlayer(player);

            if (teamName != null) {
                MainMenuLoader loader = new MainMenuLoader();
                List<MainMenuItem> items = loader.loadItems();

                Inventory Maingui = Bukkit.createInventory(null, 9, "Obchod");

                for (MainMenuItem item : items) {
                    Material material = Material.getMaterial(item.getIcon().toUpperCase());
                    if (material != null) {
                        ItemStack stack = new ItemStack(material, 1);
                        ItemMeta meta = stack.getItemMeta();
                        meta.setDisplayName(item.getName());
                        stack.setItemMeta(meta);

                        Maingui.addItem(stack);
                    }
                }
                player.openInventory(Maingui);
            } else {
                player.sendMessage("Nejste součástí žádného týmu!");
            }
        }
    }


}
