package com.sixonethree.randomutilities.utility;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class Utilities {
	public static String translateFormatted(String string, Object... formatargs) {
		return StatCollector.translateToLocalFormatted(string, formatargs);
	}
	
	public static ArrayList<ItemStack> recipeHelper(ItemStack first, ArrayList<ItemStack> rest) {
		@SuppressWarnings("unchecked") ArrayList<ItemStack> ret = (ArrayList<ItemStack>) rest.clone();
		ret.add(0, first);
		return ret;
	}

	public static ArrayList<ItemStack> recipeHelper(ItemStack first, ItemStack second) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(first);
		ret.add(second);
		return ret;
	}
}