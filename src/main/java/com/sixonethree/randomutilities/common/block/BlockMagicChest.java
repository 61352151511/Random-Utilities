package com.sixonethree.randomutilities.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.sixonethree.randomutilities.RandomUtilities;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;

public class BlockMagicChest extends BlockContainerBase {
	public BlockMagicChest() {
		super();
		this.setUnlocalizedName("magicChest");
		this.setHardness(1.5F);
		this.setParticleBlockState(Blocks.coal_block.getDefaultState());
	}
	
	@Override public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMagicChest();
	}
	
	@Override public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override public float getExplosionResistance(Entity exploder) {
		return 10000F;
	}
	
	@Override public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityMagicChest) {
			TileEntityMagicChest tem = (TileEntityMagicChest) te;
			tem.setPlacer(entityliving.getPersistentID().toString());
			tem.markDirty();
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
	
	@Override public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te == null || !(te instanceof TileEntityMagicChest)) { return true; }
		if (worldIn.isRemote) { return true; }
		if (((TileEntityMagicChest) te).isOwner(playerIn.getPersistentID().toString())) playerIn.openGui(RandomUtilities.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}