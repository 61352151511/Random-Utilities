package com.sixonethree.randomutilities.common.block.tile;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.sixonethree.randomutilities.common.init.ModBlocks;
import com.sixonethree.randomutilities.common.item.ILunchbox;
import com.sixonethree.randomutilities.reference.NBTTagKeys;

public class TileEntityMagicChest extends TileEntity implements ITickable, IInventory, ISidedInventory {
	private String[] owners;
	private String placer;
	private int turn;
	private ItemStack[] inventory = new ItemStack[3];
	
	public TileEntityMagicChest() {
		super();
		this.placer = "";
		this.owners = new String[] {""};
		this.turn = 0;
	}
	
	/* TileEntity */
	
	@Override public Packet<INetHandlerPlayClient> func_145844_m() {
		NBTTagCompound tag = new NBTTagCompound();
		this.func_145841_b(tag);
		return new SPacketUpdateTileEntity(this.getPos(), 0, tag);
	}
	
	@Override public void markDirty() {
		super.markDirty();
		this.owners = new String[] {""};
		this.turn = 0;
		inventoryCheck: if (this.inventory[1] != null) {
			ItemStack magicCard = this.inventory[1];
			if (!magicCard.hasTagCompound()) break inventoryCheck;
			NBTTagCompound tag = magicCard.getTagCompound();
			if (!tag.hasKey(NBTTagKeys.MAGIC_CARD_SIGNERS)) break inventoryCheck;
			String signers = tag.getString(NBTTagKeys.MAGIC_CARD_SIGNERS);
			this.owners = signers.split(";");
		}
	}
	
