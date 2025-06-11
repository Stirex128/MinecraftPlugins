package cz.cvut.fel.pjv.stolbajakub;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SettingsLoader {
    private final static SettingsLoader instance = new SettingsLoader();

    private File file;
    private YamlConfiguration config;
    private int x,y,z;

    private SettingsLoader(){

    }
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
    }
    public void save() {
        try {
            config.save(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

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
    public static SettingsLoader getInstance() {
        return instance;
    }
}
