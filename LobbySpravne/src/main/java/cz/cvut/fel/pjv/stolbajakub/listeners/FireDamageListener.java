package cz.cvut.fel.pjv.stolbajakub.listeners;


import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
/**
 * Prevents players from receiving fire damage.
 */

public class FireDamageListener implements Listener {
    /**
     * Prevents players from receiving fire damage.
     *
     * @param event The event representing the entity damage.
     */
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.FIRE) {
            event.setCancelled(true);
        }
    }
}
