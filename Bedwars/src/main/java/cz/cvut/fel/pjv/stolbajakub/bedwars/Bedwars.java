package cz.cvut.fel.pjv.stolbajakub.bedwars;

import cz.cvut.fel.pjv.stolbajakub.bedwars.bungeecommunication.Arena;
import cz.cvut.fel.pjv.stolbajakub.bedwars.bungeecommunication.BungeeSendLobbyArenaInfo;
import cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.*;
import cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.helpers.SecondaryGUIListeners;
import cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.MainGUIListener;
import cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.TeamSelectionGUI;
import cz.cvut.fel.pjv.stolbajakub.bedwars.runnabels.ControlWeather;
import cz.cvut.fel.pjv.stolbajakub.bedwars.WorldWork.PvpManager;
import cz.cvut.fel.pjv.stolbajakub.bedwars.WorldWork.SaveWorldCommand;
import cz.cvut.fel.pjv.stolbajakub.bedwars.runnabels.ScoreboardUpdator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This is the main class of the Bedwars plugin that extends JavaPlugin.
 * It manages the lifecycle of the plugin, including setting up and tearing down,
 * registering event listeners and command executors, and initializing the game settings.
 */
public final class Bedwars extends JavaPlugin {
    //private ProtocolManager protocolManager;

    /**
     * Initializes the plugin by loading necessary configurations, setting up teams, and registering event listeners and commands.
     * It also disables PvP and sets consistent weather conditions across game worlds.
     */
    @Override
    public void onEnable() {
        SettingsLoader.getInstance().load();
        SettingsTeamRespawnLocation.getInstance().load();
        TeamManager.getInstance().setupTeams();


        getServer().getPluginManager().registerEvents(new TeamSelectionGUI(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new BackToLobbyListener(), this);
        getServer().getPluginManager().registerEvents(new StartArenaListener(), this);
        getServer().getPluginManager().registerEvents(new BedDestroyListener(), this);
        getServer().getPluginManager().registerEvents(new TeamRespawnListener(), this);
        getServer().getPluginManager().registerEvents(new EndArenaLogic(), this);
        getServer().getPluginManager().registerEvents(new BlockProtectionListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new VillagerProtectionListener(), this);
        getServer().getPluginManager().registerEvents(new ClickOnVillagerListener(), this);
        getServer().getPluginManager().registerEvents(new MainGUIListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new SecondaryGUIListeners(), this);


        //protocolManager = ProtocolLibrary.getProtocolManager();
        getCommand("bw").setExecutor(new SaveWorldCommand());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "bedwars:tolobbyinfo");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "bedwars:bungeeeconomy");

        sendArenaInfoAsync();
        new ControlWeather().keepDaytimeAndClearWeather();
        PvpManager pvpManager = new PvpManager();
        pvpManager.setPvPInWorld(SettingsLoader.getInstance().getWorld(), false);
        new ScoreboardUpdator().start();
    }

    /**
     * Performs cleanup tasks when the server is shutting down, ensuring all configurations are saved and resources are properly released.
     */
    @Override
    public void onDisable() {
        //musi byt pre disable
        //Arena.getInstance().setMaxPlays(0);
        //arenaInfoSender.sendBungeecordArenaInfo(Arena.getInstance());

    }
    public static Bedwars getInstance(){
        return getPlugin(Bedwars.class);
    }
    private void sendArenaInfoAsync() {
        new BukkitRunnable() {
            @Override
            public void run() {
                BungeeSendLobbyArenaInfo arenaInfoSender = new BungeeSendLobbyArenaInfo();
                //Arena.getInstance();// zkusit jestli funguje i bez toho
                arenaInfoSender.sendBungeecordArenaInfo(Arena.getInstance());
            }
        }.runTaskLater(Bedwars.getInstance(), 100L);
    }

}
