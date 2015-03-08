package com.sixonethree.randomutilities.init;

import net.minecraft.block.BlockContainer;

import com.sixonethree.randomutilities.block.BlockMagicChest;
import com.sixonethree.randomutilities.block.ItemBlockMagicChest;
import com.sixonethree.randomutilities.block.tile.TileEntityLunchbox;
import com.sixonethree.randomutilities.block.tile.TileEntityMagicChest;

import cpw.mods.fml.common.registry.GameRegistry;


public class ModBlocks {
	public static BlockContainer magicChest = new BlockMagicChest();
	
	public static void init() {
		GameRegistry.registerBlock(magicChest, ItemBlockMagicChest.class, "BlockMagicChest");
		GameRegistry.registerTileEntity(TileEntityMagicChest.class, "TileEntityMagicChest");
		GameRegistry.registerTileEntity(TileEntityLunchbox.class, "TileEntityMagicBag");
	}
}