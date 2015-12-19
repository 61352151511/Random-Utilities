package com.sixonethree.randomutilities.api;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ItemCombiningRecipe {
	private ArrayList<ItemStack> ingredients = new ArrayList<ItemStack>();
	private ItemStack result;
	private int size;
	
	public ItemCombiningRecipe(ArrayList<ItemStack> Ingredients, ItemStack Result) {
		this.ingredients = Ingredients;
		this.result = Result;
		this.size = Ingredients.size();
	}
	
	public ItemStack getIngredient(int IngredientNumber) {
		return ingredients.get(IngredientNumber);
	}
	
	public ArrayList<ItemStack> getIngredients() {
		return ingredients;
	}
	
	public ItemStack getResult() {
		return result;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean doesRequiredItemMatch(ItemStack compareStack) {
		if (compareStack != null) {
			for (int i = 0; i <= getSize() - 1; i ++) {
				if (getIngredient(i).isItemEqual(compareStack)) {
					return true;
				}
			}
		}
		return false;
	}
}