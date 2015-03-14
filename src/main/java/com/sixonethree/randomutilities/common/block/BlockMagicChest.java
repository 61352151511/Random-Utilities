package com.sixonethree.randomutilities.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.sixonethree.randomutilities.RandomUtilities;
import com.sixonethree.randomutilities.client.creativetab.CreativeTab;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;

public class BlockMagicChest extends BlockContainer {
	public BlockMagicChest() {
		super(Material.rock);
		this.setCreativeTab(CreativeTab.randomUtilitiesTab);
		this.setHardness(1.5F);
	}
	
	@Override public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMagicChest();
	}
	
	@Override public int getRenderType() {
		return -1;
	}
	
	@Override public boolean isOpaqueCube() {
		return false;
	}
	
	@Override public float getExplosionResistance(Entity exploder) {
		return 10000F;
	}
	
	@Override public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
		byte chestFacing = 0;
		int facing = MathHelper.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if (facing == 0) {
			chestFacing = 2;
		}
		if (facing == 1) {
			chestFacing = 5;
		}
		if (facing == 2) {
			chestFacing = 3;
		}
		if (facing == 3) {
			chestFacing = 4;
		}
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityMagicChest) {
			TileEntityMagicChest tem = (TileEntityMagicChest) te;
			tem.setPlacer(entityliving.getPersistentID().toString());
			tem.setFacing(chestFacing);
			world.markBlockForUpdate(pos);
		}
	}
	
	@Override public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityMagicChest te = (TileEntityMagicChest) world.getTileEntity(pos);
		if (te != null) {
			ItemStack stack = te.getStackInSlot(0);
			if (stack != null) {
				float f = world.rand.nextFloat() * 0.8F + 0.1F;
				float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
				float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityitem = new EntityItem(world, (float) pos.getX() + f, (float) pos.getY() + 1 + f1, (float) pos.getZ() + f2, new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage()));
				if (stack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
				world.spawnEntityInWorld(entityitem);
			}
			stack = te.getStackInSlot(1);
			if (stack != null) {
				float f = world.rand.nextFloat() * 0.8F + 0.1F;
				float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
				float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityitem = new EntityItem(world, (float) pos.getX() + f, (float) pos.getY() + 1 + f1, (float) pos.getZ() + f2, new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage()));
				if (stack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
				world.spawnEntityInWorld(entityitem);
			}
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(pos);
		if (te == null || !(te instanceof TileEntityMagicChest)) { return true; }
		if (world.isSideSolid(pos.add(0, 1, 0), EnumFacing.DOWN, false)) { return true; }
		if (world.isRemote) { return true; }
		if (((TileEntityMagicChest) te).isOwner(player.getPersistentID().toString())) player.openGui(RandomUtilities.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}