package com.sixonethree.randomutilities.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.sixonethree.randomutilities.init.ModItems;

public class RecipesLunchboxFeeding implements IRecipe {
	
	private ItemStack result;
	
	public boolean matches(InventoryCrafting window, World world) {
		this.result = null;
		byte l = 0; // Number of Lunchboxes or Combines
		byte f = 0; // Number of Food Items
		ItemStack s = null; // The Lunchbox or Combined;
		for (int i = 0; i < window.getSizeInventory(); i ++) {
			ItemStack stack = window.getStackInSlot(i);
			if (stack != null) {
				if (stack.getItem() == ModItems.lunchbox || stack.getItem() == ModItems.combined) {
					s = stack;
					l ++;
				} else {
					if (stack.getItem() instanceof ItemFood) {
						f ++;
					} else {
						return false;
					}
				}
			}
		}
		if (l == 1 && f >= 1 && s != null) {
			NBTTagCompound tag1, tag2;
			this.result = new ItemStack(s.getItem(), 1, s.getCurrentDurability());
			if (!this.result.hasTagCompound()) this.result.setTagCompound(new NBTTagCompound());
			tag1 = s.getTagCompound();
			tag2 = this.result.getTagCompound();
			float fs = tag1.hasKey("Food Stored") ? tag1.getFloat("Food Stored") : 0F;
			float mfs = tag1.hasKey("Maximum Food Stored") ? tag1.getFloat("Maximum Food Stored") : 200F;
			float hs = tag1.hasKey("Health Stored") ? tag1.getFloat("Health Stored") : 0F;
			float mhs = tag1.hasKey("Maximum Health Stored") ? tag1.getFloat("Maximum Health Stored") : 0F;
			int c = tag1.hasKey("Color") ? tag1.getInteger("Color") : -1;
			
			float fta = 0F;
			for (int i = 0; i < window.getSizeInventory(); i ++) {
				if (window.getStackInSlot(i) != null) {
					if (window.getStackInSlot(i).getItem() instanceof ItemFood) {
						fta += ((ItemFood) (window.getStackInSlot(i).getItem())).getHealAmount(window.getStackInSlot(i));
					}
				}
			}
			
			if (fs + fta <= mfs) {
				tag2.setFloat("Food Stored", fs + fta);
				tag2.setFloat("Maximum Food Stored", mfs);
				if (c > -1) tag2.setInteger("Color", c);
				if (this.result.getItem() == ModItems.combined) {
					tag2.setFloat("Health Stored", hs);
					tag2.setFloat("Maximum Health Stored", mhs);
				}
				
				this.result.setTagCompound(tag2);
				return true;
			}
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
}