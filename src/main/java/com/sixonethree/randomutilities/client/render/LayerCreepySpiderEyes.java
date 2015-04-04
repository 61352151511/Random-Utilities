package com.sixonethree.randomutilities.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import com.sixonethree.randomutilities.common.entity.EntityCreepySpider;

public class LayerCreepySpiderEyes implements LayerRenderer {
	private static final ResourceLocation SPIDER_EYES = new ResourceLocation("textures/entity/spider_eyes.png");
	private final RenderCreepySpider spiderRenderer;
	
	public LayerCreepySpiderEyes(RenderCreepySpider spiderRendererIn) {
		this.spiderRenderer = spiderRendererIn;
	}
	
	public void func_177148_a(EntityCreepySpider spider, float p_177148_2_, float p_177148_3_, float p_177148_4_, float p_177148_5_, float p_177148_6_, float p_177148_7_, float p_177148_8_) {
		this.spiderRenderer.bindTexture(SPIDER_EYES);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(1, 1);
		
		if (spider.isInvisible()) {
			GlStateManager.depthMask(false);
		} else {
			GlStateManager.depthMask(true);
		}
		
		char c0 = 61680;
		int i = c0 % 65536;
		int j = c0 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) i / 1.0F, (float) j / 1.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.spiderRenderer.getMainModel().render(spider, p_177148_2_, p_177148_3_, p_177148_5_, p_177148_6_, p_177148_7_, p_177148_8_);
		int k = spider.getBrightnessForRender(p_177148_4_);
		i = k % 65536;
		j = k / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) i / 1.0F, (float) j / 1.0F);
		this.spiderRenderer.func_177105_a(spider, p_177148_4_);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
	}
	
	@Override public boolean shouldCombineTextures() {
		return false;
	}
	
	@Override public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
		this.func_177148_a((EntityCreepySpider) entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
	}
}