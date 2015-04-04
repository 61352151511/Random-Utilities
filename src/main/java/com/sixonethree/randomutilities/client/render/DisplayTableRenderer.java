package com.sixonethree.randomutilities.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.sixonethree.randomutilities.client.model.ModelDisplayTable;
import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.reference.Reference;
import com.sixonethree.randomutilities.utility.GlManager;

public class DisplayTableRenderer extends TileEntitySpecialRenderer {
	private final ModelDisplayTable model;
	private RenderManager renderManager;
	
	public DisplayTableRenderer(RenderManager rm) {
		this.model = new ModelDisplayTable();
		this.renderManager = rm;
	}
	
	@Override public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		TileEntityDisplayTable starCrafting = (TileEntityDisplayTable) te;
		int facing = starCrafting.getFacing();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		ResourceLocation textures = (new ResourceLocation(Reference.MOD_ID.toLowerCase() + ":textures/blocks/StarCraftingTable.png"));
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180, 0, 0, 1);
		this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GlManager.popMatrix(2);
		
		/* RENDER THE INSIDE */
		
		GlStateManager.pushMatrix();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.translate(x, y, z);
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.5, 0.5, 0.5);
		
		GlStateManager.pushMatrix();
		if (facing == 2) GlManager.translateThenRotate(1.8, 1.65, 1.8, 90, 0, 1, 0);
		if (facing == 3) GlManager.translateThenRotate(0.2, 1.65, 0.2, -90, 0, 1, 0);
		if (facing == 4) GlManager.translateThenRotate(1.8, 1.65, 0.2, 180, 0, 1, 0);
		if (facing == 5) GlManager.translateThenRotate(0.2, 1.65, 1.8, 0, 0, 1, 0);
		GlStateManager.rotate(0, 0, 1, 0);
		
		int translates = 0;
		
		for (int i = 0; i < 25; i ++) {
			ItemStack stack = starCrafting.getStackInSlot(i);
			if (stack != null) {
				ItemStack renderStack = stack.copy();
				renderStack.stackSize = 1;
				EntityItem ghostItem = new EntityItem(te.getWorld(), x, y, z, renderStack);
				ghostItem.hoverStart = 0F;
				if (!(stack.getItem() instanceof ItemBlock)) {
					if (facing == 2) GlStateManager.rotate(-90, 0, 1, 0);
					if (facing == 3) GlStateManager.rotate(90, 0, 1, 0);
					if (facing == 4) GlStateManager.rotate(180, 0, 1, 0);
				}
				renderManager.doRenderEntity(ghostItem, 0, 0, 0, 0, 0, false);
				if (!(stack.getItem() instanceof ItemBlock)) {
					if (facing == 2) GlStateManager.rotate(90, 0, 1, 0);
					if (facing == 3) GlStateManager.rotate(-90, 0, 1, 0);
					if (facing == 4) GlStateManager.rotate(-180, 0, 1, 0);
				}
			}
			GlStateManager.translate(0, 0, - 0.4);

			translates ++;
			if (translates == 5) {
				translates = 0;
				GlStateManager.translate(0.4, 0, 2);
			}
		}
		
		GlManager.popMatrix(2);
	}
}