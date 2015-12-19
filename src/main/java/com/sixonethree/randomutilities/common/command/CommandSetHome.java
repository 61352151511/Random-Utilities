package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.sixonethree.randomutilities.utility.HomePoint;

public class CommandSetHome extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() {
		return 0;
	}
	
	@Override public boolean canConsoleUseCommand() {
		return false;
	}
	
	@Override public boolean isOpOnly() {
		return false;
	}
	
	@Override public boolean tabCompletesOnlinePlayers() {
		return false;
	}
	
	@Override public void processCommandPlayer(EntityPlayer player, String[] args) {
		if (args.length > 0) {
			String HomeName = args[0];
			HomePoint.setHome((EntityPlayerMP) player, HomeName);
			outputMessage(player, "homeset", true, true, HomeName);
		} else {
			outputUsage(player, true);
		}
	}
}