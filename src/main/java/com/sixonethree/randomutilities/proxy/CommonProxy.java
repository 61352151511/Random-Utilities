package com.sixonethree.randomutilities.proxy;

import net.minecraftforge.common.MinecraftForge;

import com.sixonethree.randomutilities.event.PlayerEvents;

public abstract class CommonProxy implements IProxy {
	public void init() {
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		MinecraftForge.EVENT_BUS.register(this);
	}
}