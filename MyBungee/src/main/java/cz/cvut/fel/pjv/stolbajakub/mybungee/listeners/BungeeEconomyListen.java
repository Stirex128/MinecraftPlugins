package cz.cvut.fel.pjv.stolbajakub.mybungee.listeners;

import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Listens for BungeeCord plugin messages related to economy operations.
 * This class handles incoming messages on the "bungeeeconomy" subchannel,
 * which contain data about currency transactions for players.
 */
public class BungeeEconomyListen implements Listener {
    /**
     * Handles BungeeCord plugin messages for the "bungeeeconomy" subchannel.
     * @param event The PluginMessageEvent triggered when a plugin message is received.
     */
    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getTag().equalsIgnoreCase("BungeeCord")) return;
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()))) {
            String subChannel = in.readUTF();
            if (!subChannel.equals("bedwars:bungeeeconomy")) return;

            String uuid = in.readUTF();
            String currency = in.readUTF();
            int amount = in.readInt();
            String operation = in.readUTF();
            saveData(uuid, currency, amount, operation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Saves economy transaction data to a YAML file.
     * @param uuid The UUID of the player involved in the transaction.
     * @param currency The name of the currency being transacted.
     * @param amount The amount of currency involved in the transaction.
     * @param operation The type of operation (addition or subtraction).
     */
    private void saveData(String uuid, String currency, int amount, String operation) {
        File dataFile = new File("plugins/MyBungee/data.yml");

        if (!dataFile.getParentFile().exists()) {
            dataFile.getParentFile().mkdirs();
        }

        Map<String, Object> data = new HashMap<>();
        Yaml yaml = new Yaml();

        if (dataFile.exists()) {
            try (FileInputStream fis = new FileInputStream(dataFile)) {
                data = yaml.load(fis);
                if (data == null) data = new HashMap<>();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        Map<String, Object> playerData = (Map<String, Object>) data.getOrDefault(uuid, new HashMap<>());
        int currentAmount = (int) playerData.getOrDefault(currency, 0);
        if ("+".equals(operation)) {
            currentAmount += amount;
        } else if ("-".equals(operation)) {
            currentAmount -= amount;
        }
        playerData.put(currency, currentAmount);
        data.put(uuid, playerData);

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);

        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try (FileWriter writer = new FileWriter(dataFile)) {
            yaml.dump(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}