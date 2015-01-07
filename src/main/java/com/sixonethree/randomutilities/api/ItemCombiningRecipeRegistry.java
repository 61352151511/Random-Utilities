package com.sixonethree.randomutilities.api;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ItemCombiningRecipeRegistry {
	public static ArrayList<ItemCombiningRecipe> combiningRecipes = new ArrayList<ItemCombiningRecipe>();
	
	public static void registerCombiningRecipe(ArrayList<ItemStack> Ingredients, ItemStack Result) {
		combiningRecipes.add(new ItemCombiningRecipe(Ingredients, Result));
	}
}