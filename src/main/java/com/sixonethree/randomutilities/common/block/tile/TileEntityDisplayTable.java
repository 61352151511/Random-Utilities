package com.sixonethree.randomutilities.common.block.tile;

import javax.annotation.Nullable;

import com.sixonethree.randomutilities.common.container.ContainerDisplayTable;
import com.sixonethree.randomutilities.common.init.ModRegistry;
import com.sixonethree.randomutilities.reference.NBTTagKeys;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
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

// TODO Capability
public class TileEntityDisplayTable extends TileEntityLockableLoot implements ITickable {
	private EnumFacing facing;
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack> withSize(25, ItemStack.EMPTY);
	
	/* Constructors */
	
	public TileEntityDisplayTable() {
		super();
		this.facing = EnumFacing.NORTH;
	}
	
	/* Custom Methods */
	
	public EnumFacing getFacing() {
		return this.facing;
	}
	
	public void setFacing(EnumFacing newFacing) {
		this.facing = newFacing;
		this.markDirty();
	}
	
	/* TileEntity */
	
	@Override @Nullable public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
	}
	
	@Override public NBTTagCompound getUpdateTag() {
		NBTTagCompound updateTag = super.getUpdateTag();
		this.writeToNBT(updateTag);
		return updateTag;
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
		
		this.setFacing(EnumFacing.VALUES[compound.getByte(NBTTagKeys.DISPLAY_TABLE_FACING)]);
		this.markDirty();
	}
	
	@Override public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, this.inventory);
		}
		
		compound.setByte(NBTTagKeys.DISPLAY_TABLE_FACING, (byte) this.getFacing().ordinal());
		
		return compound;
	}
	
	/* IInventory */
	
	@Override public void closeInventory(EntityPlayer player) {
		if (this.world == null) return;
		this.world.addBlockEvent(this.pos, ModRegistry.displayTable, 1, 0);
	}
	
	@Override public ITextComponent getDisplayName() {
		return new TextComponentString("Display Table");
	}
	
	@Override public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override public String getName() {
		return "Display Table";
	}
	
	@Override public int getSizeInventory() {
		return this.inventory.size();
	}
	
	@Override public void openInventory(EntityPlayer player) {
		if (this.world == null) return;
		this.world.addBlockEvent(this.pos, ModRegistry.displayTable, 1, 1);
	}
	
	@Override public boolean isEmpty() {
		for (ItemStack is : this.inventory) {
			if (!is.isEmpty()) return false;
		}
		
		return true;
	}
	
	@Override public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		this.fillWithLoot(playerIn);
		return new ContainerDisplayTable(playerInventory, this, 212, 199);
	}
	
	@Override public String getGuiID() {
		return "randomutilities:displaytable";
	}
	
	@Override public void update() {}
	
	@Override protected NonNullList<ItemStack> getItems() {
		return this.inventory;
	}
	
	public NonNullList<ItemStack> getInventory() {
		return this.getItems();
	}
}