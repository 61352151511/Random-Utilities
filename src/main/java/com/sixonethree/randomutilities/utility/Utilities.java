package com.sixonethree.randomutilities.utility;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class Utilities {
	
	public static ArrayList<ItemStack> recipeHelper(ItemStack first, ArrayList<ItemStack> rest) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(first);
		for (ItemStack existingStack : rest) {
			ret.add(existingStack.copy());
		}
		return ret;
	}

	public static ArrayList<ItemStack> recipeHelper(ItemStack first, ItemStack second) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(first);
		ret.add(second);
		return ret;
	}
}