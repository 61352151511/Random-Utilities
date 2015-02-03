package com.sixonethree.randomutilities.recipes;

import java.util.ArrayList;

import com.sixonethree.randomutilities.init.ModItems;
import com.sixonethree.randomutilities.item.ItemHeartCanister;
import com.sixonethree.randomutilities.item.ItemLunchbox;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class RecipesCombinedCreating implements IRecipe {
	
	private ItemStack result;
	
	public boolean matches(InventoryCrafting window, World world) {
		this.result = null;
		byte l = 0;
		byte h = 0;
		ArrayList<ItemStack> s = new ArrayList<ItemStack>();
		for (int i = 0; i < window.getSizeInventory(); i ++) {
			ItemStack stack = window.getStackInSlot(i);
			if (stack != null) {
				if (stack.getItem() == ModItems.heartCanister && stack.getItemDamage() == 3) {
					h ++;
					s.add(stack);
				} else if (stack.getItem() == ModItems.lunchbox && stack.getItemDamage() == 1) {
					l ++;
					s.add(stack);
				} else {
					return false;
				}
			}
		}
		if (l >= 1 && h >= 1) {
			float fs = 0F;
			float mfs = 0F;
			float hs = 0F;
			float mhs = 0F;
			int c = -1;
			for (ItemStack stack : s) {
				if (stack.getItem() == ModItems.lunchbox) {
					fs += stack.hasTagCompound() ? stack.getTagCompound().getFloat("Food Stored") : 0F;
					mfs += stack.hasTagCompound() ? stack.getTagCompound().hasKey("Maximum Food Stored") ? stack.getTagCompound().getFloat("Maximum Food Stored") : ((ItemLunchbox) stack.getItem()).getMaxStorage(stack) : ((ItemLunchbox) stack.getItem()).getMaxStorage(stack);
					if (c == -1) c = stack.hasTagCompound() ? stack.getTagCompound().hasKey("Color") ? stack.getTagCompound().getInteger("Color") : -1 : -1;
				} else if (stack.getItem() == ModItems.heartCanister) {
					hs += stack.hasTagCompound() ? stack.getTagCompound().getFloat("Health Stored") : 0F;
					mhs += stack.hasTagCompound() ? stack.getTagCompound().hasKey("Maximum Health Stored") ? stack.getTagCompound().getFloat("Maximum Health Stored") : ((ItemHeartCanister) stack.getItem()).getMaxStorage(stack) : ((ItemHeartCanister) stack.getItem()).getMaxStorage(stack);
				}
			}
			this.result = new ItemStack(ModItems.combined, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			tag.setFloat("Health Stored", hs);
			tag.setFloat("Maximum Health Stored", mhs);
			tag.setFloat("Food Stored", fs);
			tag.setFloat("Maximum Food Stored", mfs);
			if (c != -1) tag.setInteger("Color", c);
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