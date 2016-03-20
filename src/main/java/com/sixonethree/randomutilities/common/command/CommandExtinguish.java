package com.sixonethree.randomutilities.common.command;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class CommandExtinguish extends ModCommandBase implements ICommand {
	@Override public List<String> getCommandAliases() {
		return Arrays.asList(new String[] {"ext"});
	}
	
	@Override public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			EntityPlayer player = getPlayer(server, sender, args[0]);
			player.extinguish();
			outputMessage(player, "extinguished", true, true);
			outputMessage(sender, "other", true, true, colorPlayer(player));
		} else {
			if (sender instanceof EntityPlayer) {
				((EntityPlayer) sender).extinguish();
				outputMessage(sender, "extinguished", true, true);
			}
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return true; }
	@Override public int getUsageType() { return 0; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean tabCompletesOnlinePlayers() { return true; }
}