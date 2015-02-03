package com.sixonethree.randomutilities.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public class CommandKillPlayer extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() { return 0; }
	
	@Override public boolean canConsoleUseCommand() { return true; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean TabCompletesOnlinePlayers() { return true; }
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			getPlayer(sender, args[0]).attackEntityFrom(new DamageSourceCustom("pissedOffAdmin", getLocalBase() + "pissedoffadmin"), Float.MAX_VALUE);
		} else {
			outputUsage(sender, true);
		}
	}
}