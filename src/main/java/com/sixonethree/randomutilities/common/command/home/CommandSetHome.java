package com.sixonethree.randomutilities.common.command.home;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.sixonethree.randomutilities.common.command.ModCommandBase;
import com.sixonethree.randomutilities.utility.homewarp.HomePoint;

public class CommandSetHome extends ModCommandBase implements ICommand {
	@Override public void processCommandPlayer(EntityPlayer player, String[] args) {
		if (args.length > 0) {
			String homeName = args[0];
			HomePoint.setHome((EntityPlayerMP) player, homeName);
			outputMessage(player, "homeset", true, true, homeName);
		} else {
			outputUsage(player, true);
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 0; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}