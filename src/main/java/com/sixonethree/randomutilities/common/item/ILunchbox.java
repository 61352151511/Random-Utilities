package com.sixonethree.randomutilities.common.item;

import net.minecraft.item.ItemStack;

import com.sixonethree.randomutilities.reference.NBTTagKeys;

public interface ILunchbox {
	default float getCurrentFoodStorage(ItemStack stack) { return ((ItemBase) stack.getItem()).tagOrDefault(stack, NBTTagKeys.CURRENT_FOOD_STORED, 0F); };
	default float getMaxFoodStorage(ItemStack stack) { return ((ItemBase) stack.getItem()).tagOrDefault(stack, NBTTagKeys.MAX_FOOD_STORED, 200F);};
	default int getColor(ItemStack stack) { return ((ItemBase) stack.getItem()).tagOrDefault(stack, NBTTagKeys.COLOR, 16); };
	default boolean hasColor(ItemStack stack) { return ((ItemBase) stack.getItem()).tagOrDefault(stack, NBTTagKeys.COLOR, -1) != 16; };
	default boolean isLunchboxAutomatic(ItemStack stack) { return false; }
	
	public void setCurrentFoodStorage(ItemStack stack, float storage);
}