package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cz.cvut.fel.pjv.stolbajakub.bedwars.Bedwars;
import cz.cvut.fel.pjv.stolbajakub.bedwars.bungeecommunication.Arena;
import cz.cvut.fel.pjv.stolbajakub.bedwars.bungeecommunication.BungeeSendLobbyArenaInfo;
import cz.cvut.fel.pjv.stolbajakub.bedwars.runnabels.Droppers;
import cz.cvut.fel.pjv.stolbajakub.bedwars.SettingsLoader;
import cz.cvut.fel.pjv.stolbajakub.bedwars.TeamManager;
import cz.cvut.fel.pjv.stolbajakub.bedwars.WorldWork.PvpManager;
import cz.cvut.fel.pjv.stolbajakub.bedwars.WorldWork.SaveWorldHelper;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

import static org.bukkit.Bukkit.getServer;
/**
 * Listener class for managing the last team standing scenario in Bedwars.
 */
public class EndArenaLogic implements Listener {
    private static EndArenaLogic instance;
    private PvpManager pvpManager = new PvpManager();
    BungeeSendLobbyArenaInfo arenaInfoSender = new BungeeSendLobbyArenaInfo();

    String winner;
    public static synchronized EndArenaLogic getInstance() {
        if (instance == null) {
            instance = new EndArenaLogic();
        }
        return instance;
    }

    /**
     * Checks for winner after player death.
     * @param event PlayerDeathEvent instance.
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        checkLastTeamStanding();
    }

    /**
     * Checks for winner after player quit.
     * @param event PlayerQuitEvent instance.
     */
    @EventHandler(priority = EventPriority.LOWEST) //jako poslední aby se stihli odendat z týmu
    public void onPlayerQuit(PlayerQuitEvent event){
        String team = TeamManager.getInstance().getTeamNameForPlayer(event.getPlayer());
        if (team != null){
            TeamManager.getInstance().removePlayerFromTeam(event.getPlayer(), team);
        }
        if (StartArenaListener.getInstance().isRunning()){
            checkLastTeamStanding();
            }
        }

    private void checkLastTeamStanding() {
        int teamsWithPlayers = 0;
        String lastTeamName = "";


        for (String teamName : new String[]{"Tým Modrý", "Tým Červený", "Tým Zelení", "Tým Žlutí"}) {
            if (TeamManager.getInstance().getTeamSize(teamName) > 0) {
                teamsWithPlayers++;
                lastTeamName = teamName;
            }
        }


        if (teamsWithPlayers == 1) { // || teamsWithPlayers == 0 pro t
            winner = lastTeamName;
            endTasks();
            new BukkitRunnable() {
                @Override
                public void run() {
                    File loadfile = new File(Bedwars.getInstance().getDataFolder(), "savedBlocks.dat");
                    try{
                        SaveWorldHelper.loadFromFile(loadfile, getServer().getWorld("world"));
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    EndArenaLogic.getInstance().cancelCountdown();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        switchServer(player, "lobby");
                        String team = TeamManager.getInstance().getTeamNameForPlayer(player);
                        player.sendTitle(winner + " vyhrál hru!", "", 10, 200, 10);
                        if (team != null){
                            TeamManager.getInstance().removePlayerFromTeam(player, team);
                        }

                    }
                    //poslat zprávu lobby ready na hru
                }
            }.runTaskLater(Bedwars.getInstance(), 100L);
        }

    }


    private BossBar bar;
    private BukkitRunnable countdownTask;
    /**
     * Creates a countdown displayed to all online players using a BossBar.
     * The BossBar displays the number of remaining seconds and decrements every second.
     * When the countdown reaches zero, the endTasks method is called, the countdown is
     * canceled, and players are switched to the lobby.
     *
     * @param seconds The number of seconds for the countdown.
     */
    public void createCountdownBarForGameEnd(int seconds) {
        bar = Bukkit.createBossBar("nice", BarColor.BLUE, BarStyle.SOLID);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(player);
        }
        bar.setVisible(true);

        countdownTask = new BukkitRunnable() {
            int remainingSeconds = seconds;

            @Override
            public void run() {
                bar.setProgress((double) remainingSeconds / seconds);
                bar.setTitle(remainingSeconds + " sekund do konce");
                remainingSeconds--;

                if (remainingSeconds < 0) {
                    endTasks();
                    EndArenaLogic.getInstance().cancelCountdown();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        switchServer(player, "lobby");
                        String team = TeamManager.getInstance().getTeamNameForPlayer(player);
                        player.sendTitle("Nikdo vyhrál hru!", "", 10, 200, 10);
                        if (team != null) {
                            TeamManager.getInstance().removePlayerFromTeam(player, team);
                        }
                        this.cancel();
                    }
                }
            }
        };
        countdownTask.runTaskTimer(Bedwars.getInstance(), 0L, 20L);
    }
    /**
     * Cancels the current countdown task and removes the BossBar from all players.
     * If there's an active countdown task, it will be canceled. The BossBar, if present,
     * will be hidden and cleared of all players. This method resets the BossBar and
     * countdown task references to null.
     */
    public void cancelCountdown() {
        if (countdownTask != null) {
            countdownTask.cancel();
            countdownTask = null;
        }
        if (bar != null) {
            bar.setVisible(false);
            bar.removeAll();
            bar = null;
        }
    }
    private void switchServer(Player player, String serverName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serverName);
        player.sendPluginMessage(Bedwars.getInstance(), "BungeeCord", out.toByteArray());
    }
    private void sendMoveMessageToAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("Budete přesunuti na lobby server.");
        }
    }
    private void removeItems(){
        World world = Bukkit.getWorld(SettingsLoader.getInstance().getWorld());
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Item) {
                entity.remove();
            }
        }
    }
    private void endTasks(){
        sendMoveMessageToAll();
        Droppers.stopItemSpawnScheduler();
        pvpManager.setPvPInWorld(SettingsLoader.getInstance().getWorld(), false);
        TeamManager.getInstance().setHasBedRed(true);
        TeamManager.getInstance().setHasBedBlue(true);
        TeamManager.getInstance().setHasBedGreen(true);
        TeamManager.getInstance().setHasBedYellow(true);
        StartArenaListener.getInstance().setRunning(false);
        Arena.getInstance().setOn(false);
        Arena.getInstance().setCurrPlays(0);
        arenaInfoSender.sendBungeecordArenaInfo(Arena.getInstance());
        removeItems();
    }
}