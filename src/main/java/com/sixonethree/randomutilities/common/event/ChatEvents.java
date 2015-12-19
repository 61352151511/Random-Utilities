package com.sixonethree.randomutilities.common.event;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sixonethree.randomutilities.reference.CommandReference.MutedPlayers;

public class ChatEvents {
	@SubscribeEvent public void onPlayerChat(ServerChatEvent event) {
		EntityPlayerMP mrchattypants = event.player;
		if (MutedPlayers.isMuted(mrchattypants.getUniqueID())) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent public void onPlayerCommand(CommandEvent event) {
		ICommandSender sender = event.sender;
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;
			if (MutedPlayers.isMuted(player.getUniqueID())) {
				if (event.command.getCommandName().equalsIgnoreCase("tell")) {
					event.setCanceled(true);
				}
			}
		}
	}
}