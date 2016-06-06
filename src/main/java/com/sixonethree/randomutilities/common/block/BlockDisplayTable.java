package com.sixonethree.randomutilities.common.block;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.RandomUtilities;
import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.reference.Reference;

public class BlockDisplayTable extends BlockContainerBase {
	protected static final AxisAlignedBB TABLE_AABB = new AxisAlignedBB(0F, 0F, 0F, 1F, (1F / 16) * 15, 1F);
	
	/* Constructors */
	
	public BlockDisplayTable() {
		super(Material.WOOD);
		this.setHardness(1F);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID.toLowerCase(), "displayTable"));
		this.setUnlocalizedName("displayTable");
	}
	
	/* Overridden */
	
	@Override public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityDisplayTable te = (TileEntityDisplayTable) worldIn.getTileEntity(pos);
		int count = 0;
		for (ItemStack stack : te.getInventory()) {
			if (count < te.getInventory().length) {
				if (stack != null) {
					float f = worldIn.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = worldIn.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = worldIn.rand.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem = new EntityItem(worldIn, (float) pos.getX() + f, (float) pos.getY() + 1 + f1, (float) pos.getZ() + f2, new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage()));
					if (stack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
					worldIn.spawnEntityInWorld(entityitem);
				}
			}
			count ++;
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDisplayTable();
	}
	
	@Override @SideOnly(Side.CLIENT) public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}
	
	@Override public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return TABLE_AABB;
	}
	
	@Override public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te == null || !(te instanceof TileEntityDisplayTable)) { return true; }
		if (worldIn.isRemote) { return true; }
		playerIn.openGui(RandomUtilities.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		EnumFacing facing = placer.getHorizontalFacing().getOpposite();
		TileEntity te = worldIn.getTileEntity(pos);
		if (te != null && te instanceof TileEntityDisplayTable) {
			TileEntityDisplayTable tesct = (TileEntityDisplayTable) te;
			tesct.setFacing(facing);
			tesct.markDirty();
		}
	}
}