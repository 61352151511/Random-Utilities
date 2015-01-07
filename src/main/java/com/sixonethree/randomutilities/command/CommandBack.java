package com.sixonethree.randomutilities.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.sixonethree.randomutilities.reference.CommandReference.LastLocations;
import com.sixonethree.randomutilities.utility.Location;

public class CommandBack extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() { return 1; }
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean TabCompletesOnlinePlayers() { return false; }

	@Override
	public void executeCommandPlayer(EntityPlayer player, String[] args) {
		EntityPlayerMP playermp = (EntityPlayerMP) player;
		if (LastLocations.Get(playermp) != null) {
			Location loc = LastLocations.Get(playermp);
			LastLocations.Set(playermp, new Location(playermp));
			if (loc.dimension != player.dimension) {
				TransferDimension(playermp, loc);
			} else {
				player.setPositionAndUpdate(loc.posX, loc.posY, loc.posZ);
			}
		}
	}
}