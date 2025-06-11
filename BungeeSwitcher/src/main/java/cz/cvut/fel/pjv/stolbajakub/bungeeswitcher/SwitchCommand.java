package cz.cvut.fel.pjv.stolbajakub.bungeeswitcher;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * Command executor and tab completer for switching players between different servers.
 */
public class SwitchCommand implements CommandExecutor, TabCompleter {

    List<String> list = Arrays.asList("lobby", "minigame");

    /**
     * Handles the execution of the switch command.
     *
     * @param commandSender The sender of the command.
     * @param command       The command being executed.
     * @param s             The label of the command.
     * @param strings       The arguments provided with the command.
     * @return true if the command was executed successfully, otherwise false.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)){
            commandSender.sendMessage("This command can only be run by a player.");
        } else {
            Player sender = (Player) commandSender;
            if (strings.length == 1 && list.contains(strings[0])) {
                switchServer(sender, strings[0]);
        }   else if (strings.length == 2 && list.contains(strings[0])) {
                Player moved =getOnlinePlayerByName(strings[1]);
                if (moved !=null){
                    switchServer(moved, strings[0]);
                }
            }
        } 
        return true;
    }
    /**
     * Handles tab completion for the switch command.
     *
     * @param commandSender The sender of the command.
     * @param command       The command being executed.
     * @param s             The label of the command.
     * @param strings       The arguments provided with the command.
     * @return A list of possible completions for the command arguments.
     */

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length==1){
            return list;
        }
        return Collections.emptyList();
    }
    private void switchServer(Player player, String serverName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serverName);
        player.sendPluginMessage(BungeeSwitcher.getInstance(), "BungeeCord", out.toByteArray());
    }
    private Player getOnlinePlayerByName(String playerName) {
        Player player = Bukkit.getPlayerExact(playerName);
        return player;
    }
}
