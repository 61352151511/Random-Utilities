package com.sixonethree.randomutilities.common.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;

import com.sixonethree.randomutilities.common.handler.PacketHandler;
import com.sixonethree.randomutilities.common.init.ModBlocks;

public class TileEntityDisplayTable extends TileEntity implements IInventory {
	private ItemStack[] inventory = new ItemStack[25];
	private int facing = 0;
	
	public TileEntityDisplayTable() {
		super();
	}
	
	public void setFacing(int facing) {
		this.facing = facing;
	}
	
	public int getFacing() {
		return this.facing;
	}
	
	public ItemStack[] getInventory() {
		return this.inventory;
	}
	
	public void handlePacketData(ItemStack[] stacks) {
		this.inventory = stacks;
	}
	
	@Override public Packet getDescriptionPacket() {
		this.worldObj.markBlockForUpdate(this.pos);
		return PacketHandler.getPacket(this);
	}
	
	@Override public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("facing", this.getFacing());
		NBTTagList list = new NBTTagList();
		int slot = 0;
		for (ItemStack stack : inventory) {
			if (stack != null) {
				NBTTagCompound stackCompound = new NBTTagCompound();
				stackCompound.setInteger("Slot", slot);
				stack.writeToNBT(stackCompound);
				list.appendTag(stackCompound);
			}
			slot ++;
		}
		compound.setTag("Inventory", list);
	}
	
	@Override public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.setFacing(compound.hasKey("facing") ? compound.getInteger("facing") : 0);
		NBTTagList list = compound.hasKey("Inventory") ? compound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND) : null;
		if (list == null) return;
		for (int i = 0; i < list.tagCount(); i ++) {
			NBTTagCompound stackCompound = list.getCompoundTagAt(i);
			int slot = stackCompound.getInteger("Slot");
			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(stackCompound);
			}
		}
		this.markDirty();
	}
	
	@Override public void markDirty() {
		super.markDirty();
		// this.worldObj.markBlockForUpdate(this.pos);
	}
	
	@Override public int getSizeInventory() {
		return this.inventory.length;
	}
	
	@Override public ItemStack getStackInSlot(int slotIn) {
		if (slotIn < 0 || slotIn > this.inventory.length) return null;
		return this.inventory[slotIn];
	}
	
	@Override public ItemStack decrStackSize(int slot, int amount) {
		if (slot < 0 || slot > this.inventory.length) return null;
		if (inventory[slot] != null) {
			if (inventory[slot].stackSize <= amount) {
				ItemStack itemstack = inventory[slot];
				inventory[slot] = null;
				markDirty();
				return itemstack;
			}
			ItemStack itemstack1 = inventory[slot].splitStack(amount);
			if (inventory[slot].stackSize == 0) {
				inventory[slot] = null;
			}
			markDirty();
			return itemstack1;
		} else {
			return null;
		}
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int index) {
		if (index < 0 || index > this.inventory.length) return null;
		return this.inventory[index];
	}
	
	@Override public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index > this.inventory.length) return;
		this.markDirty();
		this.inventory[index] = stack;
	}
	
	@Override public String getCommandSenderName() {
		return "Display Table";
	}
	
	@Override public boolean hasCustomName() {
		return false;
	}
	
	@Override public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer player) {
		if (worldObj == null) { return true; }
		if (worldObj.getTileEntity(pos) != this) { return false; }
		if (player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64D) return true;
		return false;
	}
	
	@Override public void openInventory(EntityPlayer player) {
		if (worldObj == null) return;
		worldObj.addBlockEvent(this.pos, ModBlocks.displayTable, 1, 1);
	}
	
	@Override public void closeInventory(EntityPlayer player) {
		if (worldObj == null) return;
		worldObj.addBlockEvent(this.pos, ModBlocks.displayTable, 1, 0);
	}
	
	@Override public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override public IChatComponent getDisplayName() {
		return new ChatComponentText("Display Table");
	}

	@Override public int getField(int id) {
		return 0;
	}

	@Override public void setField(int id, int value) {}

	@Override public int getFieldCount() {
		return 0;
	}

	@Override public void clear() {
		inventory = new ItemStack[25];
	}
}