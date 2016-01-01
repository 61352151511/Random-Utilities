package com.sixonethree.randomutilities.common.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;

import com.sixonethree.randomutilities.reference.CommandReference.LastLocations;
import com.sixonethree.randomutilities.utility.HomePoint;
import com.sixonethree.randomutilities.utility.Location;

public class CommandHome extends ModCommandBase implements ICommand {
	@Override public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return HomePoint.getPlayerHomesAsList((EntityPlayer) sender, args[0]);
	}
	
	@Override public void processCommandPlayer(EntityPlayer player, String[] args) {
		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		UUID playerUUID = player.getUniqueID();
		if (args.length > 0) {
			String requestedHome = args[0];
			if (HomePoint.getHome(playerUUID + requestedHome) != null) {
				Location loc = HomePoint.getHome(playerUUID + requestedHome).location;
				LastLocations.set(playerMP, new Location(playerMP));
				if (loc.dimension != player.dimension) {
					transferDimension(playerMP, loc);
				} else {
					player.setPositionAndUpdate(loc.posX, loc.posY, loc.posZ);
					player.fallDistance = 0F;
				}
			} else {
				outputMessage(player, noHomeCalled, true, false, requestedHome);
			}
		} else {
			outputMessage(player, "statewhichhome", true, true);
			ArrayList<String> homes = HomePoint.getPlayerHomes(player);
			outputMessage(player, homes.get(0), true, false, homes.size() > 1 ? homes.get(1) : null);
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 0; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
	
	
}