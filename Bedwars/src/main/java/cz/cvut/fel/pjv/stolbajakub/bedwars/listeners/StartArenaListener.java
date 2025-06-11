package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners;

import cz.cvut.fel.pjv.stolbajakub.bedwars.*;
import cz.cvut.fel.pjv.stolbajakub.bedwars.bungeecommunication.Arena;
import cz.cvut.fel.pjv.stolbajakub.bedwars.bungeecommunication.BungeeSendEconomy;
import cz.cvut.fel.pjv.stolbajakub.bedwars.bungeecommunication.BungeeSendLobbyArenaInfo;
import cz.cvut.fel.pjv.stolbajakub.bedwars.runnabels.Droppers;
import cz.cvut.fel.pjv.stolbajakub.bedwars.WorldWork.PvpManager;
import cz.cvut.fel.pjv.stolbajakub.bedwars.WorldWork.VillagerKiller;
import cz.cvut.fel.pjv.stolbajakub.bedwars.WorldWork.VillagerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

/**
 * Listens for player join events and controls the start of the arena.
 */
public class StartArenaListener implements Listener {

    private static StartArenaListener instance;
    public StartArenaListener() {
        instance = this;
    }

    public static StartArenaListener getInstance() {
        return instance;
    }
    private final int requiredPlayers = SettingsLoader.getInstance().getMinForStart();
    BungeeSendLobbyArenaInfo arenaInfoSender = new BungeeSendLobbyArenaInfo();
    BungeeSendEconomy sendEcon = new BungeeSendEconomy();
    private boolean isRunning = false;
    private boolean countdownRunning = false;
    private BukkitRunnable countdownTask = null;
    private PvpManager pvpManager = new PvpManager();


    /**
     * Controls player joining and arena starting.
     *
     * @param event The PlayerJoinEvent instance.
     */

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!isRunning) {
            Arena.getInstance().setCurrPlays(Arena.getInstance().getCurrPlays() + 1);
            sendArenaInfoAsync();
            if (!countdownRunning && getServer().getOnlinePlayers().size() >= requiredPlayers) {
                startCountdown();
            }
        }

    }

    /**
     * Controls if there are enough players after a player quits.
     *
     * @param event The PlayerQuitEvent instance.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

            String team = TeamManager.getInstance().getTeamNameForPlayer(event.getPlayer());
            if (team != null){
                TeamManager.getInstance().removePlayerFromTeam(event.getPlayer(), team);


            if (countdownRunning && getServer().getOnlinePlayers().size()-1 < requiredPlayers) {
                stopCountdown();
                getServer().broadcastMessage("Odpočet byl zastaven kvůli nedostatečnému počtu hráčů.");
            }
        }
    }
    /**
     * Starts the countdown for the arena start.
     * When the countdown finishes, the game starts, players are teleported, teams are assigned,
     * and various initializations are performed.
     */
    private void startCountdown() {
        countdownRunning = true;
        countdownTask = new BukkitRunnable() {
            int counter = 10;

            @Override
            public void run() {
                if (counter <= 0) {
                    //poslat zprávu lobby bacha hrajem
                    isRunning = true;
                    Arena.getInstance().setOn(true);
                    sendArenaInfoAsync();
                    pvpManager.setPvPInWorld(SettingsLoader.getInstance().getWorld(), true);
                    EndArenaLogic.getInstance().createCountdownBarForGameEnd( SettingsLoader.getInstance().getMaxArenaDuration());

                    for (Player player : getServer().getOnlinePlayers()) {
                        player.sendTitle("Hra!", "", 10, 70, 20);
                        TeamManager.getInstance().assignPlayerToRandomTeam(player);
                        teleportToTeamRespawn(player);
                        player.getInventory().clear();
                        sendEcon.sendBungeecordEconMessage(player, "coins", 10, "+");
                    }
                    VillagerKiller.killAllVillagers(Objects.requireNonNull(getServer().getWorld(SettingsLoader.getInstance().getWorld())));
                    new VillagerManager().createShopVillagers();
                    Droppers.startItemSpawnScheduler();
                    this.cancel();
                    countdownRunning = false;
                } else {
                    if (counter % 5 == 0 || counter <= 3) {
                        for (Player player : getServer().getOnlinePlayers()) {
                            player.sendTitle(" " + counter, "sekund do hry", 0, 20, 0);
                        }
                    }
                    counter--;
                }
            }
        };
        countdownTask.runTaskTimer(Bedwars.getInstance(), 0L, 20L);
    }

    private void stopCountdown() {
        if (countdownTask != null) {
            countdownTask.cancel();
            countdownRunning = false;
            EndArenaLogic.getInstance().cancelCountdown();
        }
    }
    private void teleportToTeamRespawn(Player player) {
        String team = TeamManager.getInstance().getTeamNameForPlayer(player);
        Location respawnLocation = null;

        switch (team) {
            case "Tým Modrý":
                respawnLocation = SettingsTeamRespawnLocation.getInstance().getSpawnLocationBlue();
                break;
            case "Tým Červený":
                respawnLocation = SettingsTeamRespawnLocation.getInstance().getSpawnLocationRed();
                break;
            case "Tým Zelení":
                respawnLocation = SettingsTeamRespawnLocation.getInstance().getSpawnLocationGreen();
                break;
            case "Tým Žlutí":
                respawnLocation = SettingsTeamRespawnLocation.getInstance().getSpawnLocationYellow();
                break;
            default:
                respawnLocation = SettingsLoader.getInstance().getSpawnLocation();
                break;
        }

        if (respawnLocation != null) {
            player.teleport(respawnLocation);
        } else {
            getLogger().info("Nepodařilo se nalézt respawn lokaci pro hráče: " + player.getName());
        }
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void sendArenaInfoAsync() {
        new BukkitRunnable() {
            @Override
            public void run() {
                arenaInfoSender.sendBungeecordArenaInfo(Arena.getInstance());
            }
        }.runTaskLater(Bedwars.getInstance(), 40L);
    }
}
