package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners;

import cz.cvut.fel.pjv.stolbajakub.bedwars.Bedwars;
import cz.cvut.fel.pjv.stolbajakub.bedwars.TeamManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
/**
 * Listens for player death events and removes players from teams if their team's bed is destroyed.
 */
public class PlayerDeathListener implements Listener {
    /**
     * Removes players from teams when they die and their team's bed is destroyed.
     *
     * @param event The PlayerDeathEvent instance.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void OnPlayerDeath(PlayerDeathEvent event){
        Player player = event.getPlayer();
        String team = TeamManager.getInstance().getTeamNameForPlayer(player);

        if (team != null) {
            boolean removePlayer = false;

            switch (team) {
                case "Tým Modrý":
                    removePlayer = !TeamManager.getInstance().isHasBedBlue();
                    break;
                case "Tým Červený":
                    removePlayer = !TeamManager.getInstance().isHasBedRed();
                    break;
                case "Tým Zelení":
                    removePlayer = !TeamManager.getInstance().isHasBedGreen();
                    break;
                case "Tým Žlutí":
                    removePlayer = !TeamManager.getInstance().isHasBedYellow();
                    break;
                default:
                    removePlayer = false;
            }

            if (removePlayer) {
                TeamManager.getInstance().removePlayerFromTeam(player, team);
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
            }
        }.runTaskLater(Bedwars.getInstance(), 20L);
    }
}
