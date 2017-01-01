package com.sixonethree.randomutilities.common.command;

import com.sixonethree.randomutilities.reference.CommandReference.LastLocations;
import com.sixonethree.randomutilities.utility.homewarp.Location;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandBack extends ModCommandBase implements ICommand {
	@Override public void executeCommandPlayer(MinecraftServer server, EntityPlayer player, String[] args) {
		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		if (LastLocations.get(playerMP) != null) {
			Location loc = LastLocations.get(playerMP);
			teleportPlayer(playerMP, loc);
		}
	}
	
	@Override public boolean canConsoleUseCommand() {
		return false;
	}
	
	@Override public int getUsageType() {
		return 1;
	}
	
	@Override public boolean isOpOnly() {
		return false;
	}
	
	@Override public boolean tabCompletesOnlinePlayers() {
		return false;
	}
}