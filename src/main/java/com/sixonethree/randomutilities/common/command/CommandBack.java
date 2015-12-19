package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.sixonethree.randomutilities.reference.CommandReference.LastLocations;
import com.sixonethree.randomutilities.utility.Location;

public class CommandBack extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() {
		return 1;
	}
	
	@Override public boolean canConsoleUseCommand() {
		return false;
	}
	
	@Override public boolean isOpOnly() {
		return false;
	}
	
	@Override public boolean tabCompletesOnlinePlayers() {
		return false;
	}
	
	@Override public void processCommandPlayer(EntityPlayer player, String[] args) {
		EntityPlayerMP playermp = (EntityPlayerMP) player;
		if (LastLocations.get(playermp) != null) {
			Location loc = LastLocations.get(playermp);
			LastLocations.set(playermp, new Location(playermp));
			if (loc.dimension != player.dimension) {
				transferDimension(playermp, loc);
			} else {
				player.setPositionAndUpdate(loc.posX, loc.posY, loc.posZ);
			}
		}
	}
}