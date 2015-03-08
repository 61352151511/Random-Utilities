package com.sixonethree.randomutilities.client.renderer;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;

import com.sixonethree.randomutilities.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.client.model.ModelMagicChest;
import com.sixonethree.randomutilities.reference.Reference;

public class MagicChestRenderer extends TileEntitySpecialRenderer {
	private final ModelMagicChest model;
	
	private RenderBlocks renderBlocks = new RenderBlocks();
	
	public MagicChestRenderer() {
		this.model = new ModelMagicChest();
	}
	
	@Override public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		TileEntityMagicChest magicChest = (TileEntityMagicChest) te;
		int facing = magicChest.getFacing();
		
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
		if (facing == 2 || facing == 3) {
			GL11.glTranslatef(1F, 0.5F, 0.5F); // Y
			GL11.glRotatef(0F, 0F, 1F, 0F);
			GL11.glTranslatef(-0.5F, 0F, 0.5F); // X & Z
			GL11.glRotatef(0F, 0F, 1F, 0F);
			GL11.glTranslated(0D, 0D, 0F);
		} else { // Rotate 90 degrees for east/west
			GL11.glTranslatef(1F, 0.5F, 0.5F); // Y
			GL11.glRotatef(0F, 0F, 1F, 0F);
			GL11.glTranslatef(0F, 0F, 1F); // X & Z
			GL11.glRotatef(90F, 0F, 1F, 0F);
			GL11.glTranslated(0D, 0D, 0F);
		}
		ItemStack stack = magicChest.getStackInSlot(0);
		Minecraft mc = Minecraft.getMinecraft();
		if (stack != null) {
			mc.renderEngine.bindTexture(stack.getItem() instanceof ItemBlock ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture);
			
			GL11.glScalef(2F, 2F, 2F);
			if (!ForgeHooksClient.renderEntityItem(new EntityItem(magicChest.getWorld(), magicChest.xCoord, magicChest.yCoord, magicChest.zCoord, stack), stack, 0F, 0F, magicChest.getWorld().rand, mc.renderEngine, renderBlocks, 1)) {
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				if (stack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(stack.getItem()).getRenderType())) {
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					GL11.glTranslatef(1F, 1.1F, 0F);
					renderBlocks.renderBlockAsItem(Block.getBlockFromItem(stack.getItem()), stack.getCurrentDurability(), 1F);
					GL11.glTranslatef(-1F, -1.1F, 0F);
					GL11.glScalef(2F, 2F, 2F);
				} else {
					int renderPass = 0;
					do {
						IIcon icon = stack.getItem().getIcon(stack, renderPass);
						if (icon != null) {
							Color color = new Color(stack.getItem().getColorFromItemStack(stack, renderPass));
							GL11.glColor3ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
							float f = icon.getMinU();
							float f1 = icon.getMaxU();
							float f2 = icon.getMinV();
							float f3 = icon.getMaxV();
							ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);
							GL11.glColor3f(1F, 1F, 1F);
						}
						renderPass ++;
					} while (renderPass < stack.getItem().getRenderPasses(stack.getCurrentDurability()));
				}
			}
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