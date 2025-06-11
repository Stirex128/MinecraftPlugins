package cz.cvut.fel.pjv.stolbajakub.lobbyspravne;

public class Arena {
    private static Arena instance;
    private int numOfArena, currPlays, MaxPlays;
    private boolean isOn;
    private String name;

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
