package com.sixonethree.randomutilities.api;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ItemCombiningRecipe {
	private ArrayList<ItemStack> ingredients = new ArrayList<ItemStack>();
	private ItemStack result;
	private int size;
	
	public ItemCombiningRecipe(ArrayList<ItemStack> ingredients, ItemStack result) {
		this.ingredients = ingredients;
		this.result = result;
		this.size = ingredients.size();
	}
	
	public boolean doesRequiredItemMatch(ItemStack compareStack) {
		if (compareStack != null) {
			for (int i = 0; i <= this.getSize() - 1; i ++) {
				if (this.getIngredient(i).isItemEqual(compareStack)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public ItemStack getIngredient(int position) { return this.ingredients.get(position); }
	public ArrayList<ItemStack> getIngredients() { return this.ingredients; }
	public ItemStack getResult() { return this.result; }
	public int getSize() { return this.size; }
}