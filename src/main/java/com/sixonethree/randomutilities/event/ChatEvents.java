package com.sixonethree.randomutilities.event;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;

import com.sixonethree.randomutilities.reference.CommandReference.MutedPlayers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ChatEvents {
	@SubscribeEvent
	public void onPlayerChat(ServerChatEvent event) {
		EntityPlayerMP mrchattypants = event.player;
		if (MutedPlayers.isMuted(mrchattypants.getUniqueID())) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onPlayerCommand(CommandEvent event) {
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