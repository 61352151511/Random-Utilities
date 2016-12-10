package com.sixonethree.randomutilities.common.container;

import com.sixonethree.randomutilities.client.ValidatingSlot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDisplayTable extends Container {
	private EntityPlayer player;
	private IInventory displayTable;
	
	/* Constructors */
	
	public ContainerDisplayTable(IInventory playerInventory, IInventory displayTableInventory, int xSize, int ySize) {
		this.player = ((InventoryPlayer) playerInventory).player;
		this.displayTable = displayTableInventory;
		displayTableInventory.openInventory(this.player);
		this.layoutContainer(playerInventory, displayTableInventory, xSize, ySize);
	}
	
	/* Custom Methods */
	
	public EntityPlayer getPlayer() {
		return this.player;
	}
	
	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) {
		int totalSlotsAdded = 0;
		for (int y = 1; y <= 5; y ++) {
			for (int x = 1; x <= 5; x ++) {
				this.addSlotToContainer(new ValidatingSlot(chestInventory, totalSlotsAdded, 44 + (x * 18), (y * 18) - 2, false));
				totalSlotsAdded ++;
			}
		}
		
		int leftCol = (xSize - 162) / 2 + 1;
		for (int playerInvRow = 0; playerInvRow < 3; playerInvRow ++) {
			for (int playerInvCol = 0; playerInvCol < 9; playerInvCol ++) {
				this.addSlotToContainer(new Slot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, ySize - (4 - playerInvRow) * 18 - 10));
			}
			
		}
		
		for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot ++) {
			this.addSlotToContainer(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 24));
		}
	}
	
	/* Overridden */
	
	@Override public boolean canInteractWith(EntityPlayer playerIn) {
		return this.displayTable.isUsableByPlayer(playerIn);
	}
	
	@Override public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		this.displayTable.closeInventory(playerIn);
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if (index < this.displayTable.getSizeInventory()) {
				if (!mergeItemStack(itemstack1, this.displayTable.getSizeInventory(), this.inventorySlots.size(), true)) return ItemStack.EMPTY;
			} else if (!mergeItemStack(itemstack1, 0, this.displayTable.getSizeInventory(), false)) return ItemStack.EMPTY;
			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}
}