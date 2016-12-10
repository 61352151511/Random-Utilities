package com.sixonethree.randomutilities.client.render.tileentity;

import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.utility.GlManager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class DisplayTableRenderer extends TileEntitySpecialRenderer<TileEntityDisplayTable> {
	private RenderManager renderManager;
	
	/* Constructors */
	
	public DisplayTableRenderer(RenderManager rm) {
		this.renderManager = rm;
	}
	
	/* Overridden */
	
	@Override public void renderTileEntityAt(TileEntityDisplayTable displayTable, double x, double y, double z, float partialTicks, int destroyStage) {
		EnumFacing facing = displayTable.getFacing();

		/* RENDER THE ITEMS ON TOP */
		
		GlStateManager.pushMatrix();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.translate(x, y, z);
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.5, 0.5, 0.5);
		
		GlStateManager.pushMatrix();
		if (facing == EnumFacing.NORTH) GlManager.translateThenRotate(1.8, 1.65, 1.8, 0, 0, 1, 0);
		if (facing == EnumFacing.SOUTH) GlManager.translateThenRotate(0.2, 1.65, 0.2, 0, 0, 1, 0);
		if (facing == EnumFacing.WEST) GlManager.translateThenRotate(1.8, 1.65, 0.2, 0, 0, 1, 0);
		if (facing == EnumFacing.EAST) GlManager.translateThenRotate(0.2, 1.65, 1.8, 0, 0, 1, 0);
		GlStateManager.rotate(0, 0, 1, 0);
		
		int translates = 0;
		
		for (int i = 0; i < 25; i ++) {
			ItemStack stack = displayTable.getStackInSlot(i);
			if (!stack.isEmpty()) {
				ItemStack renderStack = stack.copy();
				renderStack.setCount(1);
				EntityItem ghostItem = new EntityItem(displayTable.getWorld(), x, y, z, renderStack);
				ghostItem.hoverStart = 0F;
				if (!(stack.getItem() instanceof ItemBlock)) {
					if (facing == EnumFacing.NORTH) GlStateManager.rotate(-90, 0, 1, 0);
					if (facing == EnumFacing.SOUTH) GlStateManager.rotate(90, 0, 1, 0);
					if (facing == EnumFacing.WEST) GlStateManager.rotate(180, 0, 1, 0);
				}
				this.renderManager.doRenderEntity(ghostItem, 0, 0, 0, 0, 0, false);
				if (!(stack.getItem() instanceof ItemBlock)) {
					if (facing == EnumFacing.NORTH) GlStateManager.rotate(90, 0, 1, 0);
					if (facing == EnumFacing.SOUTH) GlStateManager.rotate(-90, 0, 1, 0);
					if (facing == EnumFacing.WEST) GlStateManager.rotate(-180, 0, 1, 0);
				}
			}
			if (facing == EnumFacing.NORTH) GlStateManager.translate(-0.4, 0, 0);
			if (facing == EnumFacing.SOUTH) GlStateManager.translate(0.4, 0, 0);
			if (facing == EnumFacing.WEST) GlStateManager.translate(0, 0, 0.4);
			if (facing == EnumFacing.EAST) GlStateManager.translate(0, 0, -0.4);
			
			translates ++;
			if (translates == 5) {
				translates = 0;
				if (facing == EnumFacing.NORTH) GlStateManager.translate(2, 0, -0.4);
				if (facing == EnumFacing.SOUTH) GlStateManager.translate(-2, 0, 0.4);
				if (facing == EnumFacing.WEST) GlStateManager.translate(-0.4, 0, -2);
				if (facing == EnumFacing.EAST) GlStateManager.translate(0.4, 0, 2);
			}
		}
		
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}
}