package com.sixonethree.randomutilities.client;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ValidatingSlot extends Slot {
	private boolean useValidItemsList = false;
	private ItemStack[] validItems = new ItemStack[1];
	
	/* Constructors */
	
	public ValidatingSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, boolean useValidItemsList, ItemStack... validItems) {
		super(inventoryIn, index, xPosition, yPosition);
		this.useValidItemsList = useValidItemsList;
		this.validItems = validItems;
	}
	
	/* Overridden */
	
	@Override public boolean isItemValid(ItemStack par1ItemStack) {
		if (!useValidItemsList) return true;
		for (ItemStack stack : validItems) {
			if (stack.getItem().equals(par1ItemStack.getItem())) return true;
		}
		return false;
	}
}
