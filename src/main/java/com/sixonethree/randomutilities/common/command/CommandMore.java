package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class CommandMore extends ModCommandBase implements ICommand {
	@Override public void executeCommandPlayer(MinecraftServer server, EntityPlayer player, String[] args) {
		if (!player.getHeldItemMainhand().isEmpty()) {
			player.getHeldItemMainhand().setCount(player.getHeldItemMainhand().getMaxStackSize());
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 1; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}