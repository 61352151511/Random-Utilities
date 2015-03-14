package com.sixonethree.randomutilities.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.sixonethree.randomutilities.common.block.ContainerMagicChest;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.common.event.PlayerEvents;

public abstract class CommonProxy implements IProxy, IGuiHandler {
	public void init() {
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void registerRenderInformation() {}
	
	@Override public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override public void registerItemRenders() {}
	
	@Override public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if (te != null && te instanceof TileEntityMagicChest) {
			TileEntityMagicChest icte = (TileEntityMagicChest) te;
			return new ContainerMagicChest(player.inventory, icte, 0, 0);
		} else {
			return null;
		}
	}
	
	@Override public World getClientWorld() {
		return null;
	}
}