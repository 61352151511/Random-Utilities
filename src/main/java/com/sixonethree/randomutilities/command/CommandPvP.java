package com.sixonethree.randomutilities.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public class CommandPvP extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() { return 0; }
	
	@Override public boolean canConsoleUseCommand() { return true; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean TabCompletesOnlinePlayers() { return false; }

	@Override
	public void execute(ICommandSender sender, String[] args) {
		if (args.length > 0) {
			ServerInstance.setAllowPvp(args[0].equalsIgnoreCase("on") ? true : false);
			messageAll((args[0].equalsIgnoreCase("on") ? "enabled" : "disabled"), true, true);
		} else {
			outputUsage(sender, true);
		}
	}
}