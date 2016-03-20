package com.sixonethree.randomutilities.common.command.warp;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import com.sixonethree.randomutilities.common.command.ModCommandBase;
import com.sixonethree.randomutilities.utility.homewarp.WarpPoint;

public class CommandDelWarp extends ModCommandBase implements ICommand {
	@Override public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
		return WarpPoint.getWarpsAsList(args[0]);
	}
	
	@Override public void executeCommandPlayer(MinecraftServer server, EntityPlayer player, String[] args) {
		if (args.length > 0) {
			String requestedWarp = args[0];
			if (WarpPoint.getWarp(requestedWarp) != null) {
				WarpPoint.delWarp(requestedWarp);
				outputMessage(player, "deletedwarp", true, true, requestedWarp);
			} else {
				outputMessage(player, "nowarp", true, true, requestedWarp);
			}
		} else {
			outputMessage(player, "statewhichwarp", true, true);
			ArrayList<String> homes = WarpPoint.getWarps(player);
			outputMessage(player, homes.get(0), true, false, homes.size() > 1 ? homes.get(1) : null);
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 0; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}