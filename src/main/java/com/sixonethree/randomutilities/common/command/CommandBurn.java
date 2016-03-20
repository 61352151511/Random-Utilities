package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class CommandBurn extends ModCommandBase implements ICommand {
	@Override public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 1) {
			EntityPlayer player = getPlayer(server, sender, args[0]);
			Integer burnTime = parseInt(args[1]);
			player.setFire(burnTime);
			outputMessage(player, "ouchhot", true, true);
			outputMessage(sender, "nowburning", true, true, colorPlayer(player), burnTime);
		} else {
			outputUsage(sender, true);
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return true; }
	@Override public int getUsageType() { return 0; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean tabCompletesOnlinePlayers() { return true; }
}