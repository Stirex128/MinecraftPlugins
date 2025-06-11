package cz.cvut.fel.pjv.stolbajakub.BungeeCommunication;



import cz.cvut.fel.pjv.stolbajakub.Arena;
import cz.cvut.fel.pjv.stolbajakub.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static org.bukkit.Bukkit.getLogger;
/**
 * Handles communication related to Bedwars arenas.
 */
public class BedwarsCommunication implements PluginMessageListener {
    /**
     * Receives information about Bedwars arenas and updates the arena manager accordingly.
     *
     * @param s The channel over which the message was received.
     * @param player  The player who sent the message.
     * @param bytes   The byte array containing the message data.
     */
    @Override
    public void onPluginMessageReceived(@NotNull String s, @NotNull Player player, @NotNull byte[] bytes) {
        getLogger().info("prijmuta arena");
        String dataAsHex = bytesToHex(bytes);
        getLogger().info("Přijatá data (hex): " + dataAsHex);
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes))) {
            //String voiding = in.readUTF();
            //voiding = in.readUTF();
            String subchannel = in.readUTF();
            getLogger().info(subchannel);
            if (subchannel.equals("bedwars:tolobbyinfo")){
            int numberOfArena = in.readInt();
            boolean isOn = in.readBoolean();
            String name = in.readUTF();
            int currPlays = in.readInt();
            int maxPlays = in.readInt();
            Arena arena = new Arena(numberOfArena, isOn, name, currPlays, maxPlays);
            ArenaManager.getInstance().addOrUpdateArena(arena);
            //getLogger().info("prijmuta arena");
            } else {
                //getLogger().info("fuck you mate");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x ", b));
        }
        return builder.toString();
    }
}

