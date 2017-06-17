package com.sixonethree.randomutilities.utility;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class Utilities {
	
	public static NonNullList<ItemStack> recipeHelper(ItemStack first, ArrayList<ItemStack> rest) {
		NonNullList<ItemStack> ret = NonNullList.<ItemStack> create();
		ret.add(first);
		for (ItemStack existingStack : rest) {
			ret.add(existingStack.copy());
		}
		return ret;
	}
	
	public static NonNullList<ItemStack> recipeHelper(ItemStack first, ItemStack second) {
		NonNullList<ItemStack> ret = NonNullList.<ItemStack> create();
		ret.add(first);
		ret.add(second);
		return ret;
	}
}