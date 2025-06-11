package cz.cvut.fel.pjv.stolbajakub.mybungee.listeners;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
/**
 * Listener for handling BungeeCord plugin messages related to bedwars lobby information.
 */
public class BedInfoListen implements Listener {

    ServerInfo targetServer = ProxyServer.getInstance().getServerInfo("lobby");
    /**
     * Handles the reception of plugin messages.
     *
     * @param event The event representing the plugin message.
     */
    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getTag().equalsIgnoreCase("BungeeCord")) return;
        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()))) {
            String subChannel = in.readUTF();
            //System.out.println("ssssss" + subChannel);
            if (!subChannel.equals("bedwars:tolobbyinfo")) return;
            //System.out.println("ssssss");
            targetServer.sendData("BungeeCord", event.getData());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
