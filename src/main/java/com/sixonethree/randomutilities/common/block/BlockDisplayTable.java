package com.sixonethree.randomutilities.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.sixonethree.randomutilities.RandomUtilities;
import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;

public class BlockDisplayTable extends BlockContainerBase {
	protected static final AxisAlignedBB TABLE_AABB = new AxisAlignedBB(0F, 0F, 0F, 1F, (1F / 16) * 15, 1F);
	
	public BlockDisplayTable() {
		super(Material.WOOD);
		this.setHardness(1F);
		this.setUnlocalizedName("displayTable");
		this.setParticleBlockState(Blocks.PLANKS.getDefaultState());
	}
	
	@Override public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return TABLE_AABB;
	}
	
	@Override public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDisplayTable();
	}
	
	@Override public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		EnumFacing facing = placer.getHorizontalFacing().getOpposite();
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityDisplayTable) {
			TileEntityDisplayTable tesct = (TileEntityDisplayTable) te;
			tesct.setFacing(facing);
			tesct.markDirty();
		}
	}
	
	@Override public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityDisplayTable te = (TileEntityDisplayTable) world.getTileEntity(pos);
		int count = 0;
		for (ItemStack stack : te.getInventory()) {
			if (count < te.getInventory().length) {
				if (stack != null) {
					float f = world.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem = new EntityItem(world, (float) pos.getX() + f, (float) pos.getY() + 1 + f1, (float) pos.getZ() + f2, new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage()));
					if (stack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
					world.spawnEntityInWorld(entityitem);
				}
			}
			count ++;
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te == null || !(te instanceof TileEntityDisplayTable)) { return true; }
		if (worldIn.isRemote) { return true; }
		playerIn.openGui(RandomUtilities.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}