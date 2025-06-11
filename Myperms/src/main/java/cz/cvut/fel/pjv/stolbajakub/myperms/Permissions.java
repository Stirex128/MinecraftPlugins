package cz.cvut.fel.pjv.stolbajakub.myperms;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.bukkit.Bukkit.getServer;
/**
 * Command executor for handling custom permissions commands.
 */
public class Permissions implements CommandExecutor, TabCompleter {
    private final String invalidPlayer = "Invalid player";

    /**
     * Executes the custom permissions command.
     * @param commandSender The sender of the command.
     * @param command The command being executed.
     * @param s The command label.
     * @param strings The command arguments.
     * @return true if the command was executed successfully, otherwise false.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length >= 3) {
            String action = strings[0];
            String playerName = strings[1];
            String permission = strings[2];
            boolean value = strings.length == 4 && Boolean.parseBoolean(strings[3]);

            switch (action.toLowerCase()) {
                case "add":
                    addPermission(commandSender, playerName, permission, value);
                    break;
                case "rem":
                    removePermission(commandSender, playerName, permission);
                    break;
                case "check":
                    checkPermission(commandSender, playerName, permission);
                    break;
                default:
                    commandSender.sendMessage("Neplatný příkaz.");
                    break;
            }
        } else {
            commandSender.sendMessage("Neplatné argumenty.");
        }
        return true;
    }

    /**
     * Provides tab completion for the custom permissions command.
     * @param commandSender The sender of the command.
     * @param command The command being executed.
     * @param s The command label.
     * @param strings The command arguments.
     * @return a list of possible completions for the command.
     */

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
    private void addPermission(CommandSender sender, String playerName, String permission, boolean value) {
        Player targetPlayer = getServer().getPlayer(playerName);
        if (targetPlayer == null) {
            sender.sendMessage(invalidPlayer);
            return;
        }

        if (PermsManager.hasPermissionSet(targetPlayer, permission)) {
            sender.sendMessage("Už má tuto permission");
        } else {
            PermissionAttachment attachment = targetPlayer.addAttachment(Myperms.getInstance(), permission, value);
            PermsManager.store(targetPlayer, attachment);
            sender.sendMessage("Your permission was granted!");
        }
    }

    private void removePermission(CommandSender sender, String playerName, String permission) {
        Player targetPlayer = getServer().getPlayer(playerName);
        if (targetPlayer == null) {
            sender.sendMessage(invalidPlayer);
            return;
        }

        if (!PermsManager.remove(targetPlayer, permission)) {
            sender.sendMessage("Tato permission nikdy nebyla danému uživateli přidána.");
        } else {
            sender.sendMessage("Oprávnění bylo odstraněno.");
        }
    }

    private void checkPermission(CommandSender sender, String playerName, String permission) {
        Player targetPlayer = getServer().getPlayer(playerName);
        if (targetPlayer == null) {
            sender.sendMessage(invalidPlayer);
            return;
        }

        if (PermsManager.hasPermissionSet(targetPlayer, permission)) {
            sender.sendMessage("Tuto permission máte");
        } else {
            sender.sendMessage("Toto oprávnění nemáte");
        }
    }
}
