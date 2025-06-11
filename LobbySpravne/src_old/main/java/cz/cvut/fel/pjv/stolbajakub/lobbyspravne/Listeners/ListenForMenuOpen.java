package cz.cvut.fel.pjv.stolbajakub.lobbyspravne.Listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cz.cvut.fel.pjv.stolbajakub.lobbyspravne.Arena;
import cz.cvut.fel.pjv.stolbajakub.lobbyspravne.ArenaManager;
import cz.cvut.fel.pjv.stolbajakub.lobbyspravne.LobbySpravne;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ListenForMenuOpen implements Listener {
    @EventHandler
    public void onPlayerRightClickWithDiamond(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemInHand = event.getItem();
            if (itemInHand != null && itemInHand.getType() == Material.DIAMOND) {
                Inventory inventory = Bukkit.createInventory(event.getPlayer(), 3*9, "Menu");
                ItemStack blueBed = new ItemStack(Material.BLUE_BED);
                ItemMeta bedMeta = blueBed.getItemMeta();
                if (bedMeta != null) {
                    bedMeta.setDisplayName(ChatColor.GRAY + "Připojit k BedWars");
                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.WHITE + "Připoj se na nasš skvělé bedwars.");
                    bedMeta.setLore(lore);
                    blueBed.setItemMeta(bedMeta);

                }
                inventory.setItem(13, blueBed);
                event.getPlayer().openInventory(inventory);
            }
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getWhoClicked().hasPermission("lobby.edit.inventory")) {
            event.setCancelled(true);
        }
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem.getType() == Material.BLUE_BED && clickedItem.hasItemMeta() && clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Připojit k BedWars")) {
            // Zavolání metody pro otevření arénového menu
            openArenaMenu((Player) event.getWhoClicked());
            event.setCancelled(true);
        }
    }
    /*@EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
            if(!event.getWhoClicked().hasPermission("lobby.edit.inventory")){
                event.setCancelled(true);
            }
            //potenciální problém koukat jestli jde o správný inevtář
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem.getType() == Material.BLUE_BED && clickedItem.hasItemMeta() && clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Připojit k BedWars")) {
                Player player = (Player) event.getWhoClicked();
                player.sendMessage(ChatColor.GREEN + "Připojování k serveru BedWars...");
                switchServer(player, "minigame");
            }

    }*/
    public void switchServer(Player player, String serverName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serverName);
        player.sendPluginMessage(LobbySpravne.getInstance(), "BungeeCord", out.toByteArray());
    }
    public void openArenaMenu(Player player) {
        Inventory arenaMenu = Bukkit.createInventory(null, 9, ChatColor.DARK_PURPLE + "Vyber arénu"); // Velikost inventory závisí na počtu arén

        List<Arena> arenas = ArenaManager.getInstance().getArenas();
        for (Arena arena : arenas) {
            ItemStack arenaItem = new ItemStack(Material.IRON_SWORD);
            ItemMeta meta = arenaItem.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.GREEN + arena.getName());
                List<String> lore = new ArrayList<>();
                lore.add("Aréna č. " + arena.getNumOfArena());
                lore.add("Aktuální hráči: " + arena.getCurrPlays());
                lore.add("Maximální hráči: " + arena.getMaxPlays());
                lore.add("Stav: " + (arena.isOn() ? ChatColor.GREEN + "Hraje" : ChatColor.RED + "Čeká na hráče"));
                meta.setLore(lore);
                arenaItem.setItemMeta(meta);
            }
            arenaMenu.addItem(arenaItem);
        }

        player.openInventory(arenaMenu);
    }
}
