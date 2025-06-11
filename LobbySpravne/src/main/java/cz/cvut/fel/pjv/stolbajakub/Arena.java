package cz.cvut.fel.pjv.stolbajakub;
/**
 * Represents an arena in the game.
 */
public class Arena {

    private int numOfArena, currPlays, MaxPlays;
    private boolean isOn;
    private String name;
    /**
     * Constructs a new Arena instance.
     *
     * @param numOfArena The number of the arena.
     * @param isOn       The status of the arena.
     * @param name       The name of the arena.
     * @param currPlays  The current number of players in the arena.
     * @param maxPlays   The maximum number of players allowed in the arena.
     */
    public Arena(int numOfArena, boolean isOn, String name, int currPlays, int maxPlays){
        this.numOfArena = numOfArena;
        this.isOn = isOn;
        this.name = name;
        this.currPlays = currPlays;
        this.MaxPlays = maxPlays;
    }

    public int getNumOfArena() {
        return numOfArena;
    }

    public void setOn(boolean status) {
        isOn = status;
    }

    public void setCurrPlays(int currPlays) {
        this.currPlays = currPlays;
    }

    public boolean isOn() {
        return isOn;
    }

    public int getCurrPlays() {
        return currPlays;
    }

    public int getMaxPlays() {
        return MaxPlays;
    }

    public String getName() {
        return name;
    }
}
