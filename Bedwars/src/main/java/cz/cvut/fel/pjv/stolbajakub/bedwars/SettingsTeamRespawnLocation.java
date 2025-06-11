package cz.cvut.fel.pjv.stolbajakub.bedwars;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static org.bukkit.Bukkit.getServer;

/**
 * Manages and provides team respawn locations based on settings stored in a configuration file.
 */
public class SettingsTeamRespawnLocation {
    private final static SettingsTeamRespawnLocation instance = new SettingsTeamRespawnLocation();

    private File file;
    private YamlConfiguration config;
    private int x1,y1,z1;
    private int x2,y2,z2;
    private int x3,y3,z3;
    private int x4,y4,z4;

    private SettingsTeamRespawnLocation(){

    }

    /**
     * Loads the respawn locations from the 'settings.yml' configuration file.
     * Initializes default values if the file does not exist and reads coordinates for each team.
     */
    public void load() {
        file = new File(Bedwars.getInstance().getDataFolder(), "settings.yml");

        if (!file.exists())
            Bedwars.getInstance().saveResource("settings.yml", false);

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        x1 = config.getInt("Bedwars.TeamBlueRespawn.x");
        y1 = config.getInt("Bedwars.TeamBlueRespawn.y");
        z1 = config.getInt("Bedwars.TeamBlueRespawn.z");
        x2 = config.getInt("Bedwars.TeamRedRespawn.x");
        y2 = config.getInt("Bedwars.TeamRedRespawn.y");
        z2 = config.getInt("Bedwars.TeamRedRespawn.z");
        x3 = config.getInt("Bedwars.TeamGreenRespawn.x");
        y3 = config.getInt("Bedwars.TeamGreenRespawn.y");
        z3 = config.getInt("Bedwars.TeamGreenRespawn.z");
        x4 = config.getInt("Bedwars.TeamYellowRespawn.x");
        y4 = config.getInt("Bedwars.TeamYellowRespawn.y");
        z4 = config.getInt("Bedwars.TeamYellowRespawn.z");
    }

    /**
     * Saves the modified configuration back to the 'settings.yml' file.
     */
    public void save() {
        try {
            config.save(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Updates a configuration setting and immediately saves the changes.
     * @param path The YAML path where the value should be stored.
     * @param value The value to store.
     */
    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }
    public Location getSpawnLocationBlue(){
        return new Location(getServer().getWorld(SettingsLoader.getInstance().getWorld()), x1,y1,z1 );
    }
    public Location getSpawnLocationRed(){
        return new Location(getServer().getWorld(SettingsLoader.getInstance().getWorld()), x2,y2,z2 );
    }
    public Location getSpawnLocationGreen(){
        return new Location(getServer().getWorld(SettingsLoader.getInstance().getWorld()), x3,y3,z3 );
    }
    public Location getSpawnLocationYellow(){
        return new Location(getServer().getWorld(SettingsLoader.getInstance().getWorld()), x4,y4,z4 );
    }

    //možná pro nastavení ve hře

    /**
     * Sets the spawn coordinates for a specific location in the configuration and saves the updated settings.
     * This method can be used to dynamically update the spawn point coordinates during gameplay or setup.
     *
     * @param x The x-coordinate of the new spawn location.
     * @param y The y-coordinate of the new spawn location.
     * @param z The z-coordinate of the new spawn location.
     */
    public void setSpawnCords(int x, int y, int z) {
        this.x1 = x;
        this.y1 = y;
        this.z1 = z;

        set("Lobby.Spawn.x", x);
        set("Lobby.Spawn.y", y);
        set("Lobby.Spawn.z", z);
    }
    public static SettingsTeamRespawnLocation getInstance() {
        return instance;
    }
}
