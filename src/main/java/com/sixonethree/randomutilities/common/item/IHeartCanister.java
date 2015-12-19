package com.sixonethree.randomutilities.common.item;

import net.minecraft.item.ItemStack;

public interface IHeartCanister {
	public float getCurrentHealthStorage(ItemStack stack);
	
	public float getMaxHealthStorage(ItemStack stack);
}