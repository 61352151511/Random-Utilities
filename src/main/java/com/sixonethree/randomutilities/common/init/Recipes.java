package com.sixonethree.randomutilities.common.init;

import java.util.ArrayList;

import com.sixonethree.randomutilities.api.ItemCombiningRecipeRegistry;
import com.sixonethree.randomutilities.reference.NBTTagKeys;
import com.sixonethree.randomutilities.utility.Utilities;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Recipes {
	public static void init() {
		ArrayList<ItemStack> UpgradeComponents = new ArrayList<ItemStack>() {
			private static final long serialVersionUID = -4349640254907860210L;
			{
				add(new ItemStack(Items.NETHER_STAR));
				add(new ItemStack(Items.BUCKET));
			}
		};
		
		ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModRegistry.heartCanister, 1, 0), UpgradeComponents), new ItemStack(ModRegistry.heartCanister, 1, 2));
		ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModRegistry.heartCanister, 1, 1), UpgradeComponents), new ItemStack(ModRegistry.heartCanister, 1, 3));
		ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModRegistry.lunchbox, 1, 0), UpgradeComponents), new ItemStack(ModRegistry.lunchbox, 1, 1));
		ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModRegistry.heartCanister, 1, 3), new ItemStack(ModRegistry.lunchbox, 1, 1)), new ItemStack(ModRegistry.combined, 1, 0));
		
		for (Object obj : Item.REGISTRY) {
			if (obj instanceof Item) {
				Item item = (Item) obj;
				if (item instanceof ItemFood) {
					ItemFood food = (ItemFood) item;
					float foodPoints = food.getHealAmount(new ItemStack(item, 1, 0));
					ItemStack lunch = new ItemStack(ModRegistry.lunchbox, 1, 0);
					NBTTagCompound tag = new NBTTagCompound();
					tag.setFloat(NBTTagKeys.CURRENT_FOOD_STORED, foodPoints);
					lunch.setTagCompound(tag);
					ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModRegistry.lunchbox, 1, 0), new ItemStack(item, 1, 0)), lunch);
				}
			}
		}
		
		for (int i = 0; i < 16; i ++) {
			ItemStack lunchbox = new ItemStack(ModRegistry.lunchbox, 1, 0);
			lunchbox.setTagCompound(new NBTTagCompound());
			lunchbox.getTagCompound().setInteger(NBTTagKeys.COLOR, i);
			ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModRegistry.lunchbox, 1, 0), new ItemStack(Items.DYE, 1, i)), lunchbox);
		}
	}
}