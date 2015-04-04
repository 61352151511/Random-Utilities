package com.sixonethree.randomutilities.common.recipes;

import java.util.ArrayList;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.sixonethree.randomutilities.common.init.ModItems;
import com.sixonethree.randomutilities.common.item.IHeartCanister;
import com.sixonethree.randomutilities.common.item.ILunchbox;
import com.sixonethree.randomutilities.reference.NBTTagKeys;

public class RecipesCombinedCombining implements IRecipe {
	
	private ItemStack result;
	
	public boolean matches(InventoryCrafting window, World world) {
		this.result = null;
		byte c = 0; // Number of combineds
		byte h = 0; // Number of heart canisters
		byte l = 0; // Number of lunchboxes
		ArrayList<ItemStack> s = new ArrayList<ItemStack>(); // Valid ItemStacks
		for (int i = 0; i < window.getSizeInventory(); i ++) {
			ItemStack stack = window.getStackInSlot(i);
			if (stack != null) {
				if (stack.getItem() == ModItems.combined) {
					c ++;
					s.add(stack);
				} else if (stack.getItem() == ModItems.heartCanister) {
					h ++;
					s.add(stack);
				} else if (stack.getItem() == ModItems.lunchbox) {
					l ++;
					s.add(stack);
				} else {
					return false;
				}
			}
		}
		if ((c == 1 && (h > 0 || l > 0)) || c > 1) {
			float fs = 0F;
			float mfs = 0F;
			float hs = 0F;
			float mhs = 0F;
			int co = -1;
			for (ItemStack stack : s) {
				if (stack.getItem() == ModItems.combined) {
					ILunchbox cast1 = (ILunchbox) stack.getItem();
					IHeartCanister cast2 = (IHeartCanister) stack.getItem();
					fs += cast1.getCurrentFoodStorage(stack);
					mfs += cast1.getMaxFoodStorage(stack);
					hs += cast2.getCurrentHealthStorage(stack);
					mhs += cast2.getMaxHealthStorage(stack);
					if (co == -1 && cast1.hasColor(stack)) co = cast1.getColor(stack);
				} else if (stack.getItem() == ModItems.heartCanister) {
					IHeartCanister cast = (IHeartCanister) stack.getItem();
					hs += cast.getCurrentHealthStorage(stack);
					mhs += cast.getMaxHealthStorage(stack);
				} else if (stack.getItem() == ModItems.lunchbox) {
					ILunchbox cast = (ILunchbox) stack.getItem();
					fs += cast.getCurrentFoodStorage(stack);
					mfs += cast.getMaxFoodStorage(stack);
					if (co == -1 && cast.hasColor(stack)) co = cast.getColor(stack);
				}
			}
			this.result = new ItemStack(ModItems.combined, 1, 0);
			if (!this.result.hasTagCompound()) this.result.setTagCompound(new NBTTagCompound());
			NBTTagCompound tag = this.result.getTagCompound();
			tag.setFloat(NBTTagKeys.CURRENT_FOOD_STORED, fs);
			tag.setFloat(NBTTagKeys.MAX_FOOD_STORED, mfs);
			tag.setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, hs);
			tag.setFloat(NBTTagKeys.MAX_HEALTH_STORED, mhs);
			if (co > -1) tag.setInteger(NBTTagKeys.COLOR, co);
			this.result.setTagCompound(tag);
			return true;
		}
		return false;
	}
	
	public ItemStack getCraftingResult(InventoryCrafting window) {
		return this.result.copy();
	}
	
	public int getRecipeSize() {
		return 10;
	}
	
	public ItemStack getRecipeOutput() {
		return this.result;
	}
	
	@Override public ItemStack[] getRemainingItems(InventoryCrafting window) {
		ItemStack[] retstack = new ItemStack[window.getSizeInventory()];
		for (int i = 0; i < retstack.length; i ++) {
			ItemStack is = window.getStackInSlot(i);
			retstack[i] = ForgeHooks.getContainerItem(is);
		}
		return retstack;
	}
}