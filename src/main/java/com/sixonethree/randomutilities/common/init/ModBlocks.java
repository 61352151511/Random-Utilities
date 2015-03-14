package com.sixonethree.randomutilities.common.init;

import net.minecraft.block.BlockContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.sixonethree.randomutilities.common.block.BlockMagicChest;
import com.sixonethree.randomutilities.common.block.ItemBlockMagicChest;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;

public class ModBlocks {
	public static BlockContainer magicChest = new BlockMagicChest();
	
	public static void init() {
		GameRegistry.registerBlock(magicChest, ItemBlockMagicChest.class, "BlockMagicChest");
		GameRegistry.registerTileEntity(TileEntityMagicChest.class, "TileEntityMagicChest");
	}
}