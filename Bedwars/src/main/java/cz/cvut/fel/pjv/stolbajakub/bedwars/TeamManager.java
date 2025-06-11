package cz.cvut.fel.pjv.stolbajakub.bedwars;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Manages teams within the game, such as adding and removing players to teams, setting up team properties, and managing team beds' status.
 */
public class TeamManager {
    private static TeamManager instance;
    private Scoreboard scoreboard;
    private boolean hasBedBlue = true;
    private boolean hasBedRed = true;
    private boolean hasBedGreen = true;
    private boolean hasBedYellow = true;
    final String teamName1 = "Tým Červený";
    final String teamName2 = "Tým Modrý";
    final String teamName3 = "Tým Zelení";
    final String teamName4 = "Tým Žlutí";
    final ChatColor teamColour1 = ChatColor.DARK_RED;
    final ChatColor teamColour2 = ChatColor.BLUE;
    final ChatColor teamColour3 = ChatColor.DARK_GREEN;
    final ChatColor teamColour4 = ChatColor.YELLOW;

    private TeamManager() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
            scoreboard = manager.getMainScoreboard();
    }

    /**
     * Provides a synchronized singleton instance of the TeamManager, ensuring that there exists only one instance across the application.
     * @return The single instance of the TeamManager.
     */
    public static synchronized TeamManager getInstance() {
        if (instance == null) {
            instance = new TeamManager();
        }
        return instance;
    }

    /**
     * Sets up teams with predefined names and display properties. This includes setting team colors and team options like friendly fire and tag visibility.
     */
    public void setupTeams() {
        createTeam(teamName1, teamColour1 + teamName1 + ChatColor.RESET);
        createTeam(teamName2, teamColour2 + teamName2 + ChatColor.RESET);
        createTeam(teamName3, teamColour3 + teamName3 + ChatColor.RESET);
        createTeam(teamName4, teamColour4 + teamName4 + ChatColor.RESET);
    }

    private void createTeam(String name, String displayName) {
        Team team = scoreboard.getTeam(name);
        if (team == null) {
            team = scoreboard.registerNewTeam(name);
        }
        ChatColor teamColor = getTeamColor(name);
        assert teamColor != null;
        team.setPrefix(teamColor.toString());
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        team.setDisplayName(displayName);
        team.setAllowFriendlyFire(false);
        team.setCanSeeFriendlyInvisibles(true);

    }

    /**
     * Attempts to add a player to a specified team. Checks if the team is not full based on a configured maximum size.
     * @param player The player to add to a team.
     * @param teamName The name of the team to add the player to.
     * @return True if the player was successfully added, false if the team is full.
     */
    public  boolean addPlayerToTeam(Player player, String teamName) {
        Team team = scoreboard.getTeam(teamName);
        if (team != null&&SettingsLoader.getInstance().getMaxInTeam()>=team.getEntries().size()) {
            team.addEntry(player.getName());
            ChatColor teamColor = getTeamColor(teamName);
            player.setScoreboard(scoreboard);
            if (teamColor != null) {
                NamedTextColor namedColor = NamedTextColor.nearestTo(TextColor.color(teamColor.asBungee().getColor().getRGB()));
                team.setPrefix(teamColor.toString());
                //player.setDisplayName(teamColor + player.getName() + ChatColor.RESET);
                //player.setPlayerListName(teamColor + player.getName() + ChatColor.RESET);
                Component nameTag = Component.text(player.getName())
                        .color(namedColor)
                        .decorate(TextDecoration.BOLD);
                player.displayName(nameTag);
                player.playerListName(nameTag);
                team.setColor(teamColor);
                //team.setPrefix(teamColor.toString()+ " " + teamName);
                //team.setSuffix(teamColor.toString() + "sasdsssa");
                team.setOption(Team.Option.NAME_TAG_VISIBILITY, true ? Team.OptionStatus.ALWAYS : Team.OptionStatus.NEVER);
                team.addEntry(player.getName());
                player.setScoreboard(scoreboard);
                //updatePlayerName(player, "test");

            }
            return true;
        } else {
            player.sendMessage("Tým je již plný");
            return false;}

    }

    /**
     * Removes a player from their current team.
     * @param player The player to remove from the team.
     * @param teamName The name of the team to remove the player from.
     */
    public void removePlayerFromTeam(Player player, String teamName) {
        Team team = scoreboard.getTeam(teamName);
        if (team != null && team.hasEntry(player.getName())) {
            team.removeEntry(player.getName());
        }
    }

    /**
     * Switches a player's team to another specified team.
     * @param player The player to switch teams.
     * @param teamName The name of the new team to assign to the player.
     * @return True if the switch was successful, false otherwise.
     */
    public boolean switchTeams(Player player,String teamName ){
        boolean ret = false;
        for (Team team : scoreboard.getTeams()) {
            if (team.hasEntry(player.getName())) {
                team.removeEntry(player.getName());
                ret = addPlayerToTeam(player, teamName);
                break;
            }
        }
        return ret;
    }

    /**
     * Checks if a player is currently assigned to any team.
     * @param player The player to check.
     * @return True if the player is part of a team, false otherwise.
     */
    public boolean hasTeam(Player player) {
        Scoreboard scoreboardForHasTeam = player.getScoreboard();
        for (Team team : scoreboardForHasTeam.getTeams()) {
            if (team.hasEntry(player.getName())) {
                return true;
            }
        }
        return false;
    }
    public void rebalanceTeams(int nOfPlayers){
        //rebalance
    }

    /**
     * Returns the size of a specified team.
     * @param teamName The name of the team to check the size of.
     * @return The number of players currently in the team.
     */
    public int getTeamSize(String teamName) {
        Team team = scoreboard.getTeam(teamName);
        if (team != null) {
            return team.getEntries().size();
        } else {
            return 0;
        }
    }

    /**
     * Determines the team a player is currently part of.
     * @param player The player whose team is to be identified.
     * @return The name of the team the player is part of, or null if the player is not part of any team.
     */
    public String getTeamNameForPlayer(Player player) {
        for (Team team : scoreboard.getTeams()) {
            if (team.hasEntry(player.getName())) {
                //Bukkit.getLogger().info(player.getName() + " je součástí týmu " + team.getName());
                return team.getName();
            }
        }
        //Bukkit.getLogger().info(player.getName() + " není součástí žádného týmu.");
        return null;
    }

    public boolean isHasBedBlue() {
        return hasBedBlue;
    }

    public void setHasBedBlue(boolean hasBedBlue) {
        this.hasBedBlue = hasBedBlue;
    }

    public boolean isHasBedRed() {
        return hasBedRed;
    }

    public void setHasBedRed(boolean hasBedRed) {
        this.hasBedRed = hasBedRed;
    }

    public boolean isHasBedGreen() {
        return hasBedGreen;
    }

    public void setHasBedGreen(boolean hasBedGreen) {
        this.hasBedGreen = hasBedGreen;
    }

    public boolean isHasBedYellow() {
        return hasBedYellow;
    }

    public void setHasBedYellow(boolean hasBedYellow) {
        this.hasBedYellow = hasBedYellow;
    }
    private ChatColor getTeamColor(String teamName) {
        switch (teamName) {
            case teamName1:
                return teamColour1;
            case teamName2:
                return teamColour2;
            case teamName3:
                return teamColour3;
            case teamName4:
                return teamColour4;
            default:
                return null;
        }
    }
    public List<String> getListOfTeams(){
        return new ArrayList<>(Arrays.asList(teamName1, teamName2, teamName3, teamName4));
    }

    /**
     * Assigns a player to a random team. This method is used when players are not yet assigned to any team
     * and need to be distributed evenly across the available teams.
     * @param player The player to assign to a team.
     */
    public void assignPlayerToRandomTeam(Player player) {
        if (!hasTeam(player)) {
            List<String> teams = getListOfTeams();
            Collections.shuffle(teams);
            for (String teamName : teams) {
                if (addPlayerToTeam(player, teamName)) {
                    return;
                }
            }
        }
    }
    /**
     * Updates the scoreboard for all online players who are part of a team. This method will iterate through
     * all online players, check if they are on a team, and update their scoreboard with the current team status.
     * The scoreboard displays team names and their bed status, using green check marks to indicate a bed is present
     * and red crosses to indicate it is not.
     */
    public void updateTeamScoreboard() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (hasTeam(player)) {
                Scoreboard board = player.getScoreboard();
                Objective objective = board.getObjective("teamInfo");
                if (objective == null) {
                    objective = board.registerNewObjective("teamInfo", "dummy", "Info o Týmech");
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                }

                objective.setDisplayName("" + getTeamNameForPlayer(player));

                updateTeamStatusOnScoreboard(objective, teamName1, isHasBedRed());
                updateTeamStatusOnScoreboard(objective, teamName2, isHasBedBlue());
                updateTeamStatusOnScoreboard(objective, teamName3, isHasBedGreen());
                updateTeamStatusOnScoreboard(objective, teamName4, isHasBedYellow());
            }
        }
    }
    /**
     * Updates the team status on the scoreboard objective for a specific team. This method sets the score for a team
     * entry to reflect the current number of players in the team and updates the icon next to the team name to show
     * a green check mark if the team has a bed, or a red cross if the team's bed has been destroyed.
     *
     * @param objective The scoreboard objective to update.
     * @param teamName The name of the team to update the status for.
     * @param hasBed Whether the team currently has a bed.
     */
    private void updateTeamStatusOnScoreboard(Objective objective, String teamName, boolean hasBed) {
        Scoreboard board = objective.getScoreboard();
        String status = hasBed ? ChatColor.GREEN + "\u2714" : ChatColor.RED + "\u2718";
        String entry = teamName + ": " + status;
        Score score = objective.getScore(entry);
        score.setScore(getTeamSize(teamName));
        for (String option : new String[]{"ChatColor.GREEN + \"\\u2714\" : ChatColor.RED + \"\\u2718\""}) {
            if (!option.equals(status)) {
                board.resetScores(teamName + ": " + option);
            }
        }
    }

}
