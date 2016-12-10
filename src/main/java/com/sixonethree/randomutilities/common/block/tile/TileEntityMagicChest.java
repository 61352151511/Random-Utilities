package com.sixonethree.randomutilities.common.block.tile;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.sixonethree.randomutilities.common.container.ContainerMagicChest;
import com.sixonethree.randomutilities.common.init.ModBlocks;
import com.sixonethree.randomutilities.common.item.ILunchbox;
import com.sixonethree.randomutilities.reference.NBTTagKeys;
import com.sixonethree.randomutilities.utility.LogHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileEntityMagicChest extends TileEntityLockableLoot implements ITickable, ISidedInventory {
	private String[] owners;
	private String placer;
	private int turn;
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack> withSize(3, ItemStack.EMPTY);
	
	public TileEntityMagicChest() {
		super();
		this.placer = "";
		this.owners = new String[] {""};
		this.turn = 0;
	}
	
	/* Custom Methods */
	
	public String getPlacer() {
		return this.placer;
	}
	
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
	
	/* TileEntity */
	
	@Override @Nullable public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.getPos(), 0, this.getUpdateTag());
	}
	
	@Override public NBTTagCompound getUpdateTag() {
		NBTTagCompound updateTag = super.getUpdateTag();
		this.writeToNBT(updateTag);
		return updateTag;
	}
	
	@Override public void markDirty() {
		super.markDirty();
		this.owners = new String[] {""};
		this.turn = 0;
		inventoryCheck: if (!this.inventory.get(1).isEmpty()) {
			ItemStack magicCard = this.inventory.get(1);
			if (!magicCard.hasTagCompound()) break inventoryCheck;
			NBTTagCompound tag = magicCard.getTagCompound();
			if (!tag.hasKey(NBTTagKeys.MAGIC_CARD_SIGNERS)) break inventoryCheck;
			String signers = tag.getString(NBTTagKeys.MAGIC_CARD_SIGNERS);
			this.owners = signers.split(";");
		}
	}
	
	@Override public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		if (this.getWorld().isRemote) this.markDirty();
	}
	
	@Override public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		this.inventory = NonNullList.<ItemStack> withSize(25, ItemStack.EMPTY);
		
		if (!this.checkLootAndRead(compound)) {
			ItemStackHelper.loadAllItems(compound, this.inventory);
		}
		
		this.markDirty();
	}
	
	@Override public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, this.inventory);
		}
		
		return compound;
	}
	
	/* IInventory */
	
	@Override public void closeInventory(EntityPlayer player) {
		if (this.world == null) return;
		this.world.addBlockEvent(this.pos, ModBlocks.magicChest, 1, 0);
	}
	
	@Override public ITextComponent getDisplayName() {
		return new TextComponentString("Magic Chest");
	}
	
	@Override public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override public String getName() {
		return "Magic Chest";
	}
	
	@Override public int getSizeInventory() {
		return this.inventory.size();
	}
	
	@Override public boolean isUsableByPlayer(EntityPlayer player) {
		if (super.isUsableByPlayer(player)) {
			return this.isOwner(player.getPersistentID());
		}
		return false;
	}
	
	@Override public void openInventory(EntityPlayer player) {
		if (this.world == null) return;
		this.world.addBlockEvent(this.pos, ModBlocks.magicChest, 1, 1);
	}
	
	/* ISidedInventory */
	
	@Override public int[] getSlotsForFace(EnumFacing side) {
		if (side == EnumFacing.NORTH || side == EnumFacing.EAST || side == EnumFacing.SOUTH || side == EnumFacing.WEST) return new int[] {2};
		return new int[] {};
	}
	
	@Override public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		LogHelper.warn(index);
		if (index == 2) {
			if (!this.inventory.get(0).isEmpty()) {
				if (this.inventory.get(0).getItem() instanceof ILunchbox) {
					if (itemStackIn.getItem() instanceof ItemFood) {
						ItemFood foodItem = (ItemFood) itemStackIn.getItem();
						ILunchbox lunchbox = (ILunchbox) this.inventory.get(0).getItem();
						if (lunchbox.getCurrentFoodStorage(this.inventory.get(0)) <= (lunchbox.getMaxFoodStorage(this.inventory.get(0)) - foodItem.getHealAmount(itemStackIn))) {
							LogHelper.warn("A");
							lunchbox.setCurrentFoodStorage(this.inventory.get(0), lunchbox.getCurrentFoodStorage(this.inventory.get(0)) + foodItem.getHealAmount(itemStackIn));
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
		if (!this.world.isRemote) {
			if (!this.inventory.get(2).isEmpty()) this.inventory.set(2, ItemStack.EMPTY);
			List<EntityPlayerMP> players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
			for (EntityPlayerMP player : players) {
				if (this.isOwner(player.getPersistentID()) && this.turn < this.owners.length) {
					if (owners[turn].equals(player.getPersistentID().toString())) {
						if (!this.inventory.get(0).isEmpty() && !this.inventory.get(1).isEmpty()) {
							this.inventory.get(0).getItem().onUpdate(this.inventory.get(0), this.world, player, 0, false);
						}
						break;
					}
				}
			}
			this.turn ++;
			if (this.turn >= this.owners.length) this.turn = 0;
		}
	}
	
	@Override public boolean isEmpty() {
		for (ItemStack is : this.inventory) {
			if (!is.isEmpty()) return false;
		}
		
		return true;
	}

	@Override public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerMagicChest(playerInventory, this, 176, 132);
	}

	@Override public String getGuiID() {
		return "randomutilities:magicchest";
	}

	@Override protected NonNullList<ItemStack> getItems() {
		return this.inventory;
	}
}