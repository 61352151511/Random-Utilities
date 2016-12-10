package com.sixonethree.randomutilities.common.event;

import com.sixonethree.randomutilities.reference.CommandReference.MutedPlayers;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatEvents {
	@SubscribeEvent public void onPlayerChat(ServerChatEvent event) {
		EntityPlayerMP mrchattypants = event.getPlayer();
		if (MutedPlayers.isMuted(mrchattypants.getUniqueID())) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent public void onPlayerCommand(CommandEvent event) {
		ICommandSender sender = event.getSender();
		if (sender instanceof EntityPlayer) {
			EntityPlayer mrchattypants = (EntityPlayer) sender;
			if (MutedPlayers.isMuted(mrchattypants.getUniqueID())) {
				if (event.getCommand().getName().equalsIgnoreCase("tell")) {
					event.setCanceled(true);
				}
			}
		}
	}
}