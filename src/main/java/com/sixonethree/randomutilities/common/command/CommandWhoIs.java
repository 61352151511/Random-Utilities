package com.sixonethree.randomutilities.common.command;

import com.sixonethree.randomutilities.reference.CommandReference.AfkPlayers;
import com.sixonethree.randomutilities.reference.CommandReference.MutedPlayers;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class CommandWhoIs extends ModCommandBase implements ICommand {
	@Override public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			EntityPlayer player = getPlayer(server, sender, args[0]);
			outputMessage(sender, "statson", true, true, colorPlayer(player));
			outputMessage(sender, "afk", true, true, AfkPlayers.isAfk(player.getUniqueID()));
			outputMessage(sender, "muted", true, true, MutedPlayers.isMuted(player.getUniqueID()));
			outputMessage(sender, "location", true, true, doubleToInt(player.posX), doubleToInt(player.posY), doubleToInt(player.posZ));
			outputMessage(sender, "dimension", true, true, player.dimension);
		} else {
			outputMessage(sender, getUsage(sender), true, false);
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return true; }
	@Override public int getUsageType() { return 0; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean tabCompletesOnlinePlayers() { return true; }
}