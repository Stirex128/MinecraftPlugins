package cz.cvut.fel.pjv.stolbajakub.bedwars;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

/**
 * Singleton class responsible for loading and managing settings from a configuration file.
 * This class provides access to game settings like spawn locations, team settings, and arena configurations.
 */
public class SettingsLoader {
    private final static SettingsLoader instance = new SettingsLoader();
    private File file;
    private YamlConfiguration config;
    private String world;
    private String arenaName;
    private int x;
    private int y;
    private int z;
    private int maxPlayers;
    private int minForStart;
    private int maxInTeam;
    private int maxArenaDuration;

    private SettingsLoader(){

    }

    /**
     * Loads configuration settings from the "settings.yml" file located in the plugin's data folder.
     * If the file does not exist, it is created from the plugin's resources.
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
        maxInTeam = config.getInt("Bedwars.MaxInTeam");
        world = config.getString("Bedwars.WorldName");
        x = config.getInt("Bedwars.Spawn.x");
        y = config.getInt("Bedwars.Spawn.y");
        z = config.getInt("Bedwars.Spawn.z");
        arenaName = config.getString("Bedwars.ArenaName");
        minForStart = config.getInt("Bedwars.MinForStart");
        maxPlayers = config.getInt("Bedwars.MaxPlayers");
        maxArenaDuration = config.getInt("Bedwars.MaxArenaDuration");


/**
 * Saves the current configuration back to the file.
 */
    }
    public void save() {
        try {
            config.save(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets a specific configuration path to a given value and saves the configuration.
     * @param path the configuration key path
     * @param value the value to set
     */
    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    public Location getSpawnLocation(){
        return new Location(getServer().getWorld(world), x,y,z );
    }
    public int getMaxInTeam() {
        return maxInTeam;
    }
    public String getWorld(){return world;}

    /**
     * Returns a list of dropper configurations from the "Bedwars.Droppers" section.
     * @return a list of maps where each map contains the configuration for one dropper
     */
    public List<Map<String, Object>> getDroppers() {
        List<Map<String, Object>> droppersList = new ArrayList<>();
        ConfigurationSection droppersSection = config.getConfigurationSection("Bedwars.Droppers");
        if (droppersSection != null) {
            for (String key : droppersSection.getKeys(false)) {
                Map<String, Object> dropperInfo = droppersSection.getConfigurationSection(key).getValues(false);
                droppersList.add(dropperInfo);
            }
        }
        return droppersList;
    }
    public void setMaxInTeam(int maxInTeam) {
        this.maxInTeam = maxInTeam;
        set("Bedwars.MaxInTeam", maxInTeam);
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinForStart() {
        return minForStart;
    }

    public String getArenaName() {
        return arenaName;
    }

    public int getMaxArenaDuration() {
        return maxArenaDuration;
    }

    /**
     * Provides access to the singleton instance of the SettingsLoader.
     * @return the singleton instance of SettingsLoader
     */
    public static SettingsLoader getInstance() {
        return instance;
    }
}
