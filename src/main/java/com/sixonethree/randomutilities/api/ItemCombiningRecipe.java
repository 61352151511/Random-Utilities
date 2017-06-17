package com.sixonethree.randomutilities.api;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemCombiningRecipe {
	private NonNullList<ItemStack> ingredients = NonNullList.create();
	private ItemStack result = ItemStack.EMPTY;
	
	public ItemCombiningRecipe(NonNullList<ItemStack> ingredients, @Nonnull ItemStack result) {
		this.ingredients = ingredients;
		this.result = result;
	}
	
	public boolean doesRequiredItemMatch(@Nonnull ItemStack compareStack) {
		if (compareStack.isEmpty()) return false;
		for (int i = 0; i <= this.getSize() - 1; i ++) {
			if (this.getIngredient(i).isItemEqual(compareStack)) return true;
		}
		return false;
	}
	
	public ItemStack getIngredient(int position) {
		return this.ingredients.get(position);
	}
	
	public NonNullList<ItemStack> getIngredients() {
		return this.ingredients;
	}
	
	public ItemStack getResult() {
		return this.result;
	}
	
	public int getSize() {
		return this.ingredients.size();
	}
}