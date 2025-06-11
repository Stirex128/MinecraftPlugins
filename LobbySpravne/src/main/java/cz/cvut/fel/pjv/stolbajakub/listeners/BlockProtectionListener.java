package cz.cvut.fel.pjv.stolbajakub.listeners;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Listener for block protection events.
 * This class prevents players from breaking or placing blocks based on permissions.
 */

public class BlockProtectionListener implements Listener {
    /**
     * Prevents players from breaking blocks if they don't have the required permission.
     * @param event The BlockBreakEvent.
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockDestroy(BlockBreakEvent event){
        Player placer = event.getPlayer();
        if (!placer.hasPermission("lobby.break.block")){
            event.setCancelled(true);
        }
    }

    /**
     * Prevents players from placing blocks if they don't have the required permission.
     * @param event The BlockPlaceEvent.
     */

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event){
        Player placer = event.getPlayer();
        if (!placer.hasPermission("lobby.place.block")){
            event.setCancelled(true);
        }
    }
}
