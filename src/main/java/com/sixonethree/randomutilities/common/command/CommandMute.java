package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

import com.sixonethree.randomutilities.reference.CommandReference.MutedPlayers;

public class CommandMute extends ModCommandBase implements ICommand {
	@Override public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			EntityPlayer requestedPlayer = getPlayer(sender, args[0]);
			boolean result = MutedPlayers.toggle(requestedPlayer.getUniqueID());
			messageAll((result ? "mutedby" : "unmutedby"), true, true, colorPlayer(requestedPlayer), colorPlayer(sender));
		} else {
			outputMessage(sender, "togglemute", true, true);
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return true; }
	@Override public int getUsageType() { return 0; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean tabCompletesOnlinePlayers() { return true; }
}