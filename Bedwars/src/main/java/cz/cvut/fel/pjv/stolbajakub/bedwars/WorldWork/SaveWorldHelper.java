package cz.cvut.fel.pjv.stolbajakub.bedwars.WorldWork;

import org.bukkit.World;

import java.io.*;
import java.util.List;

/**
 * Utility class for saving and loading serialized world data to and from files.
 * It provides methods to serialize and deserialize block data, which helps in managing game states.
 */
public class SaveWorldHelper {

    // Private constructor to prevent instantiation
    private SaveWorldHelper() {
    }

    /**
     * Saves a list of block data to a specified file. The list is serialized and written to the file.
     *
     * @param file The file where block data will be saved.
     * @param blocks A list of BlockData instances to be serialized and saved.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void saveToFile(File file, List<BlockData> blocks) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(blocks);
        }
    }

    /**
     * Loads a list of block data from a specified file and restores the blocks to a specified world.
     * The method reads the file, deserializes the list of block data, and then restores each block in the world.
     *
     * @param file The file from which block data will be loaded.
     * @param world The world where blocks will be restored.
     * @throws IOException If an I/O error occurs while reading from the file.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    public static void loadFromFile(File file, World world) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<BlockData> loadedBlocks = (List<BlockData>) ois.readObject();
            loadedBlocks.forEach(blockData -> blockData.restoreBlock(world));
            //getLogger().info("Loading blocks from file");
        }
    }
}