package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;

import com.sixonethree.randomutilities.reference.CommandReference.TeleportRequests;

public class CommandTpa extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() {
		return 0;
	}
	
	@Override public boolean canConsoleUseCommand() {
		return false;
	}
	
	@Override public boolean isOpOnly() {
		return false;
	}
	
	@Override public boolean tabCompletesOnlinePlayers() {
		return true;
	}
	
	@Override public void processCommandPlayer(EntityPlayer player, String[] args) throws CommandException {
		if (args.length > 0) {
			EntityPlayer RequestedPlayer = getPlayer(player, args[0]);
			if (RequestedPlayer.equals(player)) {
				outputMessage(player, "notyourself", true, true);
			} else {
				TeleportRequests.add(RequestedPlayer.getUniqueID(), player.getUniqueID());
				outputMessage(RequestedPlayer, "incomingrequest", true, true, colorPlayer(player));
				outputMessage(RequestedPlayer, "options", true, true);
				outputMessage(player, "outgoingrequest", true, true, colorPlayer(RequestedPlayer));
			}
		} else {
			outputMessage(player, "statewhichplayer", true, true);
		}
	}
}