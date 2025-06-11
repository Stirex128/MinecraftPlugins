package cz.cvut.fel.pjv.stolbajakub.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
/**
 * Listener for inventory lock events.
 * This class prevents players from interacting with their inventory or dropping items based on permissions.
 */

public class InventoryLock implements Listener {
    /**
     * Locks the inventory to prevent interaction if the player doesn't have the required permission.
     * @param event The InventoryClickEvent.
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!event.getWhoClicked().hasPermission("lobby.edit.inventory")) {
            event.setCancelled(true);
        }
    }

    /**
     * Prevents players from dropping items if they don't have the required permission.
     * @param event The PlayerDropItemEvent.
     */
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {

        if (!event.getPlayer().hasPermission("lobby.edit.inventory")) {
            event.setCancelled(true);
        }
    }
}
