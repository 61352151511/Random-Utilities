package com.sixonethree.randomutilities.api;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemCombiningRecipeRegistry {
	public static ArrayList<ItemCombiningRecipe> combiningRecipes = new ArrayList<>();
	
	public static void registerCombiningRecipe(NonNullList<ItemStack> ingredients, @Nonnull ItemStack result) {
		combiningRecipes.add(new ItemCombiningRecipe(ingredients, result));
	}
}