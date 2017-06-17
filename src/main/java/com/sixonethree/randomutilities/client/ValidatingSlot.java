package com.sixonethree.randomutilities.client;

import javax.annotation.Nonnull;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ValidatingSlot extends Slot {
	private boolean useValidItemsList = false;
	private NonNullList<ItemStack> validItems = NonNullList.<ItemStack> withSize(1, ItemStack.EMPTY);
	
	/* Constructors */
	
	public ValidatingSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, boolean useValidItemsList, ItemStack... validItems) {
		super(inventoryIn, index, xPosition, yPosition);
		this.useValidItemsList = useValidItemsList;
		NonNullList<ItemStack> newValidItemList = NonNullList.<ItemStack> create();
		
		for (ItemStack stack : validItems) {
			if (!stack.isEmpty()) newValidItemList.add(stack);
		}
		
		this.validItems = newValidItemList;
	}
	
	/* Overridden */
	
	@Override public boolean isItemValid(@Nonnull ItemStack par1ItemStack) {
		if (!useValidItemsList) return true;
		for (ItemStack stack : validItems) {
			if (stack.getItem().equals(par1ItemStack.getItem())) return true;
		}
		return false;
	}
}
