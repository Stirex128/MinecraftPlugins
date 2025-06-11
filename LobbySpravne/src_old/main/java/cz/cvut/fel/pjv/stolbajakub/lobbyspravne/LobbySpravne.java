package cz.cvut.fel.pjv.stolbajakub.lobbyspravne;



import cz.cvut.fel.pjv.stolbajakub.lobbyspravne.BungeeCommunication.BedwarsCommunication;
import cz.cvut.fel.pjv.stolbajakub.lobbyspravne.Commands.Spawn;
import cz.cvut.fel.pjv.stolbajakub.lobbyspravne.Listeners.*;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobbySpravne extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockProtectionListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryLock(), this);
        getServer().getPluginManager().registerEvents(new ListenForMenuOpen(), this);
        getServer().getPluginManager().registerEvents(new FireDamageListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "bedwars:tolobbyinfo", new BedwarsCommunication());
        HungerControl hungerControl = new HungerControl(this);
        hungerControl.preventHunger();
        SettingsLoader.getInstance().load();
        Command spawnCommand = this.getCommand("spawn");

        //getCommand("spawn").setExecutor(new Spawn());
        ArenaManager.getInstance();
        getLogger().info("@Lobby custom plugin loaded.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
    public static LobbySpravne getInstance(){
        return getPlugin(LobbySpravne.class);
    }
}