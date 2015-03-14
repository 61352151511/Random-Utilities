package com.sixonethree.randomutilities.proxy;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public interface IProxy extends IGuiHandler {
	public abstract void init();
	public abstract World getClientWorld();
	public abstract void registerRenderInformation();
	public abstract void registerItemRenders();
}