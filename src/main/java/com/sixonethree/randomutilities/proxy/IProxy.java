package com.sixonethree.randomutilities.proxy;

import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public interface IProxy extends IGuiHandler {
	public abstract void init();
	public abstract World getClientWorld();
	public abstract void registerRenderInformation();
}