package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;

import com.sixonethree.randomutilities.reference.CommandReference.AfkPlayers;

public class CommandAfk extends ModCommandBase implements ICommand {
	@Override public void processCommandPlayer(EntityPlayer player, String[] args) {
		boolean result = AfkPlayers.toggle(player.getUniqueID());
		messageAll((result ? "nowafk" : "notafk"), true, true, colorPlayer(player));
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 1; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}