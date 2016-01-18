package com.sixonethree.randomutilities.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.common.init.ModBlocks;

public class ModeledBlockInventoryRenderer extends TileEntityItemStackRenderer {
	private TileEntityDisplayTable tedt = new TileEntityDisplayTable();
	private TileEntityItemStackRenderer superInstance;
	
	public ModeledBlockInventoryRenderer(TileEntityItemStackRenderer superInstance) {
		this.superInstance = superInstance;
	}
	
	@Override public void renderByItem(ItemStack itemStack) {
		Block block = Block.getBlockFromItem(itemStack.getItem());
		if (block == ModBlocks.displayTable) {
			TileEntityRendererDispatcher.instance.renderTileEntityAt(this.tedt, 0, 0, 0, 0F);
		} else {
			superInstance.renderByItem(itemStack);
		}
	}
}