package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.EnumSet;
/**
 * Listens for block break events to prevent unauthorized destruction of certain block types in the game,
 * ensuring gameplay mechanics like base protection are enforced.
 */
public class BlockProtectionListener implements Listener {
    private final EnumSet<Material> allowedMaterials = EnumSet.of(
            Material.RED_BED, Material.BLUE_BED, Material.GREEN_BED, Material.YELLOW_BED,
            Material.RED_WOOL, Material.BLUE_WOOL, Material.GREEN_WOOL, Material.YELLOW_WOOL,
            Material.OBSIDIAN, Material.END_STONE
    );

    /**
     * Prevents the breaking of certain critical blocks unless the player has specific permissions.
     * This method checks if the block being broken is part of the allowed materials list or if the player has the 'bw.building' permission.
     *
     * @param event The block break event that is triggered when a player attempts to break a block.
     */
    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event){
        if (!allowedMaterials.contains(event.getBlock().getType())&&!event.getPlayer().hasPermission("bw.building")) {
            event.setCancelled(true);
        }

    }
}
