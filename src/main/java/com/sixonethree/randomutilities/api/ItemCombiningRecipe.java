package com.sixonethree.randomutilities.api;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ItemCombiningRecipe {
	private ArrayList<ItemStack> Ingredients = new ArrayList<ItemStack>();
	private ItemStack Result;
	private int Size;
	
	public ItemCombiningRecipe(ArrayList<ItemStack> Ingredients, ItemStack Result) {
		this.Ingredients = Ingredients;
		this.Result = Result;
		this.Size = Ingredients.size();
	}
	
	public ItemStack getIngredient(int IngredientNumber) {
		return Ingredients.get(IngredientNumber);
	}
	
	public ArrayList<ItemStack> getIngredients() {
		return Ingredients;
	}
	
	public ItemStack getResult() {
		return Result;
	}
	
	public int getSize() {
		return Size;
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