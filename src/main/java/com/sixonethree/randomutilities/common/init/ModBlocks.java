package com.sixonethree.randomutilities.common.init;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.sixonethree.randomutilities.common.block.BlockContainerBase;
import com.sixonethree.randomutilities.common.block.BlockDisplayTable;
import com.sixonethree.randomutilities.common.block.BlockMagicChest;
import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.reference.Reference;

@GameRegistry.ObjectHolder(Reference.MOD_ID) public class ModBlocks {
	public static final BlockContainerBase MAGIC_CHEST = new BlockMagicChest();
	public static final BlockContainerBase DISPLAY_TABLE = new BlockDisplayTable();
	
	public static void init() {
		GameRegistry.register(MAGIC_CHEST);
		GameRegistry.register(new ItemBlock(MAGIC_CHEST).setRegistryName(MAGIC_CHEST.getRegistryName()));
		GameRegistry.register(DISPLAY_TABLE);
		GameRegistry.register(new ItemBlock(DISPLAY_TABLE).setRegistryName(DISPLAY_TABLE.getRegistryName()));
		GameRegistry.registerTileEntity(TileEntityMagicChest.class, "TileEntityMagicChest");
		GameRegistry.registerTileEntity(TileEntityDisplayTable.class, "TileEntityDisplayTable");
	}
}