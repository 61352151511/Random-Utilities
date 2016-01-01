package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandHeal extends ModCommandBase implements ICommand {
	@Override public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			EntityPlayer player = getPlayer(sender, args[0]);
			player.setHealth(player.getMaxHealth());
			outputMessage(player, "healed", true, true);
			outputMessage(sender, "healedplayer", true, true, colorPlayer(player));
		} else {
			if (sender instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) sender;
				player.setHealth(player.getMaxHealth());
				outputMessage(player, "healed", true, true);
			}
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return true; }
	@Override public int getUsageType() { return 0; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean tabCompletesOnlinePlayers() { return true; }
}