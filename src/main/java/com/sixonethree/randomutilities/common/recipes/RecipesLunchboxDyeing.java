package com.sixonethree.randomutilities.common.recipes;

import com.sixonethree.randomutilities.common.init.ModItems;
import com.sixonethree.randomutilities.reference.NBTTagKeys;
import com.sixonethree.randomutilities.reference.Reference;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class RecipesLunchboxDyeing implements IRecipe {
	private ItemStack result = ItemStack.EMPTY;
	private ResourceLocation registryName = new ResourceLocation(Reference.MOD_ID, "recipe_lunchbox_dyeing");
	
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
		byte d = 0;
		byte c = 0;
		ItemStack s = ItemStack.EMPTY;
		for (int i = 0; i < window.getSizeInventory(); i ++) {
			ItemStack stack = window.getStackInSlot(i);
			if (!stack.isEmpty()) {
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
		if (l == 1 && d == 1 && !s.isEmpty()) {
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