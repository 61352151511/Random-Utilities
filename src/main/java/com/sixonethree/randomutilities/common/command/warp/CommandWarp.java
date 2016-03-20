package com.sixonethree.randomutilities.common.command.warp;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import com.sixonethree.randomutilities.common.command.ModCommandBase;
import com.sixonethree.randomutilities.reference.CommandReference.LastLocations;
import com.sixonethree.randomutilities.utility.homewarp.Location;
import com.sixonethree.randomutilities.utility.homewarp.WarpPoint;

public class CommandWarp extends ModCommandBase implements ICommand {
	@Override public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
		return WarpPoint.getWarpsAsList(args[0]);
	}
	
	@Override public void executeCommandPlayer(MinecraftServer server, EntityPlayer player, String[] args) {
		if (args.length > 0) {
			String requestedWarp = args[0];
			if (WarpPoint.getWarp(requestedWarp) != null) {
				Location loc = WarpPoint.getWarp(requestedWarp).location;
				LastLocations.set((EntityPlayerMP) player, new Location((EntityPlayerMP) player));
				if (loc.dimension != player.dimension) {
					transferDimension((EntityPlayerMP) player, loc);
				} else {
					player.setPositionAndUpdate(loc.posX, loc.posY, loc.posZ);
					player.fallDistance = 0F;
				}
			} else {
				outputMessage(player, noWarpCalled, true, false, requestedWarp);
			}
		} else {
			outputMessage(player, "statewhichwarp", true, true);
			ArrayList<String> warps = WarpPoint.getWarps(player);
			outputMessage(player, warps.get(0), true, false, warps.size() > 1 ? warps.get(1) : null);
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 0; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}