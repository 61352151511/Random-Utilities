package com.sixonethree.randomutilities.client.event;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityPig;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OnEntityRender {
	@SubscribeEvent public void onEntityRenderPre(RenderLivingEvent.Pre event) {
		if (event.entity instanceof EntityPig) {
			GlStateManager.pushMatrix();
			GlStateManager.rotate(90F, 1F, 1F, 0F);
		}
	}
	
	@SubscribeEvent public void onEntityRenderPost(RenderLivingEvent.Post event) {
		if (event.entity instanceof EntityPig) {
			GlStateManager.popMatrix();
		}
	}
}