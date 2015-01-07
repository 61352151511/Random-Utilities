package com.sixonethree.randomutilities.utility;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class Utilities {
	public static boolean areItemStacksEqualIgnoreNBT(ItemStack is1, ItemStack is2) {
		if (is1 != null && is2 != null) {
			if (is1.getItem() == is2.getItem()) { return true; }
		}
		return false;
	}
	
	public static boolean areItemStacksEqualIgnoreNBTUseMeta(ItemStack is1, ItemStack is2) {
		if (is1 != null && is2 != null) {
			if (is1.getItem() == is2.getItem()) {
				if (is1.getItemDamage() == is2.getItemDamage()) { return true; }
			}
		}
		return false;
	}
	
	public static String Translate(String string) {
		return StatCollector.translateToLocal(string);
	}
	
	public static String TranslateFormatted(String string, Object... formatargs) {
		return StatCollector.translateToLocalFormatted(string, formatargs);
	}
	
	public static ArrayList<ItemStack> RecipeHelper(ItemStack First, ArrayList<ItemStack> Rest) {
		@SuppressWarnings("unchecked") ArrayList<ItemStack> Ret = (ArrayList<ItemStack>) Rest.clone();
		Ret.add(0, First);
		return Ret;
	}

	public static ArrayList<ItemStack> RecipeHelper(ItemStack First, ItemStack Second) {
		ArrayList<ItemStack> Ret = new ArrayList<ItemStack>();
		Ret.add(First);
		Ret.add(Second);
		return Ret;
	}
}