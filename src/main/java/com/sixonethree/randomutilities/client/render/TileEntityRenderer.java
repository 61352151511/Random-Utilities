package com.sixonethree.randomutilities.client.render;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.utility.GlManager;
import com.sixonethree.randomutilities.utility.LogHelper;

public class TileEntityRenderer {
	@SubscribeEvent public void onWorldRender(RenderWorldLastEvent event) {
		Minecraft mc = Minecraft.getMinecraft();

		Entity cameraEntity = mc.getRenderViewEntity();
		BlockPos renderingVector = cameraEntity.getPosition();
		Frustum frustum = new Frustum();

		double viewX = cameraEntity.lastTickPosX + (cameraEntity.posX - cameraEntity.lastTickPosX) * event.partialTicks;
		double viewY = cameraEntity.lastTickPosY + (cameraEntity.posY - cameraEntity.lastTickPosY) * event.partialTicks;
		double viewZ = cameraEntity.lastTickPosZ + (cameraEntity.posZ - cameraEntity.lastTickPosZ) * event.partialTicks;
		frustum.setPosition(viewX, viewY, viewZ);

		WorldClient client = mc.theWorld;
		List<TileEntity> tileEntities = client.loadedTileEntityList;

		for(TileEntity entity : tileEntities) {
			if (entity == null) return;
			if (entity instanceof TileEntityDisplayTable) {
				if (this.isInRangeToRender3d(entity, renderingVector.getX(), renderingVector.getY(), renderingVector.getZ(), 1.0D)) {
					renderDisplayTable((TileEntityDisplayTable) entity, event.partialTicks, cameraEntity);
				}
			}
			if (entity instanceof TileEntityMagicChest) {
				if (this.isInRangeToRender3d(entity, renderingVector.getX(), renderingVector.getY(), renderingVector.getZ(), 1.0D)) {
					renderMagicChest((TileEntityMagicChest) entity, event.partialTicks, cameraEntity);
				}
			}
		}
	}
	
	private void renderDisplayTable(TileEntityDisplayTable entity, float partialTicks, Entity cameraEntity) {
		LogHelper.warn("Rendering display table");
	}
	
	private void renderMagicChest(TileEntityMagicChest magicChest, float partialTicks, Entity cameraEntity) {
		int x = magicChest.getPos().getX();
		int y = magicChest.getPos().getY();
		int z = magicChest.getPos().getZ();
		double x2 = Minecraft.getMinecraft().getRenderManager().viewerPosX;
		double y2 = Minecraft.getMinecraft().getRenderManager().viewerPosY;
		double z2 = Minecraft.getMinecraft().getRenderManager().viewerPosZ;
		GlStateManager.pushMatrix();
		GlStateManager.translate(x - x2, y - y2, z - z2);
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.scale(0.5, 0.5, 0.5);
		GlManager.translateThenRotate(1.5, 0.3, 0.5, 0, 0, 1, 0);
		GlManager.translateThenRotate(-0.5, 0, 0.5, 0, 0, 1, 0);
		ItemStack stack = magicChest.getStackInSlot(0);
		if (stack != null) {
			ItemStack renderStack = stack.copy();
			EntityItem ghostItem = new EntityItem(magicChest.getWorld(), x, y, z, renderStack);
			float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
			renderStack.stackSize = 1;
			ghostItem.hoverStart = 0F;
			
			GlStateManager.scale(2, 2, 2);
			GlStateManager.rotate(rotationAngle, 0, 1, 0);
			Minecraft.getMinecraft().getRenderManager().doRenderEntity(ghostItem, 0, 0, 0, 0, 0, false);
		}
		GlStateManager.popMatrix();
	}
	
	/* Copied from Vanilla Entity class */
	
	/**
	 * 
	 * @param entity
	 * @param renderX
	 * @param renderY
	 * @param renderZ
	 * @param renderDistanceWeight - Most entities have a weight of 1.0D, the exceptions are Arrow, and Players which are 10.0D
	 * @return Whether the special rendering should be done or not.
	 */
	private boolean isInRangeToRender3d(TileEntity entity, double renderX, double renderY, double renderZ, double renderDistanceWeight) {
		double d0 = entity.getPos().getX() - renderX;
		double d1 = entity.getPos().getY() - renderY;
		double d2 = entity.getPos().getZ() - renderZ;
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;
		return this.isInRangeToRenderDist(entity, d3, renderDistanceWeight);
	}
	
	private boolean isInRangeToRenderDist(TileEntity entity, double distance, double renderDistanceWeight) {
		double d0 = entity.getRenderBoundingBox().getAverageEdgeLength();
		if (Double.isNaN(d0)) {
			d0 = 1.0D;
		}
		
		d0 = d0 * 64.0D * renderDistanceWeight;
		return distance < d0 * d0;
	}
}