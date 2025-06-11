package cz.cvut.fel.pjv.stolbajakub.voidworld;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class EmptyChunkGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        return createChunkData(world);
    }
}

//do bukkit.yml aby se spoustel generator
//worlds:
        //world:
        //generator: EmptyWorldGenerator
