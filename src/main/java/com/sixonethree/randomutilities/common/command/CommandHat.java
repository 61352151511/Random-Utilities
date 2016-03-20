package com.sixonethree.randomutilities.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class CommandHat extends ModCommandBase implements ICommand {
	@Override public void executeCommandPlayer(MinecraftServer server, EntityPlayer player, String[] args) {
		ItemStack itemStack = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		ItemStack hat = player.getHeldItemMainhand();
		ItemStack oldhat = null;
		if (itemStack != null) {
			oldhat = itemStack;
		}
		player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, oldhat);
		player.setItemStackToSlot(EntityEquipmentSlot.HEAD, hat);
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 1; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}