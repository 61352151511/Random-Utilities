package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandPvP extends ModCommandBase implements ICommand {
	@Override public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
		if (args.length > 0) {
			serverInstance.setAllowPvp(args[0].equalsIgnoreCase("on") ? true : false);
			messageAll((args[0].equalsIgnoreCase("on") ? "enabled" : "disabled"), true, true);
		} else {
			outputUsage(sender, true);
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return true; }
	@Override public int getUsageType() { return 0; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}