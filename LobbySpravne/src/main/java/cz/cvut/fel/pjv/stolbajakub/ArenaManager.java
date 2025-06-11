package cz.cvut.fel.pjv.stolbajakub;

import java.util.ArrayList;
import java.util.List;
/**
 * Manages arenas in the game is singleton.
 */
public class ArenaManager {
    private static ArenaManager instance;
    private List<Arena> arenas;
    /**
     * Returns the singleton instance of ArenaManager.
     *
     * @return The instance of ArenaManager.
     */
    private ArenaManager() {
        arenas = new ArrayList<>();
    }
    public static synchronized ArenaManager getInstance() {
        if (instance == null) {
            instance = new ArenaManager();
        }
        return instance;
    }

    /**
     * Adds a new arena or updates an existing one.
     *
     * @param newArena The new arena to add or update.
     */
    public void addOrUpdateArena(Arena newArena) {
        for (Arena arena : arenas) {
            if (arena.getNumOfArena() == (newArena.getNumOfArena())) {
                arena.setCurrPlays(newArena.getCurrPlays());
                arena.setOn(newArena.isOn());
                return;
            }
        }
        arenas.add(newArena);
    }

    public List<Arena> getArenas() {
        return arenas;
    }
}