	@Override public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
		if (this.getWorld().isRemote) this.markDirty();
	}
	
	@Override public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.placer = compound.hasKey(NBTTagKeys.MAGIC_CHEST_PLACER) ? compound.getString(NBTTagKeys.MAGIC_CHEST_PLACER) : "";
		this.inventory[0] = compound.hasKey(NBTTagKeys.MAGIC_CHEST_STACK) ? ItemStack.loadItemStackFromNBT((NBTTagCompound) compound.getTag(NBTTagKeys.MAGIC_CHEST_STACK)) : null;
		this.inventory[1] = compound.hasKey(NBTTagKeys.MAGIC_CHEST_CARD) ? ItemStack.loadItemStackFromNBT((NBTTagCompound) compound.getTag(NBTTagKeys.MAGIC_CHEST_CARD)) : null;
		this.markDirty();
	}
	
	@Override public void func_145841_b(NBTTagCompound compound) {
		super.func_145841_b(compound);
		compound.setString(NBTTagKeys.MAGIC_CHEST_PLACER, this.placer);
		if (this.inventory[0] != null) compound.setTag(NBTTagKeys.MAGIC_CHEST_STACK, this.inventory[0].writeToNBT(new NBTTagCompound()));
		if (this.inventory[1] != null) compound.setTag(NBTTagKeys.MAGIC_CHEST_CARD, this.inventory[1].writeToNBT(new NBTTagCompound()));
		if (this.inventory[0] == null && compound.hasKey(NBTTagKeys.MAGIC_CHEST_STACK)) compound.removeTag(NBTTagKeys.MAGIC_CHEST_STACK);
		if (this.inventory[0] == null && compound.hasKey(NBTTagKeys.MAGIC_CHEST_CARD)) compound.removeTag(NBTTagKeys.MAGIC_CHEST_CARD);
	}
	
	/* IInventory */
	
	@Override public void clear() {
		this.inventory[0] = null;
		this.inventory[1] = null;
		this.markDirty();
	}
	
	@Override public void closeInventory(EntityPlayer player) {
		if (this.worldObj == null) return;
		this.worldObj.addBlockEvent(this.pos, ModBlocks.magicChest, 1, 0);
	}
	
	@Override public ItemStack decrStackSize(int slot, int amount) {
		if (slot < 0 || slot > this.inventory.length) return null;
		if (this.inventory[slot] != null) {
			if (this.inventory[slot].stackSize <= amount) {
				ItemStack itemstack = this.inventory[slot];
				this.inventory[slot] = null;
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
	
	@Override public ITextComponent getDisplayName() { return new TextComponentString("Magic Chest"); }
	@Override public int getField(int id) { return 0; }
	@Override public int getFieldCount() { return 0; }
	@Override public int getInventoryStackLimit() { return 64; }
	@Override public String getName() { return "Magic Chest"; }
	@Override public int getSizeInventory() { return this.inventory.length; }
	
	@Override public ItemStack getStackInSlot(int slot) {
		if (slot < 0 || slot > this.inventory.length) return null;
		return this.inventory[slot];
	}
	
	@Override public boolean hasCustomName() { return false; }
	@Override public boolean isItemValidForSlot(int index, ItemStack stack) { return true; }
	
	@Override public boolean isUseableByPlayer(EntityPlayer player) {
		if (this.worldObj == null) { return true; }
		if (this.worldObj.getTileEntity(pos) != this) { return false; }
		if (player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64D) return this.isOwner(player.getPersistentID());
		return false;
	}
	
	@Override public void openInventory(EntityPlayer player) {
		if (this.worldObj == null) return;
		this.worldObj.addBlockEvent(this.pos, ModBlocks.magicChest, 1, 1);
	}
	
	@Override public ItemStack removeStackFromSlot(int slot) {
		if (slot < 0 || slot > this.inventory.length) return null;
		return this.inventory[slot];
	}
	
	@Override public void setField(int id, int value) {}
	
	@Override public void setInventorySlotContents(int slot, ItemStack content) {
		if (slot < 0 || slot > this.inventory.length) return;
		this.inventory[slot] = content;
	}
	
	/* ISidedInventory */
	
	@Override public int[] getSlotsForFace(EnumFacing side) {
		if (side == EnumFacing.NORTH || side == EnumFacing.EAST || side == EnumFacing.SOUTH || side == EnumFacing.WEST) return new int[] {2};
		return new int[] {};
	}

	@Override public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		if (index == 2) {
			if (this.inventory[0] != null) {
				if (this.inventory[0].getItem() instanceof ILunchbox) {
					if (itemStackIn.getItem() instanceof ItemFood) {
						ItemFood foodItem = (ItemFood) itemStackIn.getItem();
						ILunchbox lunchbox = (ILunchbox) this.inventory[0].getItem();
						if (lunchbox.getCurrentFoodStorage(this.inventory[0]) <= (lunchbox.getMaxFoodStorage(this.inventory[0]) - foodItem.getHealAmount(itemStackIn))) {
							lunchbox.setCurrentFoodStorage(this.inventory[0], lunchbox.getCurrentFoodStorage(this.inventory[0]) + foodItem.getHealAmount(itemStackIn));
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}
	
	/* ITickable */
	
	@Override public void update() {
		if (this.placer.isEmpty() || this.placer.equalsIgnoreCase("")) return;
		if (!this.worldObj.isRemote) {
			if (this.inventory[2] != null) this.inventory[2] = null;
			List<EntityPlayerMP> players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerList();
			for (EntityPlayerMP player : players) {
				if (this.isOwner(player.getPersistentID()) && this.turn < this.owners.length) {
					if (owners[turn].equals(player.getPersistentID().toString())) {
						if (this.inventory[0] != null && this.inventory[1] != null) {
							this.inventory[0].getItem().onUpdate(inventory[0], this.worldObj, player, 0, false);
						}
						break;
					}
				}
			}
			this.turn ++;
			if (this.turn >= this.owners.length) this.turn = 0;
		}
	}
	
	/* TileEntityMagicChest */
	
	public String getPlacer() { return this.placer; }
	
	public boolean isOwner(Object compareTo) {
		if (compareTo instanceof String) {
			String possibleOwner = (String) compareTo;
			if (possibleOwner.equals(placer)) return true;
			for (String s : this.owners) {
				if (s.equals(possibleOwner)) return true;
			}
		} else if (compareTo instanceof UUID) { return isOwner(((UUID) compareTo).toString()); }
		return false;
	}
	
	public void setPlacer(String newPlacer) {
		this.placer = newPlacer;
		this.markDirty();
	}
}