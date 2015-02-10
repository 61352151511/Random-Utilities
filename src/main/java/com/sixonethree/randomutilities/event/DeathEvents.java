package com.sixonethree.randomutilities.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import com.sixonethree.randomutilities.reference.CommandReference.LastLocations;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DeathEvents {
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event) {
		Entity Dead = event.entity;
		if (Dead instanceof EntityPlayer) {
			LastLocations.Set((EntityPlayerMP) Dead, Dead.posX, Dead.posY, Dead.posZ, Dead.dimension);
		}
	}
}