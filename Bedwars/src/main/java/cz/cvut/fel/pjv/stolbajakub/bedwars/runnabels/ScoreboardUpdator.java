package cz.cvut.fel.pjv.stolbajakub.bedwars.runnabels;

import cz.cvut.fel.pjv.stolbajakub.bedwars.Bedwars;
import cz.cvut.fel.pjv.stolbajakub.bedwars.TeamManager;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getLogger;

/**
 * The {@code ScoreboardUpdator} class is responsible for periodically updating the scoreboard
 * for all players in the Bedwars game. It uses the Bukkit scheduler to run the update task
 * at regular intervals.
 */
public class ScoreboardUpdator {
    /**
     * Schedules the scoreboard update task to run repeatedly with a fixed delay. This ensures
     * that the scoreboard is kept up to date with the latest game information, such as team statuses
     * and bed statuses.
     */
    public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bedwars.getInstance(), this::update, 0L, 20L);

    }
    /**
     * The update method that is called at each scheduled interval. It invokes the {@code updateTeamScoreboard}
     * method from the {@code TeamManager} class to refresh the scoreboard information for all online players.
     */
    private void update() {
        TeamManager.getInstance().updateTeamScoreboard();

    }
}
