package com.sixonethree.randomutilities.common.recipes;

import java.util.ArrayList;

import com.sixonethree.randomutilities.common.init.ModRegistry;
import com.sixonethree.randomutilities.common.item.IHeartCanister;
import com.sixonethree.randomutilities.common.item.ILunchbox;
import com.sixonethree.randomutilities.reference.NBTTagKeys;
import com.sixonethree.randomutilities.reference.Reference;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class RecipesCombinedCreating implements IRecipe {
	private ItemStack result = ItemStack.EMPTY;
	private ResourceLocation registryName = new ResourceLocation(Reference.MOD_ID, "recipe_combined_creating");
	
	@Override public ItemStack getCraftingResult(InventoryCrafting window) {
		return this.result.copy();
	}
	
	@Override public ItemStack getRecipeOutput() {
		return this.result;
	}
	
	@Override public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack> withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		
		for (int i = 0; i < nonnulllist.size(); i ++) {
			nonnulllist.set(i, ForgeHooks.getContainerItem(inv.getStackInSlot(i)));
		}
		
		return nonnulllist;
	}
	
	@Override public boolean matches(InventoryCrafting window, World world) {
		this.result = ItemStack.EMPTY;
		byte l = 0;
		byte h = 0;
		ArrayList<ItemStack> s = new ArrayList<>();
		for (int i = 0; i < window.getSizeInventory(); i ++) {
			ItemStack stack = window.getStackInSlot(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() == ModRegistry.heartCanister && stack.getItemDamage() == 3) {
					h ++;
					s.add(stack);
				} else if (stack.getItem() == ModRegistry.lunchbox && stack.getItemDamage() == 1) {
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
				if (stack.getItem() == ModRegistry.lunchbox) {
					ILunchbox cast = (ILunchbox) stack.getItem();
					fs += cast.getCurrentFoodStorage(stack);
					mfs += cast.getMaxFoodStorage(stack);
					if (c == -1 && cast.hasColor(stack)) c = cast.getColor(stack);
				} else if (stack.getItem() == ModRegistry.heartCanister) {
					IHeartCanister cast = (IHeartCanister) stack.getItem();
					hs += cast.getCurrentHealthStorage(stack);
					mhs += cast.getMaxHealthStorage(stack);
				}
			}
			this.result = new ItemStack(ModRegistry.combined, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			tag.setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, hs);
			tag.setFloat(NBTTagKeys.MAX_HEALTH_STORED, mhs);
			tag.setFloat(NBTTagKeys.CURRENT_FOOD_STORED, fs);
			tag.setFloat(NBTTagKeys.MAX_FOOD_STORED, mfs);
			if (c != -1) tag.setInteger(NBTTagKeys.COLOR, c);
			this.result.setTagCompound(tag);
			return true;
		}
		return false;
	}
	
	@Override public IRecipe setRegistryName(ResourceLocation name) {
		this.setRegistryName(name);
		return this;
	}
	
	@Override public ResourceLocation getRegistryName() {
		return registryName;
	}
	
	@Override public Class<IRecipe> getRegistryType() {
		return IRecipe.class;
	}
	
	@Override public boolean canFit(int width, int height) {
		return width >= 2 && height >= 2;
	}
}