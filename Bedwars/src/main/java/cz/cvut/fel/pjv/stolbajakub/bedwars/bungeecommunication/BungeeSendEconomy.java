package cz.cvut.fel.pjv.stolbajakub.bedwars.bungeecommunication;

import cz.cvut.fel.pjv.stolbajakub.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
/**
 * Handles communication with BungeeCord for sending economy-related information.
 */
public class BungeeSendEconomy {
    /**
     * Sends information about economy to BungeeCord.
     *
     * @param player The player to whom the information is related.
     * @param currency The type of currency (e.g., "coins").
     * @param amount The amount of currency.
     * @param operation The operation performed (e.g., "+" for addition, "-" for subtraction).
     */
    public void sendBungeecordEconMessage(Player player, String currency, int amount, String operation) {
        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(byteArray);
            //tohle by se pouzilo pro kom se vsemi
            //out.writeUTF("Forward");
            //out.writeUTF("ALL");
            out.writeUTF("bedwars:bungeeeconomy");
            out.writeUTF(player.getUniqueId().toString());
            out.writeUTF(currency);
            out.writeInt(amount);
            out.writeUTF(operation);
            Bukkit.getServer().sendPluginMessage(Bedwars.getInstance(), "BungeeCord", byteArray.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
