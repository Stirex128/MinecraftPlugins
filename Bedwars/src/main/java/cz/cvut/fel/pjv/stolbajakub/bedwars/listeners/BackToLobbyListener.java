package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cz.cvut.fel.pjv.stolbajakub.bedwars.Bedwars;
import cz.cvut.fel.pjv.stolbajakub.bedwars.bungeecommunication.Arena;
import cz.cvut.fel.pjv.stolbajakub.bedwars.bungeecommunication.BungeeSendLobbyArenaInfo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
/**
 * Listens for player interactions that indicate a desire to return to the main lobby, typically triggered by using a compass.
 * This class handles the redirection of players back to the lobby server via BungeeCord.
 */
public class BackToLobbyListener implements Listener {
    BungeeSendLobbyArenaInfo arenaInfoSender = new BungeeSendLobbyArenaInfo();
    /**
     * Processes player interactions specifically looking for right-click actions with a compass to send the player back to the lobby.
     * This method reduces the current player count in the arena and updates the arena info across the network before switching the server.
     *
     * @param event The event triggered by player interaction, which includes details about the action and the item used.
     */

    @EventHandler
    public void OnCompassClick(PlayerInteractEvent event){
        Arena.getInstance().setCurrPlays(Arena.getInstance().getCurrPlays()-1);
        arenaInfoSender.sendBungeecordArenaInfo(Arena.getInstance());
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemInHand = event.getItem();
            if (itemInHand != null && itemInHand.getType() == Material.COMPASS) {
                switchServer(event.getPlayer(), "lobby");
            }
        }

    }
    /**
     * Switches the player to a different server within the BungeeCord network, specified by the server name.
     * This method creates and sends a plugin message to the BungeeCord channel to transfer the player.
     *
     * @param player The player to be transferred.
     * @param serverName The name of the server to which the player will be transferred, typically "lobby".
     */

    private void switchServer(Player player, String serverName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serverName);
        player.sendPluginMessage(Bedwars.getInstance(), "BungeeCord", out.toByteArray());
    }
}
