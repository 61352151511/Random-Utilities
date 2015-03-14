package com.sixonethree.randomutilities.common.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sixonethree.randomutilities.reference.CommandReference.LastLocations;

public class DeathEvents {
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event) {
		Entity Dead = event.entity;
		if (Dead instanceof EntityPlayer) {
			LastLocations.Set((EntityPlayerMP) Dead, Dead.posX, Dead.posY, Dead.posZ, Dead.dimension);
		}
	}
}