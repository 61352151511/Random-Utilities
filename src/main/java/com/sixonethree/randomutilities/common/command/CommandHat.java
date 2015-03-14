package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class CommandHat extends ModCommandBase implements ICommand {

	@Override public int getUsageType() { return 1; }
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean TabCompletesOnlinePlayers() { return false; }
	
	@Override
	public void processCommandPlayer(EntityPlayer player, String[] args) {
		ItemStack is = player.getCurrentArmor(3);
		ItemStack hat = player.getCurrentEquippedItem();
		ItemStack oldhat = null;
		if (is != null) {
			oldhat = is;
		}
		player.setCurrentItemOrArmor(0, oldhat);
		player.setCurrentItemOrArmor(4, hat);
	}
}