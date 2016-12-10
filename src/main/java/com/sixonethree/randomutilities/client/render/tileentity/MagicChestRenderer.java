package com.sixonethree.randomutilities.client.render.tileentity;

import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.utility.GlManager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class MagicChestRenderer extends TileEntitySpecialRenderer<TileEntityMagicChest> {
	private RenderManager renderManager;
	
	/* Constructors */
	
	public MagicChestRenderer(RenderManager rm) {
		this.renderManager = rm;
	}
	
	/* Overridden */
	
	@Override public void renderTileEntityAt(TileEntityMagicChest magicChest, double x, double y, double z, float partialTicks, int destroyStage) {
		
		/* RENDER THE INSIDE */
		
		GlStateManager.pushMatrix();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.translate(x, y, z);
		
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.5, 0.5, 0.5);
		GlManager.translateThenRotate(1.5, 0.3, 0.5, 0, 0, 1, 0);
		GlManager.translateThenRotate(-0.5, 0, 0.5, 0, 0, 1, 0);
		ItemStack stack = magicChest.getStackInSlot(0);
		if (!stack.isEmpty()) {
			ItemStack renderStack = stack.copy();
			EntityItem ghostItem = new EntityItem(magicChest.getWorld(), x, y, z, renderStack);
			float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
			renderStack.setCount(1);
			ghostItem.hoverStart = 0F;
			
			GlStateManager.scale(2, 2, 2);
			GlStateManager.rotate(rotationAngle, 0, 1, 0);
			this.renderManager.doRenderEntity(ghostItem, 0, 0, 0, 0, 0, false);
		}
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}