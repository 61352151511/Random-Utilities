package com.sixonethree.randomutilities.proxy;

import com.sixonethree.randomutilities.client.RandomUtilitiesItemColors;
import com.sixonethree.randomutilities.client.gui.GuiManager;
import com.sixonethree.randomutilities.client.gui.GuiManager.GUI;
import com.sixonethree.randomutilities.client.render.tileentity.DisplayTableRenderer;
import com.sixonethree.randomutilities.client.render.tileentity.MagicChestRenderer;
import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.common.init.ModBlocks;
import com.sixonethree.randomutilities.common.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends ServerProxy {
	@Override public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		this.registerRenderersPreInit();
	}
	
	@Override public void init(FMLInitializationEvent event) {
		super.init(event);
		this.registerRenderersInit();
	}
	
	@Override public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	@Override public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if (te == null) return null;
		if (ID == 0) return GuiManager.GUI.buildGUI(GUI.MAGIC, player.inventory, (TileEntityMagicChest) te);
		if (ID == 1) return GuiManager.GUI.buildGUI(GUI.DISPLAYTABLE, player.inventory, (TileEntityDisplayTable) te);
		return null;
	}
	
	@Override public World getClientWorld() {
		return FMLClientHandler.instance().getClient().world;
	}
	
	private void registerRenderersPreInit() {
		this.registerItem(ModItems.lunchbox, 0);
		this.registerItem(ModItems.lunchbox, 1);
		this.registerItem(ModItems.heartCanister, 0);
		this.registerItem(ModItems.heartCanister, 1);
		this.registerItem(ModItems.heartCanister, 2);
		this.registerItem(ModItems.heartCanister, 3);
		this.registerItem(ModItems.combined);
		this.registerItem(ModItems.magicCard);
		this.registerBlock(ModBlocks.magicChest);
		this.registerBlock(ModBlocks.displayTable);
	}
	
	private void registerRenderersInit() {
		TileEntitySpecialRenderer<TileEntityDisplayTable> dtr = new DisplayTableRenderer(Minecraft.getMinecraft().getRenderManager());
		TileEntitySpecialRenderer<TileEntityMagicChest> mcr = new MagicChestRenderer(Minecraft.getMinecraft().getRenderManager());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplayTable.class, dtr);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagicChest.class, mcr);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(RandomUtilitiesItemColors.lunchbox, ModItems.lunchbox);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(RandomUtilitiesItemColors.heartCanister, ModItems.heartCanister);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(RandomUtilitiesItemColors.combined, ModItems.combined);
	}
	
	private void registerBlock(Block block) {
		this.registerItem(Item.getItemFromBlock(block));
	}
	
	private void registerBlock(Block block, int metadata) {
		this.registerItem(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	private void registerBlock(Block block, int metadata, ModelResourceLocation model) {
		this.registerItem(Item.getItemFromBlock(block), metadata, model);
	}
	
	private void registerItem(Item item) {
		this.registerItem(item, 0);
	}
	
	private void registerItem(Item item, int metadata) {
		this.registerItem(item, metadata, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	private void registerItem(Item item, int metadata, ModelResourceLocation model) {
		ModelLoader.setCustomModelResourceLocation(item, metadata, model);
	}
}