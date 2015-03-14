package com.sixonethree.randomutilities.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import com.sixonethree.randomutilities.client.gui.GUIChest;
import com.sixonethree.randomutilities.client.model.ModelHelper;
import com.sixonethree.randomutilities.client.renderer.MagicChestRenderHelper;
import com.sixonethree.randomutilities.client.renderer.MagicChestRenderer;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.common.init.ModBlocks;
import com.sixonethree.randomutilities.common.init.ModItems;

public class ClientProxy extends CommonProxy {
	@Override public void init() {
		super.init();
	}
	
	@Override public void registerRenderInformation() {
		TileEntitySpecialRenderer mcr = new MagicChestRenderer(Minecraft.getMinecraft().getRenderManager());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagicChest.class, mcr);
		TileEntityItemStackRenderer.instance = new MagicChestRenderHelper();
		// MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.magicChest), new MagicChestInventoryRenderer(mcr, new TileEntityMagicChest()));
	}
	
	@Override public void registerItemRenders() {
		ModelHelper.removeblockstate(ModBlocks.magicChest);
		ModelHelper.registerBlock(ModBlocks.magicChest);
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
		if (te != null && te instanceof TileEntityMagicChest) {
			return GUIChest.GUI.buildGUI(player.inventory, (TileEntityMagicChest) te);
		} else {
			return null;
		}
	}
}