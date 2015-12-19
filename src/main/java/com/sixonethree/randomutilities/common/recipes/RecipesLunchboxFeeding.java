package com.sixonethree.randomutilities.common.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.sixonethree.randomutilities.common.init.ModItems;
import com.sixonethree.randomutilities.common.item.IHeartCanister;
import com.sixonethree.randomutilities.common.item.ILunchbox;
import com.sixonethree.randomutilities.reference.NBTTagKeys;

public class RecipesLunchboxFeeding implements IRecipe {
	
	private ItemStack result;
	
	@Override public boolean matches(InventoryCrafting window, World world) {
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
			NBTTagCompound tag;
			this.result = new ItemStack(s.getItem(), 1, s.getItemDamage());
			if (!this.result.hasTagCompound()) this.result.setTagCompound(new NBTTagCompound());
			tag = this.result.getTagCompound();
			ILunchbox cast1 = null;
			IHeartCanister cast2 = null;
			try { cast1 = (ILunchbox) s.getItem(); }
			catch (ClassCastException e) {}
			try { cast2 = (IHeartCanister) s.getItem(); }
			catch (ClassCastException e) {}
			float fs, mfs, hs, mhs;
			fs = mfs = hs = mhs = 0F;
			int c = -1;
			if (cast1 != null) {
				fs = cast1.getCurrentFoodStorage(s);
				mfs = cast1.getMaxFoodStorage(s);
				if (cast1.hasColor(s)) c = cast1.getColor(s);
			}
			if (cast2 != null) {
				hs = cast2.getCurrentHealthStorage(s);
				mhs = cast2.getMaxHealthStorage(s);
			}
			
			float fta = 0F;
			for (int i = 0; i < window.getSizeInventory(); i ++) {
				if (window.getStackInSlot(i) != null) {
					if (window.getStackInSlot(i).getItem() instanceof ItemFood) {
						fta += ((ItemFood) (window.getStackInSlot(i).getItem())).getHealAmount(window.getStackInSlot(i));
					}
				}
			}
			
			if (fs + fta <= mfs) {
				tag.setFloat(NBTTagKeys.CURRENT_FOOD_STORED, fs + fta);
				tag.setFloat(NBTTagKeys.MAX_FOOD_STORED, mfs);
				if (c > -1) tag.setInteger(NBTTagKeys.COLOR, c);
				if (this.result.getItem() == ModItems.combined) {
					tag.setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, hs);
					tag.setFloat(NBTTagKeys.MAX_HEALTH_STORED, mhs);
				}
				this.result.setTagCompound(tag);
				return true;
			}
		}
		return false;
	}
	
	@Override public ItemStack getCraftingResult(InventoryCrafting window) {
		return this.result.copy();
	}
	
	@Override public int getRecipeSize() {
		return 10;
	}
	
	@Override public ItemStack getRecipeOutput() {
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