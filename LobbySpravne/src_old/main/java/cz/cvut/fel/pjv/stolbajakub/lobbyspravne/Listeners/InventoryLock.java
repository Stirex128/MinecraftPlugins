package cz.cvut.fel.pjv.stolbajakub.lobbyspravne.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class InventoryLock implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!event.getWhoClicked().hasPermission("lobby.edit.inventory")) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {

        if (!event.getPlayer().hasPermission("lobby.edit.inventory")) {
            event.setCancelled(true);
        }
    }
}
