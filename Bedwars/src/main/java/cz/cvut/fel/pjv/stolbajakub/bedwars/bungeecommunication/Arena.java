package cz.cvut.fel.pjv.stolbajakub.bedwars.bungeecommunication;

import cz.cvut.fel.pjv.stolbajakub.bedwars.SettingsLoader;
/**
 * Singleton class representing an arena in Bedwars.
 * This class manages the state of the arena, including the number of players currently playing,
 * the maximum number of players allowed, and whether the arena is active or not.
 */
public class Arena {
    private static Arena instance;
    private int numOfArena, currPlays, MaxPlays;
    private boolean isOn;
    private String name;
    /**
     * Private constructor for Arena class.
     * @param numOfArena The number of the arena.
     * @param isOn Indicates whether the arena is active.
     * @param name The name of the arena.
     * @param currPlays The current number of players in the arena.
     * @param maxPlays The maximum number of players allowed in the arena.
     */

    private Arena(int numOfArena, boolean isOn, String name, int currPlays, int maxPlays){
        this.numOfArena = numOfArena;
        this.isOn = isOn;
        this.name = name;
        this.currPlays = currPlays;
        this.MaxPlays = maxPlays;

    }

    /**
     * Retrieves the instance of the Arena singleton.
     * @return The Arena instance.
     */
    public static synchronized Arena getInstance() {
        if (instance == null) {
            instance = new Arena(
                    1,
                    false,
                    SettingsLoader.getInstance().getArenaName(),
                    0,
                    SettingsLoader.getInstance().getMaxPlayers());
        }
        return instance;
    }

    public int getCurrPlays() {
        return currPlays;
    }

    public void setCurrPlays(int currPlays) {
        this.currPlays = currPlays;
    }

    public int getMaxPlays() {
        return MaxPlays;
    }

    public void setMaxPlays(int maxPlays) {
        MaxPlays = maxPlays;
    }

    public int getNumOfArena() {
        return numOfArena;
    }

    public void setNumOfArena(int numOfArena) {
        this.numOfArena = numOfArena;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
