package com.sixonethree.randomutilities.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

import com.sixonethree.randomutilities.reference.CommandReference.MutedPlayers;

public class CommandMute extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() { return 0; }
	
	@Override public boolean canConsoleUseCommand() { return true; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean TabCompletesOnlinePlayers() { return true; }

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length > 0) {
			EntityPlayer RequestedPlayer = getPlayer(sender, args[0]);
			boolean Result = MutedPlayers.Toggle(RequestedPlayer.getUniqueID());
			messageAll((Result ? "mutedby" : "unmutedby"), true, true, ColorPlayer(RequestedPlayer), ColorPlayer(sender));
		} else {
			outputMessage(sender, "togglemute", true, true);
		}
	}
}