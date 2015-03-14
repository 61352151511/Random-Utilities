package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;

import com.sixonethree.randomutilities.reference.CommandReference.AfkPlayers;

public class CommandAfk extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() { return 1; }

	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean TabCompletesOnlinePlayers() { return false; }
	
	@Override
	public void processCommandPlayer(EntityPlayer player, String[] args) {
		boolean Result = AfkPlayers.Toggle(player.getUniqueID());
		messageAll((Result ? "nowafk" : "notafk"), true, true, ColorPlayer(player));
	}
}