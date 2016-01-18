package com.sixonethree.randomutilities.common.item;

import net.minecraft.item.ItemStack;

import com.sixonethree.randomutilities.reference.NBTTagKeys;

public interface IHeartCanister {
	default float getCurrentHealthStorage(ItemStack stack) { return ((ItemBase) stack.getItem()).tagOrDefault(stack, NBTTagKeys.CURRENT_HEALTH_STORED, 0F); };
	default float getMaxHealthStorage(ItemStack stack) { return ((ItemBase) stack.getItem()).tagOrDefault(stack, NBTTagKeys.MAX_HEALTH_STORED, this.isLarge(stack) ? 2000F : 200F); };
	
	default boolean isLarge(ItemStack stack) { return false; }
	default boolean isHeartCanisterAutomatic(ItemStack stack) { return false; }
}