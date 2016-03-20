package com.sixonethree.randomutilities.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.common.container.ContainerDisplayTable;
import com.sixonethree.randomutilities.common.container.ContainerMagicChest;
import com.sixonethree.randomutilities.common.event.ChatEvents;
import com.sixonethree.randomutilities.common.event.DeathEvents;
import com.sixonethree.randomutilities.common.event.PlayerEvents;

public class ServerProxy implements IGuiHandler {
	public void preInit(FMLPreInitializationEvent event) {}
	
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		MinecraftForge.EVENT_BUS.register(new ChatEvents());
		MinecraftForge.EVENT_BUS.register(new DeathEvents());
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void postInit(FMLPostInitializationEvent event) {}
	
	@Override public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }
	public World getClientWorld() { return null; }
	
	@Override public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if (te == null) return null;
		if (ID == 0) return new ContainerMagicChest(player.inventory, (TileEntityMagicChest) te, 0, 0);
		if (ID == 1) return new ContainerDisplayTable(player.inventory, (TileEntityDisplayTable) te, 0, 0);
		return null;
	}
}