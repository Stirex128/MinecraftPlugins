package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners;

import cz.cvut.fel.pjv.stolbajakub.bedwars.SettingsTeamRespawnLocation;
import cz.cvut.fel.pjv.stolbajakub.bedwars.SettingsLoader;
import cz.cvut.fel.pjv.stolbajakub.bedwars.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.bukkit.enchantments.Enchantment.LUCK;
/**
 * Listener class for handling team respawn events and managing spectators.
 */

public class TeamRespawnListener implements Listener {

    /**
    * Decides where players should be respawned based on their team.
    * @param event PlayerRespawnEvent instance.
    */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        String team = TeamManager.getInstance().getTeamNameForPlayer(player);

        if (team != null) {

             if ("Tým Modrý".equals(team)&&TeamManager.getInstance().isHasBedBlue()) {
                Location blueTeamRespawn = SettingsTeamRespawnLocation.getInstance().getSpawnLocationBlue();
                event.setRespawnLocation(blueTeamRespawn);}
                else if ("Tým Červený".equals(team)&&TeamManager.getInstance().isHasBedRed()) {
                    Location redTeamRespawn = SettingsTeamRespawnLocation.getInstance().getSpawnLocationRed();
                    event.setRespawnLocation(redTeamRespawn);
            }else if ("Tým Zelení".equals(team)&&TeamManager.getInstance().isHasBedGreen()) {
                    Location greenTeamRespawn = SettingsTeamRespawnLocation.getInstance().getSpawnLocationGreen();
                    event.setRespawnLocation(greenTeamRespawn);
            }else if ("Tým Žlutí".equals(team)&&TeamManager.getInstance().isHasBedYellow()) {
                    Location yellowTeamRespawn = SettingsTeamRespawnLocation.getInstance().getSpawnLocationYellow();
                    event.setRespawnLocation(yellowTeamRespawn);
            }
        }
        else {
            event.setRespawnLocation(SettingsLoader.getInstance().getSpawnLocation());
            enableAdventureModeWithCompass(player);
        }
    }
    private Set<UUID> vanishedPlayers = new HashSet<>();

    /**
     * Prevents spectators from picking up items.
     * @param event EntityPickupItemEvent instance.
     */
    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (vanishedPlayers.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * Removes player from vanished players set upon quitting.
     * @param event PlayerQuitEvent instance.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (vanishedPlayers.contains(event.getPlayer().getUniqueId())) {
            vanishedPlayers.remove(event.getPlayer().getUniqueId());
        }

    }
    /**
     * Enables adventure mode for the player with a compass to leave to the lobby.
     * This method sets the player's game mode to Adventure, allows flight, makes the player fly,
     * adds invisibility potion effect, clears the player's inventory, gives them a compass enchanted with luck,
     * sets its display name, hides enchantments, updates the player's inventory, adds the player to the list of vanished players,
     * and hides the player from all other online players.
     * @param player The player to enable adventure mode for.
     */
    private void enableAdventureModeWithCompass(Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
        player.setFlying(true);
        PlayerInventory inventory = player.getInventory();
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, Integer.MAX_VALUE, false, false, false));
        inventory.clear();
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compasMeta = compass.getItemMeta();
        if (compasMeta != null) {
            compasMeta.addEnchant(LUCK, 0, true);
            compasMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            compasMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Leave to Lobby");
            compass.setItemMeta(compasMeta);
        }
        inventory.setItem(0, compass);
        player.updateInventory();
        vanishedPlayers.add(player.getUniqueId());
        for (Player otherPlayer : Bukkit.getServer().getOnlinePlayers()) {
            if (otherPlayer != player) {
                otherPlayer.hidePlayer(player);
            }
        }
    }

}
