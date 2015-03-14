package com.sixonethree.randomutilities.common.recipes;

import java.util.ArrayList;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.sixonethree.randomutilities.common.init.ModItems;
import com.sixonethree.randomutilities.common.item.ItemHeartCanister;
import com.sixonethree.randomutilities.common.item.ItemLunchbox;

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
					NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
					fs += tag.hasKey("Food Stored") ? tag.getFloat("Food Stored") : 0F;
					mfs += tag.hasKey("Maximum Food Stored") ? tag.getFloat("Maximum Food Stored") : 200F;
					hs += tag.hasKey("Health Stored") ? tag.getFloat("Health Stored") : 0F;
					mhs += tag.hasKey("Maximum Health Stored") ? tag.getFloat("Maximum Health Stored") : 2000F;
					co = (co != -1) ? co : tag.hasKey("Color") ? tag.getInteger("Color") : -1;
				} else if (stack.getItem() == ModItems.heartCanister) {
					NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
					hs += tag.hasKey("Health Stored") ? tag.getFloat("Health Stored") : 0F;
					mhs += tag.hasKey("Maximum Health Stored") ? tag.getFloat("Maximum Health Stored") : ((ItemHeartCanister) stack.getItem()).getMaxStorage(stack);
				} else if (stack.getItem() == ModItems.lunchbox) {
					NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
					fs += tag.hasKey("Food Stored") ? tag.getFloat("Food Stored") : 0F;
					mfs += tag.hasKey("Maximum Food Stored") ? tag.getFloat("Maximum Food Stored") : ((ItemLunchbox) stack.getItem()).getMaxStorage(stack);
					co = (co != -1) ? co : tag.hasKey("Color") ? tag.getInteger("Color") : -1;
				}
			}
			this.result = new ItemStack(ModItems.combined, 1, 0);
			if (!this.result.hasTagCompound()) this.result.setTagCompound(new NBTTagCompound());
			NBTTagCompound tag = this.result.getTagCompound();
			tag.setFloat("Food Stored", fs);
			tag.setFloat("Maximum Food Stored", mfs);
			tag.setFloat("Health Stored", hs);
			tag.setFloat("Maximum Health Stored", mhs);
			if (co > -1) tag.setInteger("Color", co);
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