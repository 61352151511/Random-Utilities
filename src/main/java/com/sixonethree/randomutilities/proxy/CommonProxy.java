package com.sixonethree.randomutilities.proxy;

import com.sixonethree.randomutilities.event.PlayerEvents;
import com.sixonethree.randomutilities.event.TooltipEvents;

import net.minecraftforge.common.MinecraftForge;

public abstract class CommonProxy implements IProxy {
	public void init() {
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		MinecraftForge.EVENT_BUS.register(new TooltipEvents());
		MinecraftForge.EVENT_BUS.register(this);
	}
}