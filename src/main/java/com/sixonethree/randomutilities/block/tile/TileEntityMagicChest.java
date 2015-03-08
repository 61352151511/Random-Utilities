package com.sixonethree.randomutilities.block.tile;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

import com.sixonethree.randomutilities.handler.PacketHandler;
import com.sixonethree.randomutilities.init.ModBlocks;

import cpw.mods.fml.common.FMLCommonHandler;

public class TileEntityMagicChest extends TileEntity implements IInventory {
	
	private String owner;
	private int facing;
	private float rotate = 0F;
	private ItemStack[] inventory = new ItemStack[1];
	
	public TileEntityMagicChest() {
		super();
		this.owner = "";
	}
	
	@Override public boolean canUpdate() { return true; }
	
	@SuppressWarnings("unchecked") @Override public void updateEntity() {
		rotate ++;
		if (rotate >= 360F) rotate = 0F;
		if (this.owner.isEmpty() || this.owner.equalsIgnoreCase("")) return;
		if (!this.worldObj.isRemote) {
			List<EntityPlayerMP> players = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
			for (EntityPlayerMP player : players) {
				if (player.getUniqueID().equals(UUID.fromString(this.owner))) {
					if (this.inventory[0] != null) {
						inventory[0].getItem().onUpdate(inventory[0], this.worldObj, player, 0, false);
					}
					break;
				}
			}
		}
	}
	
	public float getRotation() {
		return rotate;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
		markDirty();
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	@Override public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.owner = compound.hasKey("owner") ? compound.getString("owner") : "";
		this.facing = compound.hasKey("facing") ? compound.getInteger("facing") : 0;
		this.inventory[0] = compound.hasKey("stack") ? ItemStack.loadItemStackFromNBT((NBTTagCompound) compound.getTag("stack")) : null;
		markDirty();
	}
	
	@Override public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("owner", this.owner);
		compound.setInteger("facing", this.facing);
		if (this.inventory[0] != null) {
			compound.setTag("stack", this.inventory[0].writeToNBT(new NBTTagCompound()));
		}
	}
	
	@Override public Packet getDescriptionPacket() {
		return PacketHandler.getPacket(this);
	}
	
	public void handlePacketData(ItemStack[] stacks) {
		this.inventory = stacks;
	}
	
	public int getFacing() { return this.facing; }
	
	public void setFacing(int facing2) {
		this.facing = facing2;
	}
	
	/* IInventory */
	
	@Override public int getSizeInventory() { return 1; }
	
	@Override public ItemStack getStackInSlot(int slot) {
		if (slot != 0) return null;
		return this.inventory[0];
	}
	
	@Override public ItemStack decrStackSize(int slot, int amount) {
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
	
	@Override public ItemStack getStackInSlotOnClosing(int slot) {
		return slot == 0 ? this.inventory[0] : null;
	}
	
	@Override public void setInventorySlotContents(int slot, ItemStack content) {
		if (slot == 0) this.inventory[0] = content;
	}
	
	@Override public String getInventoryName() { return "Magic Chest"; }
	@Override public boolean isUseableByPlayer(EntityPlayer player) {
		if (worldObj == null) { return true; }
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) { return false; }
		if (player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D) return player.getUniqueID().equals(UUID.fromString(this.owner));
		return false;
	}
	
	@Override public void openChest() {
		if (worldObj == null) return;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.magicChest, 1, 1);
	}
	
	@Override public void closeChest() {
		if (worldObj == null) return;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.magicChest, 1, 0);
	}

	@Override public boolean isCustomInventoryName() {
		return false;
	}

	@Override public int getInventoryStackLimit() {
		return 64;
	}

	@Override public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}
}