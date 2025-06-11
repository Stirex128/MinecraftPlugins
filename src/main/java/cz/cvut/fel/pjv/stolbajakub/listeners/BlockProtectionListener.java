package cz.cvut.fel.pjv.stolbajakub.listeners;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class BlockProtectionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockDestroy(BlockBreakEvent event){
        Player placer = event.getPlayer();
        if (!placer.hasPermission("lobby.break.block")){
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockDestroy(BlockPlaceEvent event){
        Player placer = event.getPlayer();
        if (!placer.hasPermission("lobby.place.block")){
            event.setCancelled(true);
        }
    }
}
