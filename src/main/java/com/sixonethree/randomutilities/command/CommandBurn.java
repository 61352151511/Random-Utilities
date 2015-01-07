package com.sixonethree.randomutilities.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandBurn extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() { return 0; }

	@Override public boolean canConsoleUseCommand() { return true; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean TabCompletesOnlinePlayers() { return true; }
	
	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 1) {
			EntityPlayer player = getPlayer(sender, args[0]);
			Integer burnTime = parseInt(args[1]);
			player.setFire(burnTime);
			outputMessage(player, "ouchhot", true, true);
			outputMessage(sender, "nowburning", true, true, ColorPlayer(player), burnTime);
		} else {
			outputUsage(sender, true);
		}
	}
}