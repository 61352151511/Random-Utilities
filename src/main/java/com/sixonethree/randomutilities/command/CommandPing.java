package com.sixonethree.randomutilities.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandPing extends ModCommandBase implements ICommand {

	@Override public int getUsageType() { return 1; }

	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean TabCompletesOnlinePlayers() { return false; }
	
	@Override
	public void executeCommandPlayer(EntityPlayer player, String[] args) {
		outputMessage(player, String.valueOf(((EntityPlayerMP) player).ping), false, false);
	}
}