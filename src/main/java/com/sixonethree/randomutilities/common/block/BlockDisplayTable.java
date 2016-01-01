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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.sixonethree.randomutilities.RandomUtilities;
import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;

public class BlockDisplayTable extends BlockContainerBase {
	public BlockDisplayTable() {
		super(Material.wood);
		this.setHardness(1F);
		this.setUnlocalizedName("displayTable");
		this.setBlockBounds(0F, 0F, 0F, 1F, (1F / 16) * 15, 1F);
		this.setParticleBlockState(Blocks.planks.getDefaultState());
	}
	
	@Override public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDisplayTable();
	}
	
	@Override public int getRenderType() {
		return 2;
	}
	
	@Override public boolean isOpaqueCube() {
		return false;
	}
	
	@Override public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		int facing = placer.getHorizontalFacing().getOpposite().getIndex();
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityDisplayTable) {
			TileEntityDisplayTable tesct = (TileEntityDisplayTable) te;
			tesct.setFacing(facing);
			world.markBlockForUpdate(pos);
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
	
	@Override public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(pos);
		if (te == null || !(te instanceof TileEntityDisplayTable)) { return true; }
		if (world.isRemote) { return true; }
		player.openGui(RandomUtilities.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}