package com.sixonethree.randomutilities.common.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.util.Constants;

import com.sixonethree.randomutilities.common.init.ModBlocks;
import com.sixonethree.randomutilities.reference.NBTTagKeys;

public class TileEntityDisplayTable extends TileEntity implements IInventory {
	private int facing;
	private ItemStack[] inventory = new ItemStack[25];
	
	public TileEntityDisplayTable() {
		super();
	}
	
	/* TileEntity */
	
	@Override public Packet<INetHandlerPlayClient> getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new SPacketUpdateTileEntity(this.getPos(), 0, tag);
	}
	
	@Override public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
		if (this.getWorld().isRemote) this.markDirty();
	}
	
	@Override public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.setFacing(compound.hasKey(NBTTagKeys.DISPLAY_TABLE_FACING) ? compound.getInteger(NBTTagKeys.DISPLAY_TABLE_FACING) : 0);
		NBTTagList list = compound.hasKey(NBTTagKeys.DISPLAY_TABLE_INVENTORY) ? compound.getTagList(NBTTagKeys.DISPLAY_TABLE_INVENTORY, Constants.NBT.TAG_COMPOUND) : null;
		if (list == null) return;
		for (int i = 0; i < list.tagCount(); i ++) {
			NBTTagCompound stackCompound = list.getCompoundTagAt(i);
			int slot = stackCompound.getInteger(NBTTagKeys.DISPLAY_TABLE_INVENTORY_SLOT);
			if (slot >= 0 && slot < this.inventory.length) {
				this.inventory[slot] = ItemStack.loadItemStackFromNBT(stackCompound);
			}
		}
		this.markDirty();
	}
	
	@Override public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger(NBTTagKeys.DISPLAY_TABLE_FACING, this.getFacing());
		NBTTagList list = new NBTTagList();
		int slot = 0;
		for (ItemStack stack : this.inventory) {
			if (stack != null) {
				NBTTagCompound stackCompound = new NBTTagCompound();
				stackCompound.setInteger(NBTTagKeys.DISPLAY_TABLE_INVENTORY_SLOT, slot);
				stack.writeToNBT(stackCompound);
				list.appendTag(stackCompound);
			}
			slot ++;
		}
		compound.setTag(NBTTagKeys.DISPLAY_TABLE_INVENTORY, list);
	}
	
	/* IInventory */
	
	@Override public void clear() {
		this.inventory = new ItemStack[25];
		this.markDirty();
	}
	
	@Override public void closeInventory(EntityPlayer player) {
		if (this.worldObj == null) return;
		this.worldObj.addBlockEvent(this.pos, ModBlocks.displayTable, 1, 0);
	}
	
	@Override public ItemStack decrStackSize(int slot, int amount) {
		if (slot < 0 || slot > this.inventory.length) return null;
		if (this.inventory[slot] != null) {
			if (this.inventory[slot].stackSize <= amount) {
				ItemStack itemstack = inventory[slot];
				inventory[slot] = null;
				this.markDirty();
				return itemstack;
			}
			ItemStack itemstack1 = this.inventory[slot].splitStack(amount);
			if (this.inventory[slot].stackSize == 0) {
				this.inventory[slot] = null;
			}
			this.markDirty();
			return itemstack1;
		} else {
			return null;
		}
	}
	
	@Override public ITextComponent getDisplayName() { return new TextComponentString("Display Table"); }
	@Override public int getField(int id) { return 0; }
	@Override public int getFieldCount() { return 0; }
	@Override public int getInventoryStackLimit() { return 64; }
	@Override public String getName() { return "Display Table"; }
	@Override public int getSizeInventory() { return this.inventory.length; }
	
	@Override public ItemStack getStackInSlot(int slotIn) {
		if (slotIn < 0 || slotIn > this.inventory.length) return null;
		return this.inventory[slotIn];
	}
	
	@Override public boolean hasCustomName() { return false; }
	@Override public boolean isItemValidForSlot(int index, ItemStack stack) { return true; }
	
	@Override public boolean isUseableByPlayer(EntityPlayer player) {
		if (this.worldObj == null) { return true; }
		if (this.worldObj.getTileEntity(pos) != this) { return false; }
		if (player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64D) return true;
		return false;
	}
	
	@Override public void openInventory(EntityPlayer player) {
		if (this.worldObj == null) return;
		this.worldObj.addBlockEvent(this.pos, ModBlocks.displayTable, 1, 1);
	}
	
	@Override public ItemStack removeStackFromSlot(int index) {
		if (index < 0 || index > this.inventory.length) return null;
		return this.inventory[index];
	}
	
	@Override public void setField(int id, int value) {}
	
	@Override public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index > this.inventory.length) return;
		this.markDirty();
		this.inventory[index] = stack;
	}
	
	/* TileEntityDisplayTable */
	
	public int getFacing() { return this.facing; }
	public ItemStack[] getInventory() { return this.inventory; }
	
	public void setFacing(int newFacing) {
		this.facing = newFacing;
		this.markDirty();
	}
}