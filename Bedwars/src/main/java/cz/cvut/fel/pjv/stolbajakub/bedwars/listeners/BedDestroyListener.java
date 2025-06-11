package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners;

import cz.cvut.fel.pjv.stolbajakub.bedwars.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import static org.bukkit.Bukkit.getServer;
/**
 * Listens for bed destruction events.
 */
public class BedDestroyListener implements Listener {
    /**
     * Responds to bed block destruction events to enforce game rules regarding bed destruction.
     * Prevents players from destroying their own team's bed and handles the bed destruction consequences for opposing teams.
     *
     * @param event The block break event that is fired when a player breaks a block.
     */

    @EventHandler
    public void onBedDestroy(BlockBreakEvent event){
        Player player = event.getPlayer();
        Material destroyed = event.getBlock().getType();
        String teamName = TeamManager.getInstance().getTeamNameForPlayer(player);

        if (teamName != null) {
            if ((destroyed == Material.RED_BED && teamName.equals("Tým Červený")) ||
                    (destroyed == Material.BLUE_BED && teamName.equals("Tým Modrý")) ||
                    (destroyed == Material.GREEN_BED && teamName.equals("Tým Zelení")) ||
                    (destroyed == Material.YELLOW_BED && teamName.equals("Tým Žlutí"))) {
                player.sendMessage(ChatColor.RED + "Nemůžeš zničit vlastní postel!");
                event.setCancelled(true);
            } else {
                handleBedDestruction(destroyed);
                if (destroyed == Material.RED_BED || destroyed == Material.BLUE_BED ||
                        destroyed == Material.GREEN_BED || destroyed == Material.YELLOW_BED) {
                    event.setDropItems(false);
                }
            }
        }
    }
    /**
     * Handles the logic for when a bed belonging to a team is destroyed.
     * Disables the bed for the team and broadcasts a message to all players about the bed destruction.
     *
     * @param destroyed The type of bed block that was destroyed.
     */
    private void handleBedDestruction(Material destroyed) {
        switch (destroyed) {
            case RED_BED:
                TeamManager.getInstance().setHasBedRed(false);
                broadcastBedDestruction("Červená");
                break;
            case BLUE_BED:
                TeamManager.getInstance().setHasBedBlue(false);
                broadcastBedDestruction("Modrá");
                break;
            case GREEN_BED:
                TeamManager.getInstance().setHasBedGreen(false);
                broadcastBedDestruction("Zelená");
                break;
            case YELLOW_BED:
                TeamManager.getInstance().setHasBedYellow(false);
                broadcastBedDestruction("Žlutá");
                break;
            default:
                break;
        }
    }
    /**
     * Broadcasts a server-wide message announcing the destruction of a team's bed.
     *
     * @param teamColorName The name of the team whose bed was destroyed.
     */
    private void broadcastBedDestruction(String teamColorName) {
        ChatColor teamColor = getTeamChatColor(teamColorName);
        getServer().broadcastMessage(teamColor +teamColorName+ " postel zničena");
    }
    /**
     * Retrieves the chat color associated with a team name.
     *
     * @param teamColorName The name of the team whose chat color is to be retrieved.
     * @return The ChatColor associated with the given team name.
     */
    private ChatColor getTeamChatColor(String teamColorName) {
        switch (teamColorName) {
            case "Červená":
                return ChatColor.DARK_RED;
            case "Modrá":
                return ChatColor.BLUE;
            case "Zelená":
                return ChatColor.DARK_GREEN;
            case "Žlutá":
                return ChatColor.YELLOW;
            default:
                return ChatColor.WHITE;
        }
    }
}
