package com.sixonethree.randomutilities.common.recipes;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.sixonethree.randomutilities.common.init.ModItems;
import com.sixonethree.randomutilities.reference.NBTTagKeys;

public class RecipesLunchboxDyeing implements IRecipe {
	private ItemStack result;
	
	@Override public ItemStack getCraftingResult(InventoryCrafting window) { return this.result.copy(); }
	@Override public ItemStack getRecipeOutput() { return this.result; }
	@Override public int getRecipeSize() { return 10; }
	
	@Override public ItemStack[] getRemainingItems(InventoryCrafting window) {
		ItemStack[] retstack = new ItemStack[window.getSizeInventory()];
		for (int i = 0; i < retstack.length; i ++) {
			ItemStack is = window.getStackInSlot(i);
			retstack[i] = ForgeHooks.getContainerItem(is);
		}
		return retstack;
	}
	
	@Override public boolean matches(InventoryCrafting window, World world) {
		this.result = null;
		byte l = 0;
		byte d = 0;
		byte c = 0;
		ItemStack s = null;
		for (int i = 0; i < window.getSizeInventory(); i ++) {
			ItemStack stack = window.getStackInSlot(i);
			if (stack != null) {
				if (stack.getItem() == ModItems.lunchbox || stack.getItem() == ModItems.combined) {
					s = stack;
					l ++;
				} else if (stack.getItem() == Items.DYE) {
					c = (byte) stack.getItemDamage();
					d ++;
				} else {
					return false;
				}
			}
		}
		if (l == 1 && d == 1 && s != null) {
			NBTTagCompound tag;
			this.result = new ItemStack(s.getItem(), 1, s.getItemDamage());
			if (!this.result.hasTagCompound()) this.result.setTagCompound(new NBTTagCompound());
			tag = (NBTTagCompound) s.getTagCompound().copy();
			tag.setInteger(NBTTagKeys.COLOR, c);
			this.result.setTagCompound(tag);
			return true;
		}
		return false;
	}
}