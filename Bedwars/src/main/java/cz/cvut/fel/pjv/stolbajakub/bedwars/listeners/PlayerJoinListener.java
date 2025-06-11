package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners;

import cz.cvut.fel.pjv.stolbajakub.bedwars.SettingsLoader;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.enchantments.Enchantment.LUCK;
/**
 * Listens for player join events and sets initial settings for players.
 */
public class PlayerJoinListener implements Listener {
    /**
     * Sets initial settings for players when they join the server.
     *
     * @param event The PlayerJoinEvent instance.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compasMeta = compass.getItemMeta();
        if (compasMeta != null) {
        compasMeta.addEnchant(LUCK, 0, true);
        compasMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        compasMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Leave to Lobby");
        compass.setItemMeta(compasMeta);
    }

        player.getInventory().setItem(8, compass);
        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta bookMeta = book.getItemMeta();
        if (bookMeta != null) {
            bookMeta.addEnchant(LUCK, 0, true);
            bookMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            bookMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Vyber s tým");
            book.setItemMeta(bookMeta);
        }

        player.getInventory().setItem(0, book);
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.teleport(SettingsLoader.getInstance().getSpawnLocation());
    }

}
