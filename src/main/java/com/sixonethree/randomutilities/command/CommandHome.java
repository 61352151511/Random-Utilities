package com.sixonethree.randomutilities.command;

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
	
	@Override public int getUsageType() { return 0; }
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean TabCompletesOnlinePlayers() { return false; }

	@Override
	public void processCommandPlayer(EntityPlayer player, String[] args) {
		EntityPlayerMP playermp = (EntityPlayerMP) player;
		UUID puid = player.getUniqueID();
		if (args.length > 0) {
			String RequestedHome = args[0];
			if (HomePoint.getHome(puid + RequestedHome) != null) {
				Location loc = HomePoint.getHome(puid + RequestedHome).location;
				LastLocations.Set(playermp, new Location(playermp));
				if (loc.dimension != player.dimension) {
					TransferDimension(playermp, loc);
				} else {
					if (Math.floor(Math.sqrt(player.getDistanceSq(loc.x, loc.y, loc.z))) > 1000) {
					}
					player.setPositionAndUpdate(loc.posX, loc.posY, loc.posZ);
					player.fallDistance = 0F;
				}
			} else {
				outputMessage(player, NoHomeCalled, true, false, RequestedHome);
			}
		} else {
			outputMessage(player, "statewhichhome", true, true);
			ArrayList<String> homes = HomePoint.getPlayerHomes(player);
			outputMessage(player, homes.get(0), true, false, homes.size() > 1 ? homes.get(1) : null);
		}
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return HomePoint.getPlayerHomesAsList((EntityPlayer) sender, args[0]);
	}
}