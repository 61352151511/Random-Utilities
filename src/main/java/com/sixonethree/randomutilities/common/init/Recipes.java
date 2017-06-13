package com.sixonethree.randomutilities.common.init;

import java.util.ArrayList;

import com.sixonethree.randomutilities.api.ItemCombiningRecipeRegistry;
import com.sixonethree.randomutilities.common.recipes.RecipesCombinedCombining;
import com.sixonethree.randomutilities.common.recipes.RecipesCombinedCreating;
import com.sixonethree.randomutilities.common.recipes.RecipesCombining;
import com.sixonethree.randomutilities.common.recipes.RecipesLunchboxDyeing;
import com.sixonethree.randomutilities.common.recipes.RecipesLunchboxFeeding;
import com.sixonethree.randomutilities.common.recipes.RecipesUpgrading;
import com.sixonethree.randomutilities.reference.NBTTagKeys;
import com.sixonethree.randomutilities.reference.Reference;
import com.sixonethree.randomutilities.utility.RecipeHelper;
import com.sixonethree.randomutilities.utility.Utilities;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

public class Recipes {
	public static void init() {
		Category shapelessCategory = Category.SHAPELESS;
		String shapelessDependency = "after:minecraft:shapeless";
		
		RecipeSorter.register(Reference.RESOURCE_PREFIX + "combined_combining", RecipesCombinedCombining.class, shapelessCategory, shapelessDependency);
		RecipeSorter.register(Reference.RESOURCE_PREFIX + "combined_creating", RecipesCombinedCreating.class, shapelessCategory, shapelessDependency);
		RecipeSorter.register(Reference.RESOURCE_PREFIX + "combining", RecipesCombining.class, shapelessCategory, shapelessDependency);
		RecipeSorter.register(Reference.RESOURCE_PREFIX + "lunchbox_dyeing", RecipesLunchboxDyeing.class, shapelessCategory, shapelessDependency);
		RecipeSorter.register(Reference.RESOURCE_PREFIX + "lunchbox_feeding", RecipesLunchboxFeeding.class, shapelessCategory, shapelessDependency);
		RecipeSorter.register(Reference.RESOURCE_PREFIX + "upgrading", RecipesUpgrading.class, shapelessCategory, shapelessDependency);
		
		/* GameRegistry.addRecipe(new RecipesCombinedCombining());
		 * GameRegistry.addRecipe(new RecipesCombinedCreating());
		 * GameRegistry.addRecipe(new RecipesCombining());
		 * GameRegistry.addRecipe(new RecipesLunchboxDyeing());
		 * GameRegistry.addRecipe(new RecipesLunchboxFeeding());
		 * GameRegistry.addRecipe(new RecipesUpgrading()); */
		
		RecipeHelper.addShapedRecipe(new ItemStack(ModItems.heartCanister, 1, 0), 3, 3, Blocks.QUARTZ_BLOCK, Items.DIAMOND, Blocks.QUARTZ_BLOCK, Items.IRON_INGOT, Items.GOLDEN_APPLE, Items.IRON_INGOT, Blocks.QUARTZ_BLOCK, Items.DIAMOND, Blocks.QUARTZ_BLOCK);
		RecipeHelper.addShapedRecipe(new ItemStack(ModItems.heartCanister, 1, 1), 3, 3, Blocks.GOLD_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.GOLD_BLOCK, Items.NETHER_STAR, Items.GOLDEN_APPLE, Items.NETHER_STAR, Blocks.GOLD_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.GOLD_BLOCK);
		RecipeHelper.addShapedRecipe(new ItemStack(ModItems.lunchbox, 1, 0), 3, 3, ItemStack.EMPTY, Items.STICK, ItemStack.EMPTY, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.CARROT, Items.GOLDEN_APPLE, Items.BREAD);
		RecipeHelper.addShapedRecipe(new ItemStack(ModBlocks.magicChest), 3, 3, Blocks.CHEST, Blocks.CHEST, Blocks.CHEST, Blocks.CHEST, Items.NETHER_STAR, Blocks.CHEST, Blocks.CHEST, Blocks.CHEST, Blocks.CHEST);
		RecipeHelper.addShapedRecipe(new ItemStack(ModBlocks.displayTable), 3, 3, Blocks.CRAFTING_TABLE, Blocks.CRAFTING_TABLE, Blocks.CRAFTING_TABLE, Blocks.PLANKS, ItemStack.EMPTY, Blocks.PLANKS, Blocks.PLANKS, ItemStack.EMPTY, Blocks.PLANKS);
		
		ArrayList<ItemStack> UpgradeComponents = new ArrayList<ItemStack>() {
			private static final long serialVersionUID = -4349640254907860210L;
			{
				add(new ItemStack(Items.NETHER_STAR));
				add(new ItemStack(Items.BUCKET));
			}
		};
		
		ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModItems.heartCanister, 1, 0), UpgradeComponents), new ItemStack(ModItems.heartCanister, 1, 2));
		ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModItems.heartCanister, 1, 1), UpgradeComponents), new ItemStack(ModItems.heartCanister, 1, 3));
		ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModItems.lunchbox, 1, 0), UpgradeComponents), new ItemStack(ModItems.lunchbox, 1, 1));
		ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModItems.heartCanister, 1, 3), new ItemStack(ModItems.lunchbox, 1, 1)), new ItemStack(ModItems.combined, 1, 0));
	}
	
	public static void initLunchboxRecipes() {
		for (Object obj : Item.REGISTRY) {
			if (obj instanceof Item) {
				Item item = (Item) obj;
				if (item instanceof ItemFood) {
					ItemFood food = (ItemFood) item;
					float foodPoints = food.getHealAmount(new ItemStack(item, 1, 0));
					ItemStack lunch = new ItemStack(ModItems.lunchbox, 1, 0);
					NBTTagCompound tag = new NBTTagCompound();
					tag.setFloat(NBTTagKeys.CURRENT_FOOD_STORED, foodPoints);
					lunch.setTagCompound(tag);
					ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModItems.lunchbox, 1, 0), new ItemStack(item, 1, 0)), lunch);
				}
			}
		}
		
		for (int i = 0; i < 16; i ++) {
			ItemStack lunchbox = new ItemStack(ModItems.lunchbox, 1, 0);
			lunchbox.setTagCompound(new NBTTagCompound());
			lunchbox.getTagCompound().setInteger(NBTTagKeys.COLOR, i);
			ItemCombiningRecipeRegistry.registerCombiningRecipe(Utilities.recipeHelper(new ItemStack(ModItems.lunchbox, 1, 0), new ItemStack(Items.DYE, 1, i)), lunchbox);
		}
	}
}