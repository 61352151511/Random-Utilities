package com.sixonethree.randomutilities.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;

public class CommandMore extends ModCommandBase implements ICommand {

	@Override public int getUsageType() { return 1; }

	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean TabCompletesOnlinePlayers() { return false; }
	
	@Override
	public void processCommandPlayer(EntityPlayer player, String[] args) {
		if (player.getCurrentEquippedItem() != null) {
			player.getCurrentEquippedItem().stackSize = player.getCurrentEquippedItem().getMaxStackSize();
		}
	}
}