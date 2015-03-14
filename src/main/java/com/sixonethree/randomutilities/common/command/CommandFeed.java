package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandFeed extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() { return 0; }
	
	@Override public boolean TabCompletesOnlinePlayers() { return true; };
	@Override public boolean canConsoleUseCommand() { return true; }
	@Override public boolean isOpOnly() { return true; }

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			EntityPlayer RequestedPlayer = getPlayer(sender, args[0]);
			RequestedPlayer.getFoodStats().addStats(20, 1);
			outputMessage(sender, "otherfed", true, true, ColorPlayer(RequestedPlayer));
			outputMessage(RequestedPlayer, "fed", true, true);
		} else {
			if (sender instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) sender;
				player.getFoodStats().addStats(20, 1);
				outputMessage(player, "fed", true, true);
			}
		}
	}
}