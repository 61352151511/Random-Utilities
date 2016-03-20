package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import com.sixonethree.randomutilities.reference.CommandReference.TeleportRequests;

public class CommandTpa extends ModCommandBase implements ICommand {
	@Override public void executeCommandPlayer(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {
		if (args.length > 0) {
			EntityPlayer requestedPlayer = getPlayer(server, player, args[0]);
			if (requestedPlayer.equals(player)) {
				outputMessage(player, "notyourself", true, true);
			} else {
				TeleportRequests.add(requestedPlayer.getUniqueID(), player.getUniqueID());
				outputMessage(requestedPlayer, "incomingrequest", true, true, colorPlayer(player));
				outputMessage(requestedPlayer, "options", true, true);
				outputMessage(player, "outgoingrequest", true, true, colorPlayer(requestedPlayer));
			}
		} else {
			outputMessage(player, "statewhichplayer", true, true);
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 0; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean tabCompletesOnlinePlayers() { return true; }
}