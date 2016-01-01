package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;

public class CommandSuicide extends ModCommandBase implements ICommand {
	@Override public void processCommandPlayer(EntityPlayer player, String[] args) {
		player.attackEntityFrom(new DamageSourceCustom("suicide", "command.suicide.bidfarewell"), Float.MAX_VALUE);
	}

	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 1; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}