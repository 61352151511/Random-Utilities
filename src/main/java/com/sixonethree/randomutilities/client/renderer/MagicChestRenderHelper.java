package com.sixonethree.randomutilities.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.common.init.ModBlocks;

public class MagicChestRenderHelper extends TileEntityItemStackRenderer {
	private TileEntityMagicChest temc = new TileEntityMagicChest();
	
	@Override public void renderByItem(ItemStack itemStack) {
		Block block = Block.getBlockFromItem(itemStack.getItem());
		if (block == ModBlocks.magicChest) {
			TileEntityRendererDispatcher.instance.renderTileEntityAt(this.temc, 0.0D, 0.0D, 0.0D, 0.0F);
		} else {
			super.renderByItem(itemStack);
		}
	}
}

/*public class MagicChestInventoryRenderer implements IItemRenderer {
	private TileEntitySpecialRenderer render;
	private TileEntity entity;
	
	public MagicChestInventoryRenderer(TileEntitySpecialRenderer render, TileEntity entity) {
		this.entity = entity;
		this.render = render;
	}
	
	@Override public boolean handleRenderType(ItemStack item, ItemRenderType type) { return true; }
	@Override public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) { return true; }

	@Override public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (type == IItemRenderer.ItemRenderType.ENTITY) GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
		this.render.renderTileEntityAt(this.entity, 0.0D, 0.0D, 0.0D, 0.0F, 0);
	}
}*/