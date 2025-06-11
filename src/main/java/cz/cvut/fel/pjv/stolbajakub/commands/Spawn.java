package cz.cvut.fel.pjv.stolbajakub.commands;

import cz.cvut.fel.pjv.stolbajakub.SettingsLoader;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

import static org.bukkit.Bukkit.*;

public class Spawn implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)){
            commandSender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player sender = (Player) commandSender;

        Location spawnLocation = new Location(getServer().getWorld("world"),
                SettingsLoader.getInstance().getSpawnX(),
                SettingsLoader.getInstance().getSpawnY(),
                SettingsLoader.getInstance().getSpawnZ());


        if (strings.length == 0){
            sender.teleport(spawnLocation);
            return true;
        }


        if (strings.length == 4 && strings[0].equalsIgnoreCase("set") && sender.hasPermission("lobby.spawn.set")){
            try {
                int x = Integer.parseInt(strings[1]);
                int y = Integer.parseInt(strings[2]);
                int z = Integer.parseInt(strings[3]);
                SettingsLoader.getInstance().setSpawnCords(x, y, z);
                sender.sendMessage("Spawn location set to: " + x + ", " + y + ", " + z);
                spawnLocation = new Location(getServer().getWorld("world"), x, y, z);
            } catch (NumberFormatException e){
                sender.sendMessage("x, y, and z must be numbers.");
                return false;
            }
        } else {
            sender.sendMessage("You dont have permission");
            return false;
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (strings.length == 1) {
                return Collections.singletonList("set");
            }


            if (strings.length > 1 && "set".equalsIgnoreCase(strings[0])) {
                switch (strings.length) {

                    case 2:
                        return Collections.singletonList(String.valueOf(player.getLocation().getBlockX()));

                    case 3:
                        return Collections.singletonList(String.valueOf(player.getLocation().getBlockY()));

                    case 4:
                        return Collections.singletonList(String.valueOf(player.getLocation().getBlockZ()));
                }
            }
        }

        return Collections.emptyList();
    }
}
