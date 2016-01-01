package com.sixonethree.randomutilities.common.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

import com.sixonethree.randomutilities.utility.HomePoint;

public class CommandDelHome extends ModCommandBase implements ICommand {
	@Override public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return HomePoint.getPlayerHomesAsList((EntityPlayer) sender, args[0]);
	}
	
	@Override public void processCommandPlayer(EntityPlayer player, String[] args) {
		if (args.length > 0) {
			String RequestedHome = args[0];
			if (HomePoint.getHome(player.getUniqueID().toString() + RequestedHome) != null) {
				HomePoint.delHome(player.getUniqueID().toString() + RequestedHome);
				outputMessage(player, "deletedhome", true, true, RequestedHome);
			} else {
				outputMessage(player, "nohome", true, true, RequestedHome);
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