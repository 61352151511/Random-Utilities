package com.sixonethree.randomutilities.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.sixonethree.randomutilities.client.model.ModelMagicChest;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.reference.Reference;
import com.sixonethree.randomutilities.utility.GlManager;

public class MagicChestRenderer extends TileEntitySpecialRenderer {
	private final ModelMagicChest model;
	private RenderManager renderManager;
	ResourceLocation magicChestTexture = (new ResourceLocation(Reference.MOD_ID.toLowerCase() + ":textures/blocks/MagicChest.png"));
	
	public MagicChestRenderer(RenderManager rm) {
		this.model = new ModelMagicChest();
		this.renderManager = rm;
	}
	
	@Override public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		TileEntityMagicChest magicChest = (TileEntityMagicChest) te;
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
		Minecraft.getMinecraft().renderEngine.bindTexture(magicChestTexture);
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
		GlManager.translateThenRotate(1.5, 0.3, 0.5, 0, 0, 1, 0);
		GlManager.translateThenRotate(-0.5, 0, 0.5, 0, 0, 1, 0);
		ItemStack stack = magicChest.getStackInSlot(0);
		if (stack != null) {
			ItemStack renderStack = stack.copy();
			EntityItem ghostItem = new EntityItem(te.getWorld(), x, y, z, renderStack);
			float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
			renderStack.stackSize = 1;
			ghostItem.hoverStart = 0F;
			
			GlStateManager.scale(2, 2, 2);
			GlStateManager.rotate(rotationAngle, 0, 1, 0);
			renderManager.doRenderEntity(ghostItem, 0, 0, 0, 0, 0, false);
		}
		GlManager.popMatrix(2);
	}
}