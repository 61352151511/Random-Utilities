package com.sixonethree.randomutilities.common.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sixonethree.randomutilities.client.model.ModelTest;
import com.sixonethree.randomutilities.proxy.ClientProxy;

@SuppressWarnings("deprecation") public class ModelEvent {
	public static final ModelEvent instance = new ModelEvent();
	
	private ModelEvent() {}
	
	@SubscribeEvent public void onModelBake(ModelBakeEvent event) {
		TextureAtlasSprite base = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/slime");
		TextureAtlasSprite overlay = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/redstone_block");
        IBakedModel customModel = new ModelTest(base, overlay);
        event.modelRegistry.putObject(ClientProxy.blockLocation, customModel);
        event.modelRegistry.putObject(ClientProxy.itemLocation, customModel);
	}
}