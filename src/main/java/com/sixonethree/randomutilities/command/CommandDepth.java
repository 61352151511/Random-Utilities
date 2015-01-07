package com.sixonethree.randomutilities.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;

public class CommandDepth extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() { return 1; }

	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean TabCompletesOnlinePlayers() { return false; }
	
	@Override
	public void executeCommandPlayer(EntityPlayer player, String[] args) {
		Integer Depth = doubleToInt(player.posY) - 63;
		outputMessage(player, (Depth > 0 ? "abovesea" : (Depth < 0 ? "belowsea" : "sealevel")), true, true, (Depth > 0 ? Depth : Depth < 0 ? (-Depth) : null));
	}
}