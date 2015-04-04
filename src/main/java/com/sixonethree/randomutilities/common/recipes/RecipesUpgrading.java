package com.sixonethree.randomutilities.common.recipes;

import net.minecraft.init.Items;
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

public class RecipesUpgrading implements IRecipe {
	
	private ItemStack result;
	
	public boolean matches(InventoryCrafting window, World world) {
		this.result = null;
		byte u = 0;
		byte n = 0;
		byte b = 0;
		ItemStack upgrade = null;
		for (int i = 0; i < window.getSizeInventory(); i ++) {
			ItemStack stack = window.getStackInSlot(i);
			if (stack != null) {
				if (u == 0 && stack.getItem() == ModItems.heartCanister && stack.getItemDamage() < 2) {
					u ++;
					upgrade = stack;
				} else if (u == 0 && stack.getItem() == ModItems.lunchbox && stack.getItemDamage() == 0) {
					u ++;
					upgrade = stack;
				} else if (stack.getItem() == Items.nether_star) {
					n ++;
				} else if (stack.getItem() == Items.bucket) {
					b ++;
				} else {
					return false;
				}
			}
		}
		if (u == 1 && n == 1 && b == 1 && upgrade != null) {
			byte t = (byte) (upgrade.getItem() == ModItems.lunchbox ? 1 : 0); // 1 Lunchbox, 0 Heart Canister
			float fs, mfs, hs, mhs;
			fs = mfs = hs = mhs = 0F;
			int c = -1;
			ILunchbox cast1 = null;
			IHeartCanister cast2 = null;
			try { cast1 = (ILunchbox) upgrade.getItem(); }
			catch (ClassCastException e) {}
			try { cast2 = (IHeartCanister) upgrade.getItem(); }
			catch (ClassCastException e) {}
			if (cast1 != null) {
				fs += cast1.getCurrentFoodStorage(upgrade);
				mfs += cast1.getMaxFoodStorage(upgrade);
				if (c == -1 && cast1.hasColor(upgrade)) c = cast1.getColor(upgrade);
			}
			if (cast2 != null) {
				hs += cast2.getCurrentHealthStorage(upgrade);
				mhs += cast2.getMaxHealthStorage(upgrade);
			}
			
			this.result = new ItemStack(upgrade.getItem(), 1, t == 0 ? upgrade.getItemDamage() + 2 : 1);
			NBTTagCompound tag = new NBTTagCompound();
			if (t == 0) tag.setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, hs);
			if (t == 0) tag.setFloat(NBTTagKeys.MAX_HEALTH_STORED, mhs);
			if (t == 1) tag.setFloat(NBTTagKeys.CURRENT_FOOD_STORED, fs);
			if (t == 1) tag.setFloat(NBTTagKeys.MAX_FOOD_STORED, mfs);
			if (t == 1 && c != -1) tag.setInteger(NBTTagKeys.COLOR, c);
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