package cz.cvut.fel.pjv.stolbajakub.lobbyspravne;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {
    private static ArenaManager instance;
    private List<Arena> arenas;
    private ArenaManager() {
        arenas = new ArrayList<>();
    }
    public static synchronized ArenaManager getInstance() {
        if (instance == null) {
            instance = new ArenaManager();
        }
        return instance;
    }
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
