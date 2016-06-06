package com.sixonethree.randomutilities.common.container;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.sixonethree.randomutilities.client.ValidatingSlot;
import com.sixonethree.randomutilities.common.init.ModItems;

public class ContainerMagicChest extends Container {
	private EntityPlayer player;
	private IInventory chest;
	
	/* Constructors */
	
	public ContainerMagicChest(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) {
		this.player = ((InventoryPlayer) playerInventory).player;
		this.chest = chestInventory;
		chestInventory.openInventory(player);
		this.layoutContainer(playerInventory, chestInventory, xSize, ySize);
	}
	
	/* Custom Methods */
	
	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) {
		this.addSlotToContainer(new ValidatingSlot(chestInventory, 0, 80, 18, false));
		this.addSlotToContainer(new ValidatingSlot(chestInventory, 1, 152, 18, true, new ItemStack(ModItems.MAGIC_CARD)));
		
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
	
	public EntityPlayer getPlayer() {
		return this.player;
	}
	
	/* Overridden */
	
	@Override public boolean canInteractWith(EntityPlayer playerIn) {
		return this.chest.isUseableByPlayer(playerIn);
	}
	
	@Override public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		this.chest.closeInventory(playerIn);
	}
	
	@Override @Nullable public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index < 1) {
				if (!mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) { return null; }
			} else if (!slot.isItemValid(itemstack1)) {
				return null;
			} else if (!mergeItemStack(itemstack1, 0, 1, false)) { return null; }
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}
		this.chest.markDirty();
		return itemstack;
	}
}