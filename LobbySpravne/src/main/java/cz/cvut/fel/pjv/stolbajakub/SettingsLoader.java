package cz.cvut.fel.pjv.stolbajakub;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
/**
 * Handles loading and saving settings from a settings.yml file.
 */
public class SettingsLoader {
    private final static SettingsLoader instance = new SettingsLoader();

    private File file;
    private YamlConfiguration config;
    private int x,y,z;



    private boolean bedwars;
    private SettingsLoader(){

    }

    /**
     * Loads settings from the settings.yml file.
     */
    public void load() {
        file = new File(Lobby.getInstance().getDataFolder(), "settings.yml");

        if (!file.exists())
            Lobby.getInstance().saveResource("settings.yml", false);

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        x = config.getInt("Lobby.Spawn.x");
        y = config.getInt("Lobby.Spawn.y");
        z = config.getInt("Lobby.Spawn.z");
        bedwars = config.getBoolean("Lobby.Bedwars");
    }
    /**
     * Saves settings to the settings.yml file.
     */
    public void save() {
        try {
            config.save(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Sets a value in the configuration and saves it to the file.
     *
     * @param path  The path to the value.
     * @param value The value to set.
     */
    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }
    public int getSpawnX() {
        return x;
    }
    public int getSpawnY() {
        return y;
    }
    public int getSpawnZ() {
        return z;
    }
    public void setSpawnCords(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;

        set("Lobby.Spawn.x", x);
        set("Lobby.Spawn.y", y);
        set("Lobby.Spawn.z", z);
    }
    public boolean isBedwars() {return bedwars;}

    public void setBedwars(boolean bedwars) {
        this.bedwars = bedwars;
    }
    public static SettingsLoader getInstance() {
        return instance;
    }
}
