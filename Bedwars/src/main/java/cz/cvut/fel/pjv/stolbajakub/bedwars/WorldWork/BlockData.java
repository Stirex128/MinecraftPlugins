package cz.cvut.fel.pjv.stolbajakub.bedwars.WorldWork;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;

import java.io.Serializable;

/**
 * Represents the data for a block, with additional support for beds.
 * This class is used to serialize and deserialize block information, particularly useful for saving and restoring blocks.
 */
public class BlockData implements Serializable {
    private final int x, y, z;
    private final Material material;
    private boolean isBedHead;
    private Bed.Part part;
    private BlockFace facing;

    /**
     * Constructs a new BlockData instance from a block at the specified coordinates.
     * It captures essential block properties like material, and if the block is a bed, additional properties like head part and facing direction.
     *
     * @param x The x-coordinate of the block.
     * @param y The y-coordinate of the block.
     * @param z The z-coordinate of the block.
     * @param block The block at the specified coordinates.
     */
    public BlockData(int x, int y, int z, Block block) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.material = block.getType();
        this.isBedHead = false;

        if (block.getBlockData() instanceof Bed) {
            Bed bed = (Bed) block.getBlockData();
            this.isBedHead = bed.getPart() == Bed.Part.HEAD;
            this.part = bed.getPart();
            this.facing = bed.getFacing();
        }
    }

    /**
     * Restores the block at its original coordinates in the given world using the stored block data.
     * It sets the block type and, for beds, configures the part and facing direction.
     *
     * @param world The world where the block needs to be restored.
     */
    public void restoreBlock(World world) {
        Block block = world.getBlockAt(x, y, z);
        block.setType(material, false);

        if (block.getBlockData() instanceof Bed) {
            Bed bed = (Bed) block.getBlockData();
            bed.setPart(this.part);
            bed.setFacing(this.facing);
            block.setBlockData(bed, true);
        }
    }
}