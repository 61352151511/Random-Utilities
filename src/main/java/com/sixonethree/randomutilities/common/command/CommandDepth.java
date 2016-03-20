package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class CommandDepth extends ModCommandBase implements ICommand {
	@Override public void executeCommandPlayer(MinecraftServer server, EntityPlayer player, String[] args) {
		Integer depth = doubleToInt(player.posY) - 63;
		outputMessage(player, (depth > 0 ? "abovesea" : (depth < 0 ? "belowsea" : "sealevel")), true, true, (depth > 0 ? depth : depth < 0 ? (-depth) : null));
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 1; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}