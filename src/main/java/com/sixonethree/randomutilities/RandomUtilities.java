package com.sixonethree.randomutilities;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import com.sixonethree.randomutilities.handler.ConfigurationHandler;
import com.sixonethree.randomutilities.init.Commands;
import com.sixonethree.randomutilities.init.ModItems;
import com.sixonethree.randomutilities.init.Recipes;
import com.sixonethree.randomutilities.proxy.IProxy;
import com.sixonethree.randomutilities.reference.Reference;
import com.sixonethree.randomutilities.utility.LogHelper;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION) public class RandomUtilities {
	@Mod.Instance(Reference.MOD_ID) public static RandomUtilities instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY) public static IProxy proxy;
	
	@Mod.EventHandler public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
		
		LogHelper.info("Pre-Init Complete");
	}
	
	@Mod.EventHandler public void init(FMLInitializationEvent event) {
		proxy.init();
		ModItems.init();
		Recipes.init();
		LogHelper.info("Init Complete");
	}
	
	@Mod.EventHandler public void postInit(FMLPostInitializationEvent event) {
		Recipes.initLunchboxRecipes();
		LogHelper.info("Post-Init Complete");
	}
	
	@Mod.EventHandler public void serverStarting(FMLServerStartingEvent event) {
		proxy.init();
		Commands.init(event);
	}
}