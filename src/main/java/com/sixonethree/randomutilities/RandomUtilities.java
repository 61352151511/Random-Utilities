package com.sixonethree.randomutilities;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.sixonethree.randomutilities.common.handler.ConfigurationHandler;
import com.sixonethree.randomutilities.common.handler.PacketHandler;
import com.sixonethree.randomutilities.common.init.Commands;
import com.sixonethree.randomutilities.common.init.ModBlocks;
import com.sixonethree.randomutilities.common.init.ModEntities;
import com.sixonethree.randomutilities.common.init.ModItems;
import com.sixonethree.randomutilities.common.init.Recipes;
import com.sixonethree.randomutilities.proxy.CommonProxy;
import com.sixonethree.randomutilities.reference.Reference;
import com.sixonethree.randomutilities.utility.LogHelper;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION) public class RandomUtilities {
	@Mod.Instance(Reference.MOD_ID) public static RandomUtilities instance;
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY) public static CommonProxy proxy;
	
    @SuppressWarnings("unchecked") public static final IUnlistedProperty<Integer>[] properties = new IUnlistedProperty[6];
    
    static {
        for (EnumFacing facing : EnumFacing.values()) {
            RandomUtilities.properties[facing.ordinal()] = Properties.toUnlisted(PropertyInteger.create(facing.getName(), 0, (1 << (3 * 3)) - 1));
        }
    }
	
	@Mod.EventHandler public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		PacketHandler.INSTANCE.ordinal();
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());

		LogHelper.info("Pre-Init Complete");
	}
	
	@Mod.EventHandler public void init(FMLInitializationEvent event) {
		ModBlocks.init();
		ModItems.init();
		ModEntities.init();
		
		proxy.init(event);
		proxy.bindTileEntitySpecialRenderers();
		proxy.registerRenderInformation();
		proxy.registerItemRenders();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		Recipes.init();
		LogHelper.info("Init Complete");
	}
	
	@Mod.EventHandler public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		Recipes.initLunchboxRecipes();
		LogHelper.info("Post-Init Complete");
	}
	
	@Mod.EventHandler public void serverStarting(FMLServerStartingEvent event) {
		Commands.init(event);
	}
}