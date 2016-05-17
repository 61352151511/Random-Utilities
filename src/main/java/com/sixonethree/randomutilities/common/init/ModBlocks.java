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
	public static BlockContainerBase magicChest = new BlockMagicChest();
	public static BlockContainerBase displayTable = new BlockDisplayTable();
	
	public static void init() {
		GameRegistry.register(magicChest.setRegistryName("magicChest"));
		GameRegistry.register(new ItemBlock(magicChest).setRegistryName("magicChest"));
		GameRegistry.register(displayTable.setRegistryName("displayTable"));
		GameRegistry.register(new ItemBlock(displayTable).setRegistryName("displayTable"));
		GameRegistry.registerTileEntity(TileEntityMagicChest.class, "TileEntityMagicChest");
		GameRegistry.registerTileEntity(TileEntityDisplayTable.class, "TileEntityDisplayTable");
	}
}