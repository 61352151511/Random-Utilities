package com.sixonethree.randomutilities.recipes;

import com.sixonethree.randomutilities.init.ModItems;
import com.sixonethree.randomutilities.item.ItemHeartCanister;
import com.sixonethree.randomutilities.item.ItemLunchbox;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

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
			float fs = 0F;
			float mfs = 0F;
			float hs = 0F;
			float mhs = 0F;
			int c = -1;
			if (t == 0) hs += upgrade.hasTagCompound() ? upgrade.getTagCompound().getFloat("Health Stored") : 0F;
			if (t == 0) mhs += upgrade.hasTagCompound() ? upgrade.getTagCompound().hasKey("Maximum Health Stored") ? upgrade.getTagCompound().getFloat("Maximum Health Stored") : ((ItemHeartCanister) upgrade.getItem()).getMaxStorage(upgrade) : ((ItemHeartCanister) upgrade.getItem()).getMaxStorage(upgrade);
			if (t == 1) fs = upgrade.hasTagCompound() ? upgrade.getTagCompound().getFloat("Food Stored") : 0F;
			if (t == 1) mfs = upgrade.hasTagCompound() ? upgrade.getTagCompound().hasKey("Maximum Food Stored") ? upgrade.getTagCompound().getFloat("Maximum Food Stored") : ((ItemLunchbox) upgrade.getItem()).getMaxStorage(upgrade) : ((ItemLunchbox) upgrade.getItem()).getMaxStorage(upgrade);
			if (t == 1 && c == -1) c = upgrade.hasTagCompound() ? upgrade.getTagCompound().hasKey("Color") ? upgrade.getTagCompound().getInteger("Color") : -1 : -1;
			
			this.result = new ItemStack(upgrade.getItem(), 1, t == 0 ? upgrade.getItemDamage() + 2 : 1);
			NBTTagCompound tag = new NBTTagCompound();
			if (t == 0) tag.setFloat("Health Stored", hs);
			if (t == 0) tag.setFloat("Maximum Health Stored", mhs);
			if (t == 1) tag.setFloat("Food Stored", fs);
			if (t == 1) tag.setFloat("Maximum Food Stored", mfs);
			if (t == 1 && c != -1) tag.setInteger("Color", c);
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