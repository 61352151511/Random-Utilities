package com.sixonethree.randomutilities.common.block;

import com.sixonethree.randomutilities.RandomUtilities;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.reference.Reference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMagicChest extends BlockContainerBase {
	
	/* Constructors */
	
	public BlockMagicChest() {
		super();
		this.setHardness(1.5F);
		this.setRegistryName(new ResourceLocation(Reference.MOD_ID.toLowerCase(), "magic_chest"));
		this.setUnlocalizedName(this.getRegistryName().toString());
	}
	
	/* Overridden */
	
	@Override public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityMagicChest te = (TileEntityMagicChest) worldIn.getTileEntity(pos);
		if (te != null) {
			ItemStack stack = te.getStackInSlot(0);
			if (!stack.isEmpty()) {
				float f = worldIn.rand.nextFloat() * 0.8F + 0.1F;
				float f1 = worldIn.rand.nextFloat() * 0.8F + 0.1F;
				float f2 = worldIn.rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityitem = new EntityItem(worldIn, (float) pos.getX() + f, (float) pos.getY() + 1 + f1, (float) pos.getZ() + f2, new ItemStack(stack.getItem(), stack.getCount(), stack.getItemDamage()));
				if (stack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
				worldIn.spawnEntity(entityitem);
			}
			stack = te.getStackInSlot(1);
			if (!stack.isEmpty()) {
				float f = worldIn.rand.nextFloat() * 0.8F + 0.1F;
				float f1 = worldIn.rand.nextFloat() * 0.8F + 0.1F;
				float f2 = worldIn.rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityitem = new EntityItem(worldIn, (float) pos.getX() + f, (float) pos.getY() + 1 + f1, (float) pos.getZ() + f2, new ItemStack(stack.getItem(), stack.getCount(), stack.getItemDamage()));
				if (stack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
				worldIn.spawnEntity(entityitem);
			}
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMagicChest();
	}
	
	@Override @SideOnly(Side.CLIENT) public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override public float getExplosionResistance(Entity exploder) {
		return 10000F;
	}
	
	@Override public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te == null || !(te instanceof TileEntityMagicChest)) { return true; }
		if (worldIn.isRemote) { return true; }
		if (((TileEntityMagicChest) te).isOwner(playerIn.getPersistentID().toString())) playerIn.openGui(RandomUtilities.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te != null && te instanceof TileEntityMagicChest) {
			TileEntityMagicChest tem = (TileEntityMagicChest) te;
			tem.setPlacer(placer.getPersistentID().toString());
			tem.markDirty();
		}
	}
}