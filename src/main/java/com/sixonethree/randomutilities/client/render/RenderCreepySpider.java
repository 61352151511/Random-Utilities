package com.sixonethree.randomutilities.client.render;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import com.sixonethree.randomutilities.common.entity.EntityCreepySpider;
import com.sixonethree.randomutilities.utility.LogHelper;

public class RenderCreepySpider extends RenderLiving {
	private static final ResourceLocation spiderTextures = new ResourceLocation("textures/entity/spider/spider.png");
	
	public RenderCreepySpider(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelSpider(), 1.0F);
		this.addLayer(new LayerCreepySpiderEyes(this));
	}
	
	protected float getDeathMaxRotation(EntityCreepySpider entityLivingBaseIn) {
		return 180.0F;
	}
	
	protected ResourceLocation getEntityTexture(EntityCreepySpider entity) {
		return spiderTextures;
	}
	
	@Override protected float getDeathMaxRotation(EntityLivingBase entityLivingBaseIn) {
		return this.getDeathMaxRotation((EntityCreepySpider) entityLivingBaseIn);
	}
	
	@Override protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityCreepySpider) entity);
	}
	
	/* The fun stuff */
	
	@Override public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre(entity, this, x, y, z))) return;
		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
		this.mainModel.isRiding = entity.isRiding();
		this.mainModel.isChild = entity.isChild();
		
		try {
			float f2 = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
			float f3 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
			float f4 = f3 - f2;
			float f5;
			
			if (entity.isRiding() && entity.ridingEntity instanceof EntityLivingBase) {
				EntityLivingBase entitylivingbase1 = (EntityLivingBase) entity.ridingEntity;
				f2 = this.interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset, partialTicks);
				f4 = f3 - f2;
				f5 = MathHelper.wrapAngleTo180_float(f4);
				
				if (f5 < -85.0F) {
					f5 = -85.0F;
				}
				
				if (f5 >= 85.0F) {
					f5 = 85.0F;
				}
				
				f2 = f3 - f5;
				
				if (f5 * f5 > 2500.0F) {
					f2 += f5 * 0.2F;
				}
			}
			
			float f9 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
			boolean onGround = entity.onGround;
			boolean inAir = entity.isAirBorne;
			//TODO Work out kinks, only rotate the spider when they're climbing the wall, not punched.
			//Make the spider climb the wall and face upwards, possibly move their legs too!
			GlStateManager.translate((float) x, (float) y, (float) z);
			if (!onGround || inAir) {
				float yaw = entity.rotationYaw;
				if (yaw >= 135 || yaw <= -135) {
					GlStateManager.rotate(180F, 0F, 1F, 0F);
					GlStateManager.rotate(-90F, 1F, 0F, 0F);
					GlStateManager.translate(0F, -1F, 1F);
				} else if (yaw >= 45) {
					GlStateManager.translate(-1F, 1F, 0F);
					GlStateManager.rotate(-90F, 0F, 1F, 0F);
					GlStateManager.rotate(-90F, 1F, 0F, 0F);
				} else if (yaw <= -45) {
					GlStateManager.translate(1F, 1F, 0F);
					GlStateManager.rotate(90F, 0F, 1F, 0F);
					GlStateManager.rotate(-90F, 1F, 0F, 0F);
				} else {
					GlStateManager.rotate(-90F, 1F, 0F, 0F);
					GlStateManager.translate(0F, -1F, 1F);
				}
			}
			f5 = this.handleRotationFloat(entity, partialTicks);
			this.rotateCorpse(entity, f5, f2, partialTicks);
			GlStateManager.enableRescaleNormal();
			GlStateManager.scale(-1.0F, -1.0F, 1.0F);
			this.preRenderCallback(entity, partialTicks);
			GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
			float f7 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
			float f8 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);
			
			if (entity.isChild()) {
				f8 *= 3.0F;
			}
			
			if (f7 > 1.0F) {
				f7 = 1.0F;
			}
			
			GlStateManager.enableAlpha();
			this.mainModel.setLivingAnimations(entity, f8, f7, partialTicks);
			this.mainModel.setRotationAngles(f8, f7, f5, f4, f9, 0.0625F, entity);
			boolean flag;
			
			if (this.renderOutlines) {
				flag = this.setScoreTeamColor(entity);
				this.renderModel(entity, f8, f7, f5, f4, f9, 0.0625F);
				
				if (flag) {
					this.unsetScoreTeamColor();
				}
			} else {
				flag = this.setDoRenderBrightness(entity, partialTicks);
				this.renderModel(entity, f8, f7, f5, f4, f9, 0.0625F);
				
				if (flag) {
					this.unsetBrightness();
				}
				
				GlStateManager.depthMask(true);
				
				if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isSpectator()) {
					this.renderLayers(entity, f8, f7, partialTicks, f5, f4, f9, 0.0625F);
				}
			}
			
			GlStateManager.disableRescaleNormal();
		} catch (Exception exception) {
			LogHelper.warn("Couldn\'t render entity");
			LogHelper.warn(exception);
		}
		
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.enableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GlStateManager.enableCull();
		GlStateManager.popMatrix();
		
		if (!this.renderOutlines) {
			// super.doRender(entity, x, y, z, entityYaw, partialTicks);
		}
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post(entity, this, x, y, z));
	}
	
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		this.doRender((EntityLivingBase) entity, x, y, z, entityYaw, partialTicks);
	}
}