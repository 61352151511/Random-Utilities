package com.sixonethree.randomutilities.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.sixonethree.randomutilities.client.model.ModelMagicChest;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.reference.Reference;

public class MagicChestRenderer extends TileEntitySpecialRenderer {
	private final ModelMagicChest model;
	
	// private RenderBlocks renderBlocks = new RenderBlocks();
	private RenderManager renderManager;
	
	public MagicChestRenderer(RenderManager rm) {
		this.model = new ModelMagicChest();
		this.renderManager = rm;
	}
	
	@Override public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		TileEntityMagicChest magicChest = (TileEntityMagicChest) te;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		ResourceLocation textures = (new ResourceLocation(Reference.MOD_ID.toLowerCase() + ":textures/blocks/MagicChest.png"));
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		
		/* RENDER THE INSIDE */
		
		GL11.glPushMatrix();
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glTranslated(x, y, z);
		
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(1.5F, 0.3F, 0.5F); // Y
		GL11.glRotatef(0F, 0F, 1F, 0F);
		GL11.glTranslatef(-0.5F, 0F, 0.5F); // X & Z
		GL11.glRotatef(0F, 0F, 1F, 0F);
		GL11.glTranslated(0D, 0D, 0F);
		ItemStack stack = magicChest.getStackInSlot(0);
		if (stack != null) {
			ItemStack renderStack = stack.copy();
			renderStack.stackSize = 1;
			EntityItem ghostItem = new EntityItem(te.getWorld(), x, y, z, renderStack);
			ghostItem.hoverStart = 0F;
			GL11.glScalef(2F, 2F, 2F);
			float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
			GL11.glRotatef(rotationAngle, 0F, 1F, 0F);
			renderManager.doRenderEntity(ghostItem, 0, 0, 0, 0, 0, false);
		}
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5F, 1.8F, 0.5F);
		GL11.glRotatef(180F, 1F, 0F, 1F);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glPopMatrix();
	}
}