package cz.cvut.fel.pjv.stolbajakub.bedwars.WorldWork;

import cz.cvut.fel.pjv.stolbajakub.bedwars.Bedwars;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements command functionality for saving specified areas of the game world to a file.
 */
public class SaveWorldCommand implements CommandExecutor, TabCompleter {
    private Location start;
    private Location end;
    private boolean hasStart = false;
    private boolean hasEnd = false;
    private final List<BlockData> blocks = new ArrayList<>();

    /**
     * Handles command input for saving specific world areas. This method configures start and end positions
     * and initiates saving process if conditions are met.
     *
     * @param commandSender the source of the command
     * @param command       the command that was executed
     * @param s             alias of the command which was used
     * @param strings       passed command arguments
     * @return true if a valid command was provided, false otherwise
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (strings.length == 1 && hasStart && hasEnd && strings[0].equalsIgnoreCase("save")) {
            copyTerritory(start, end);
            File saveFile = new File(Bedwars.getInstance().getDataFolder(), "savedBlocks.dat");
            try {
                SaveWorldHelper.saveToFile(saveFile, blocks);
                commandSender.sendMessage("Území bylo úspěšně uloženo.");
            } catch (IOException e) {
                e.printStackTrace();
                commandSender.sendMessage("Došlo k chybě při ukládání území.");
            }
        } else if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("pos1")) {
                start = player.getLocation();
                hasStart = true;
            } else if (strings[0].equalsIgnoreCase("pos2")) {
                end = player.getLocation();
                hasEnd = true;
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * This method provides tab completion for commands, but it's not implemented in this case.
     *
     * @param commandSender the source of the command
     * @param command       the command for which tab completion is being considered
     * @param s             alias of the command
     * @param strings       the arguments passed to the command
     * @return null as tab completion is not implemented
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }

    /**
     * Copies all blocks within a specified rectangular region from 'start' to 'end' into a list.
     *
     * @param start the starting location of the area to copy
     * @param end   the ending location of the area to copy
     */
    private void copyTerritory(Location start, Location end) {
        int minX = Math.min(start.getBlockX(), end.getBlockX());
        int maxX = Math.max(start.getBlockX(), end.getBlockX());
        int minY = Math.min(start.getBlockY(), end.getBlockY());
        int maxY = Math.max(start.getBlockY(), end.getBlockY());
        int minZ = Math.min(start.getBlockZ(), end.getBlockZ());
        int maxZ = Math.max(start.getBlockZ(), end.getBlockZ());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = start.getWorld().getBlockAt(x, y, z);
                    blocks.add(new BlockData(x, y, z, block));
                }
            }
        }
    }
}