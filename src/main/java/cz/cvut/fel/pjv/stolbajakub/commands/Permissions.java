package cz.cvut.fel.pjv.stolbajakub.commands;

import cz.cvut.fel.pjv.stolbajakub.Lobby;
import cz.cvut.fel.pjv.stolbajakub.PermsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getServer;

public class Permissions implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length==3){
            String playerName = strings[1];
            Player targetPlayer = getServer().getPlayer(playerName);
            if (targetPlayer != null) {
                if (strings[0].equalsIgnoreCase("add")){
                    if (PermsManager.hasPermissionSet(targetPlayer, strings[2])){
                        commandSender.sendMessage("Už má tuto permission");
                    } else{
                        PermissionAttachment attachment = targetPlayer.addAttachment(Lobby.getInstance(), strings[2], true);
                        PermsManager.store(targetPlayer, attachment);
                        commandSender.sendMessage("Your permission was granted!");
                    }
                }
            else if(strings[0].equalsIgnoreCase("rem")){
                    if (!PermsManager.remove(targetPlayer, strings[2])) {
                        commandSender.sendMessage("Tato permission nikdy nebyla danému uživateli přidána.");
                    } else {
                        commandSender.sendMessage("Oprávnění bylo odstraněno.");
                    }
            }else if (strings[0].equalsIgnoreCase("check")){
                    if (PermsManager.hasPermissionSet(targetPlayer, strings[2])){
                        commandSender.sendMessage("Tuto permission máte");
                    } else commandSender.sendMessage("Toto oprávnění nemáte");
            }
            }
            else commandSender.sendMessage("Invalid player");
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
