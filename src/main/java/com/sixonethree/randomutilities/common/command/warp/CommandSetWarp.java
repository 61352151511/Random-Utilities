package com.sixonethree.randomutilities.common.command.warp;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import com.sixonethree.randomutilities.common.command.ModCommandBase;
import com.sixonethree.randomutilities.utility.homewarp.WarpPoint;

public class CommandSetWarp extends ModCommandBase implements ICommand {
	@Override public void executeCommandPlayer(MinecraftServer server, EntityPlayer player, String[] args) {
		if (args.length > 0) {
			String warpName = args[0];
			WarpPoint.setWarp((EntityPlayerMP) player, warpName);
			outputMessage(player, "warpset", true, true, warpName);
		} else {
			outputUsage(player, true);
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 0; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}