package cz.cvut.fel.pjv.stolbajakub.listeners;


import cz.cvut.fel.pjv.stolbajakub.SettingsLoader;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Bukkit.getServer;
import static org.bukkit.enchantments.Enchantment.LUCK;
/**
 * Listener for player join events.
 * This class handles tasks that should occur when a player joins the server.
 */
public class PlayerJoinListener implements Listener {
    /**
     * Performs tasks when a player joins the server.
     * Teleports the player to the spawn location, gives them a menu item,
     * and plays a sound effect if they do not have a specific permission.
     * @param event The PlayerJoinEvent.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location spawnLocation = new Location(getServer().getWorld("world"),
                SettingsLoader.getInstance().getSpawnX(),
                SettingsLoader.getInstance().getSpawnY(),
                SettingsLoader.getInstance().getSpawnZ());
        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemMeta diamondMeta = diamond.getItemMeta();
        if (diamondMeta != null) {
            diamondMeta.addEnchant(LUCK, 0, true);
            diamondMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            diamondMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Menu");
            diamond.setItemMeta(diamondMeta);
        }

        player.getInventory().setItem(4, diamond);
        if(!event.getPlayer().hasPermission("lobby.not.spawn.on.spawn"))
        {player.teleport(spawnLocation);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1.0F, 1.0F);
        }

    }
}
