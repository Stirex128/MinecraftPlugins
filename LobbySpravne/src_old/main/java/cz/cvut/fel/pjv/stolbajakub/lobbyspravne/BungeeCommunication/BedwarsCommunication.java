package cz.cvut.fel.pjv.stolbajakub.lobbyspravne.BungeeCommunication;


import cz.cvut.fel.pjv.stolbajakub.lobbyspravne.Arena;
import cz.cvut.fel.pjv.stolbajakub.lobbyspravne.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class BedwarsCommunication implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(@NotNull String s, @NotNull Player player, @NotNull byte[] bytes) {
        if (!s.equals("bedwars:tolobbyinfo")) {
            return;
        }
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes))) {
            int numberOfArena = in.readInt();
            boolean isOn = in.readBoolean();
            String name = in.readUTF();
            int currPlays = in.readInt();
            int maxPlays = in.readInt();
            Arena arena = new Arena(numberOfArena, isOn, name, currPlays, maxPlays);
            ArenaManager.getInstance().addOrUpdateArena(arena);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

