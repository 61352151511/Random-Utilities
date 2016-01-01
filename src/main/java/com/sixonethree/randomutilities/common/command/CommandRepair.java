package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class CommandRepair extends ModCommandBase implements ICommand {
	@Override public void processCommandPlayer(EntityPlayer player, String[] args) {
		ItemStack itemStack = player.getCurrentEquippedItem();
		if (itemStack != null) {
			if (itemStack.getItem().isDamageable()) {
				itemStack.setItemDamage(0);
			}
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 1; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}