package com.sixonethree.randomutilities.client;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ValidatingSlot extends Slot {
	private boolean useValidItemsList = false;
	private ItemStack[] validItems = new ItemStack[1];
	
	public ValidatingSlot(IInventory par1iInventory, int par2, int par3, int par4, boolean useValidItemsList, ItemStack... validItems) {
		super(par1iInventory, par2, par3, par4);
		this.useValidItemsList = useValidItemsList;
		this.validItems = validItems;
	}
	
	@Override public boolean isItemValid(ItemStack par1ItemStack) {
		if (!useValidItemsList) return true;
		for (ItemStack stack : validItems) {
			if (stack.getItem().equals(par1ItemStack.getItem())) return true;
		}
		return false;
	}
}
