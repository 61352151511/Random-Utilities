package com.sixonethree.randomutilities.proxy;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

import com.sixonethree.randomutilities.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.client.gui.GUIChest;
import com.sixonethree.randomutilities.client.renderer.MagicChestInventoryRenderer;
import com.sixonethree.randomutilities.client.renderer.MagicChestRenderer;
import com.sixonethree.randomutilities.init.ModBlocks;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	@Override public void init() {
		super.init();
	}
	
	@Override public void registerRenderInformation() {
		TileEntitySpecialRenderer mcr = new MagicChestRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagicChest.class, mcr);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.magicChest), new MagicChestInventoryRenderer(mcr, new TileEntityMagicChest()));
	}
	
	@Override public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	@Override public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityMagicChest) {
			return GUIChest.GUI.buildGUI(player.inventory, (TileEntityMagicChest) te);
		} else {
			return null;
		}
	}
}