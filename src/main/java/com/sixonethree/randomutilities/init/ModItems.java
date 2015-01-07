package com.sixonethree.randomutilities.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.sixonethree.randomutilities.item.ItemBase;
import com.sixonethree.randomutilities.item.ItemCombined;
import com.sixonethree.randomutilities.item.ItemHeartCanister;
import com.sixonethree.randomutilities.item.ItemLunchbox;
import com.sixonethree.randomutilities.reference.Reference;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
	public static final ItemBase lunchbox = new ItemLunchbox();
	public static final ItemBase heartCanister = new ItemHeartCanister();
	public static final ItemBase combined = new ItemCombined();
	
	public static void init() {
		GameRegistry.registerItem(lunchbox, "lunchbox");
		GameRegistry.registerItem(heartCanister, "heartCanister");
		GameRegistry.registerItem(combined, "combined");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(lunchbox, 0, new ModelResourceLocation("randomutilities:lunchbox", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(lunchbox, 1, new ModelResourceLocation("randomutilities:lunchbox", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(heartCanister, 0, new ModelResourceLocation("randomutilities:heartCanister", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(heartCanister, 1, new ModelResourceLocation("randomutilities:heartCanister", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(heartCanister, 2, new ModelResourceLocation("randomutilities:heartCanister", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(heartCanister, 3, new ModelResourceLocation("randomutilities:heartCanister", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(combined, 0, new ModelResourceLocation("randomutilities:lunchbox", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(combined, 1, new ModelResourceLocation("randomutilities:heartCanister", "inventory"));
	}
}