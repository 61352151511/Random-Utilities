package com.sixonethree.randomutilities.common.block.tile;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.sixonethree.randomutilities.common.handler.PacketHandler;
import com.sixonethree.randomutilities.common.init.ModBlocks;
import com.sixonethree.randomutilities.reference.NBTTagKeys;

public class TileEntityMagicChest extends TileEntity implements IUpdatePlayerListBox, IInventory {
	
	private String[] owners;
	private String placer;
	private int facing;
	private int turn;
	private float rotate = 0F;
	private ItemStack[] inventory = new ItemStack[2];
	
	public TileEntityMagicChest() {
		super();
		this.placer = "";
		this.owners = new String[] {""};
		this.turn = 0;
	}
	
	// @Override public boolean canUpdate() { return true; }
	
	@SuppressWarnings("unchecked") @Override public void update() {
		rotate ++;
		if (rotate >= 360F) rotate = 0F;
		if (this.placer.isEmpty() || this.placer.equalsIgnoreCase("")) return;
		if (!this.worldObj.isRemote) {
			List<EntityPlayerMP> players = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
			for (EntityPlayerMP player : players) {
				if (isOwner(player.getPersistentID()) && turn < owners.length) {
					if (owners[turn].equals(player.getPersistentID().toString())) {
						if (this.inventory[0] != null && this.inventory[1] != null) {
							inventory[0].getItem().onUpdate(inventory[0], this.worldObj, player, 0, false);
						}
						break;
					}
				}
			}
			turn ++;
			if (turn >= owners.length) turn = 0;
		}
	}
	
	public boolean isOwner(Object compareTo) {
		if (compareTo instanceof String) {
			String possibleOwner = (String) compareTo;
			for (String s : owners) {
				if (s.equals(possibleOwner)) return true;
			}
			if (possibleOwner.equals(placer)) return true;
		} else if (compareTo instanceof UUID) {
			return isOwner(((UUID) compareTo).toString());
		}
		return false;
	}
	
	public float getRotation() {
		return rotate;
	}
	
	public void setPlacer(String placer) {
		this.placer = placer;
		markDirty();
	}
	
	public String getPlacer() { return this.placer; }
	
	@Override public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.placer = compound.hasKey("owner") ? compound.getString("owner") : "";
		this.facing = compound.hasKey("facing") ? compound.getInteger("facing") : 0;
		this.inventory[0] = compound.hasKey("stack") ? ItemStack.loadItemStackFromNBT((NBTTagCompound) compound.getTag("stack")) : null;
		this.inventory[1] = compound.hasKey("card") ? ItemStack.loadItemStackFromNBT((NBTTagCompound) compound.getTag("card")) : null;
		markDirty();
	}
	
	@Override public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("owner", this.placer);
		compound.setInteger("facing", this.facing);
		if (this.inventory[0] != null) compound.setTag("stack", this.inventory[0].writeToNBT(new NBTTagCompound()));
		if (this.inventory[1] != null) compound.setTag("card", inventory[1].writeToNBT(new NBTTagCompound()));
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
	
	@Override public void markDirty() {
		super.markDirty();
		owners = new String[] {""};
		turn = 0;
		inventoryCheck: if (this.inventory[1] != null) {
			ItemStack magicCard = this.inventory[1];
			if (!magicCard.hasTagCompound()) break inventoryCheck;
			NBTTagCompound tag = magicCard.getTagCompound();
			if (!tag.hasKey(NBTTagKeys.MAGIC_CARD_SIGNERS)) break inventoryCheck;
			String signers = tag.getString(NBTTagKeys.MAGIC_CARD_SIGNERS);
			owners = signers.split(";");
		}
	}
	
	/* IInventory */
	
	@Override public int getSizeInventory() { return this.inventory.length; }
	
	@Override public ItemStack getStackInSlot(int slot) {
		if (slot < 0 || slot > this.inventory.length) return null;
		return this.inventory[slot];
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
	
	@Override public ItemStack getStackInSlotOnClosing(int slot) {
		if (slot < 0 || slot > this.inventory.length) return null;
		return this.inventory[slot];
	}
	
	@Override public void setInventorySlotContents(int slot, ItemStack content) {
		if (slot < 0 || slot > this.inventory.length) return;
		this.inventory[slot] = content;
	}
	
	@Override public IChatComponent getDisplayName() { return new ChatComponentText("Magic Chest"); }
	@Override public boolean isUseableByPlayer(EntityPlayer player) {
		//TODO Check the magic card
		if (worldObj == null) { return true; }
		if (worldObj.getTileEntity(pos) != this) { return false; }
		if (player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64D) return isOwner(player.getPersistentID());
		return false;
	}
	
	@Override public void openInventory(EntityPlayer player) {
		if (worldObj == null) return;
		worldObj.addBlockEvent(pos, ModBlocks.magicChest, 1, 1);
	}
	
	@Override public void closeInventory(EntityPlayer player) {
		if (worldObj == null) return;
		worldObj.addBlockEvent(pos, ModBlocks.magicChest, 1, 0);
	}

	@Override public boolean hasCustomName() {
		return false;
	}

	@Override public int getInventoryStackLimit() {
		return 64;
	}

	@Override public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override public String getCommandSenderName() {
		return null;
	}

	@Override public int getField(int id) {
		return 0;
	}

	@Override public void setField(int id, int value) {}

	@Override public int getFieldCount() {
		return 0;
	}

	@Override public void clear() {}
}