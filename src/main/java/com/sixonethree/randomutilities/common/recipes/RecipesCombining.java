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

public class RecipesCombining implements IRecipe {
	
	private ItemStack result;
	
	public boolean matches(InventoryCrafting window, World world) {
		this.result = null;
		byte l = 0;
		byte h = 0;
		byte m = -1;
		ArrayList<ItemStack> s = new ArrayList<ItemStack>();
		for (int i = 0; i < window.getSizeInventory(); i ++) {
			ItemStack stack = window.getStackInSlot(i);
			if (stack != null) {
				if ((m == -1 && stack.getItem() == ModItems.lunchbox) || (stack.getItem() == ModItems.lunchbox && stack.getItemDamage() == m)) {
					l ++;
					s.add(stack);
					if (m == -1) m = (byte) stack.getItemDamage();
				} else if ((m == -1 && stack.getItem() == ModItems.heartCanister) || (stack.getItem() == ModItems.heartCanister && stack.getItemDamage() == m)) {
					h ++;
					s.add(stack);
					if (m == -1) m = (byte) stack.getItemDamage();
				} else {
					return false;
				}
			}
		}
		if ((l > 1 && h == 0) || (h > 1 && l == 0)) {
			byte t = (byte) ((l > 1) ? 1 : 0); // 1 = Lunchbox, 0 = Heart Canister
			float fs = 0F;
			float mfs = 0F;
			float hs = 0F;
			float mhs = 0F;
			int c = -1;
			for (ItemStack stack : s) {
				if (t == 1 && stack.getItem() == ModItems.lunchbox) {
					ILunchbox cast = (ILunchbox) stack.getItem();
					fs += cast.getCurrentFoodStorage(stack);
					mfs += cast.getMaxFoodStorage(stack);
					if (c == -1 && cast.hasColor(stack)) c = cast.getColor(stack);
				} else if (t == 0 && stack.getItem() == ModItems.heartCanister) {
					IHeartCanister cast = (IHeartCanister) stack.getItem();
					hs += cast.getCurrentHealthStorage(stack);
					mhs += cast.getMaxHealthStorage(stack);
				}
			}
			this.result = new ItemStack(t == 0 ? ModItems.heartCanister : ModItems.lunchbox, 1, s.get(0).getItemDamage());
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