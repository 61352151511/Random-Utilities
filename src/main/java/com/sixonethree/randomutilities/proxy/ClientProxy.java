package com.sixonethree.randomutilities.proxy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.sixonethree.randomutilities.client.event.OnEntityRender;
import com.sixonethree.randomutilities.client.gui.GuiManager;
import com.sixonethree.randomutilities.client.gui.GuiManager.GUI;
import com.sixonethree.randomutilities.client.model.ModelHelper;
import com.sixonethree.randomutilities.client.render.DisplayTableRenderer;
import com.sixonethree.randomutilities.client.render.MagicChestRenderer;
import com.sixonethree.randomutilities.client.render.ModeledBlockInventoryRenderer;
import com.sixonethree.randomutilities.client.render.RenderCreepySpider;
import com.sixonethree.randomutilities.common.block.BlockTest;
import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.common.entity.EntityCreepySpider;
import com.sixonethree.randomutilities.common.event.ModelEvent;
import com.sixonethree.randomutilities.common.init.ModBlocks;
import com.sixonethree.randomutilities.common.init.ModItems;

public class ClientProxy extends CommonProxy {
    public static ModelResourceLocation blockLocation = new ModelResourceLocation("test", "normal");
    public static ModelResourceLocation itemLocation = new ModelResourceLocation("test", "inventory");
	
	@Override public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		Item item = Item.getItemFromBlock(BlockTest.instance);
        ModelLoader.setCustomModelResourceLocation(item, 0, itemLocation);
        ModelLoader.setCustomStateMapper(BlockTest.instance, new StateMapperBase(){
            @Override protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return blockLocation;
            }
        });
        MinecraftForge.EVENT_BUS.register(ModelEvent.instance);
        MinecraftForge.EVENT_BUS.register(new OnEntityRender());
	}
	
	@Override public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	
	@Override public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	@Override public void bindTileEntitySpecialRenderers() {
		TileEntitySpecialRenderer mcr = new MagicChestRenderer(Minecraft.getMinecraft().getRenderManager());
		TileEntitySpecialRenderer dtr = new DisplayTableRenderer(Minecraft.getMinecraft().getRenderManager());
		ModelHelper.removeblockstate(ModBlocks.magicChest);
		ModelHelper.removeblockstate(ModBlocks.displayTable);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagicChest.class, mcr);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplayTable.class, dtr);
	}
	
	@Override public void registerRenderInformation() {
		TileEntityItemStackRenderer.instance = new ModeledBlockInventoryRenderer();
		RenderingRegistry.registerEntityRenderingHandler(EntityCreepySpider.class, new RenderCreepySpider(Minecraft.getMinecraft().getRenderManager()));
	}
	
	@Override public void registerItemRenders() {
		ModelHelper.registerBlock(ModBlocks.magicChest);
		ModelHelper.registerBlock(ModBlocks.displayTable);
		ModelHelper.registerItem(ModItems.lunchbox, 0, 1);
		ModelHelper.registerItem(ModItems.heartCanister, 0, 1, 2, 3);
		ModelHelper.registerItem(ModItems.combined);
		ModelHelper.registerItem(ModItems.magicCard);
	}
	
	@Override public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	@Override public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if (te == null) return null;
		if (ID == 0) return GuiManager.GUI.buildGUI(GUI.MAGIC, player.inventory, (TileEntityMagicChest) te);
		if (ID == 1) return GuiManager.GUI.buildGUI(GUI.DISPLAYTABLE, player.inventory, (TileEntityDisplayTable) te);
		return null;
	}
}