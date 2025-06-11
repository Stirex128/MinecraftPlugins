package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
/**
 * Listens for damage events to villagers and cancels the damage to protect them.
 */
public class VillagerProtectionListener implements Listener {
    /**
     * Protects villagers from damage.
     *
     * @param event The EntityDamageByEntityEvent instance.
     */
    @EventHandler
    public void onVillagerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Villager) {
            event.setCancelled(true);
        }
    }
}
