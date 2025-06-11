package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners;

import cz.cvut.fel.pjv.stolbajakub.bedwars.TeamManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerQuitEvent;
/**
 * Listener class for handling player quit events.
 */
public class PlayerQuitListener implements Listener {
    /**
     * Removes the player from their team if they are still in a team when they quit.
     * @param event PlayerQuitEvent instance.
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {

        String team = TeamManager.getInstance().getTeamNameForPlayer(event.getPlayer());
        if (team !=null){
            TeamManager.getInstance().removePlayerFromTeam(event.getPlayer(), team);
        }
    }
}
