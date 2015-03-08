package com.sixonethree.randomutilities.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.sixonethree.randomutilities.block.ContainerMagicChest;
import com.sixonethree.randomutilities.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.event.ChatEvents;
import com.sixonethree.randomutilities.event.DeathEvents;
import com.sixonethree.randomutilities.event.PlayerEvents;

import cpw.mods.fml.common.network.IGuiHandler;

public abstract class CommonProxy implements IProxy, IGuiHandler {
	public void init() {
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		MinecraftForge.EVENT_BUS.register(new ChatEvents());
		MinecraftForge.EVENT_BUS.register(new DeathEvents());
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void registerRenderInformation() {}
	
	@Override public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override public Object getServerGuiElement(int ID, EntityPlayer player, World world, int X, int Y, int Z) {
		TileEntity te = world.getTileEntity(X, Y, Z);
		if (te != null && te instanceof TileEntityMagicChest) {
			TileEntityMagicChest icte = (TileEntityMagicChest) te;
			return new ContainerMagicChest(player.inventory, icte, 0, 0);
		} else {
			return null;
		}
	}
	
	public World getClientWorld() {
		return null;
	}
}