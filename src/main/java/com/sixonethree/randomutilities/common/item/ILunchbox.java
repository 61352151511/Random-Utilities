package com.sixonethree.randomutilities.common.item;

import net.minecraft.item.ItemStack;

public interface ILunchbox {
	public float getCurrentFoodStorage(ItemStack stack);
	public float getMaxFoodStorage(ItemStack stack);
	public int getColor(ItemStack stack);
	public boolean hasColor(ItemStack stack);
}