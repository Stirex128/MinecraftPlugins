package cz.cvut.fel.pjv.stolbajakub.bedwars.bungeecommunication;

import cz.cvut.fel.pjv.stolbajakub.bedwars.Bedwars;
import org.bukkit.Bukkit;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Class for sending arena information to BungeeCord.
 * This class provides a method to send information about the arena status,
 * including the arena number, status, name, current players, and maximum players,
 * to BungeeCord.
 */
public class BungeeSendLobbyArenaInfo {
    /**
     * Sends information to BungeeCord about the arena status.
     * @param arena The Arena instance containing the information to be sent.
     */
    public void sendBungeecordArenaInfo(Arena arena) {
        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(byteArray);
            out.writeUTF("bedwars:tolobbyinfo");
            //getLogger().warning(" "+ arena.getNumOfArena() );
            out.writeInt(arena.getNumOfArena());
            //getLogger().warning(" "+ arena.isOn());
            out.writeBoolean(arena.isOn());
            out.writeUTF(arena.getName());
            out.writeInt(arena.getCurrPlays());
            out.writeInt(arena.getMaxPlays());
            Bukkit.getServer().sendPluginMessage(Bedwars.getInstance(), "BungeeCord", byteArray.toByteArray());
            //getLogger().info("poslana arena");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
