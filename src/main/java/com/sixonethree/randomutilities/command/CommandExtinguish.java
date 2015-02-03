package com.sixonethree.randomutilities.command;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandExtinguish extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() { return 0; }

	@Override public boolean canConsoleUseCommand() { return true; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean TabCompletesOnlinePlayers() { return true; }
	
	@SuppressWarnings({"rawtypes"})
	@Override public List getAliases() { return Arrays.asList(new String[] {"ext"}); }
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			EntityPlayer player = getPlayer(sender, args[0]);
			player.extinguish();
			outputMessage(player, "extinguished", true, true);
			outputMessage(sender, "other", true, true, ColorPlayer(player));
		} else {
			if (sender instanceof EntityPlayer) {
				((EntityPlayer) sender).extinguish();
				outputMessage(sender, "extinguished", true, true);
			}
		}
	}
}