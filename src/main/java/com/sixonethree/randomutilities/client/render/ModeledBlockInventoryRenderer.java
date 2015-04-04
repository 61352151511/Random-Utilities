package com.sixonethree.randomutilities.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.common.init.ModBlocks;

public class ModeledBlockInventoryRenderer extends TileEntityItemStackRenderer {
	private TileEntityMagicChest temc = new TileEntityMagicChest();
	private TileEntityDisplayTable tedt = new TileEntityDisplayTable();
	
	@Override public void renderByItem(ItemStack itemStack) {
		Block block = Block.getBlockFromItem(itemStack.getItem());
		if (block == ModBlocks.magicChest) {
			TileEntityRendererDispatcher.instance.renderTileEntityAt(this.temc, 0, 0, 0, 0F);
		} else if (block == ModBlocks.displayTable) {
			TileEntityRendererDispatcher.instance.renderTileEntityAt(this.tedt, 0, 0, 0, 0F);
		} else {
			super.renderByItem(itemStack);
		}
	}
}