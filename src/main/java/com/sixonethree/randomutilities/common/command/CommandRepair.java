package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class CommandRepair extends ModCommandBase implements ICommand {
	@Override public void executeCommandPlayer(MinecraftServer server, EntityPlayer player, String[] args) {
		ItemStack itemStack = player.getHeldItemMainhand();
		if (!itemStack.isEmpty()) {
			if (itemStack.getItem().isDamageable()) {
				itemStack.setItemDamage(0);
			}
		}
		itemStack = player.getHeldItemOffhand();
		if (!itemStack.isEmpty()) {
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