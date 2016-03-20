package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import com.sixonethree.randomutilities.reference.CommandReference.LastLocations;
import com.sixonethree.randomutilities.utility.homewarp.Location;

public class CommandBack extends ModCommandBase implements ICommand {
	@Override public void executeCommandPlayer(MinecraftServer server, EntityPlayer player, String[] args) {
		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		if (LastLocations.get(playerMP) != null) {
			Location loc = LastLocations.get(playerMP);
			LastLocations.set(playerMP, new Location(playerMP));
			if (loc.dimension != player.dimension) {
				transferDimension(playerMP, loc);
			} else {
				player.setPositionAndUpdate(loc.posX, loc.posY, loc.posZ);
			}
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 1; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}