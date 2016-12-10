package com.sixonethree.randomutilities.common.init;

import com.sixonethree.randomutilities.common.block.BlockContainerBase;
import com.sixonethree.randomutilities.common.block.BlockDisplayTable;
import com.sixonethree.randomutilities.common.block.BlockMagicChest;
import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static final BlockContainerBase magicChest = new BlockMagicChest();
	public static final BlockContainerBase displayTable = new BlockDisplayTable();
	
	public static void init() {
		GameRegistry.register(magicChest);
		GameRegistry.register(new ItemBlock(magicChest).setRegistryName(magicChest.getRegistryName()));
		GameRegistry.register(displayTable);
		GameRegistry.register(new ItemBlock(displayTable).setRegistryName(displayTable.getRegistryName()));
		GameRegistry.registerTileEntity(TileEntityMagicChest.class, "TileEntityMagicChest");
		GameRegistry.registerTileEntity(TileEntityDisplayTable.class, "TileEntityDisplayTable");
	}
}