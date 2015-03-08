package com.sixonethree.randomutilities.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.sixonethree.randomutilities.RandomUtilities;
import com.sixonethree.randomutilities.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.creativetab.CreativeTab;
import com.sixonethree.randomutilities.reference.Reference;

public class BlockMagicChest extends BlockContainer {
	public BlockMagicChest() {
		super(Material.rock);
		this.setCreativeTab(CreativeTab.randomUtilities);
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
	
	@Override public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override public float getExplosionResistance(Entity exploder) {
		return 10000F;
	}
	
	@Override public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack itemStack) {
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
		TileEntity te = world.getTileEntity(i, j, k);
		if (te != null && te instanceof TileEntityMagicChest) {
			TileEntityMagicChest tem = (TileEntityMagicChest) te;
			tem.setOwner(entityliving.getUniqueID().toString());
			tem.setFacing(chestFacing);
			world.markBlockForUpdate(i, j, k);
		}
	}
	
	@Override public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntityMagicChest te = (TileEntityMagicChest) world.getTileEntity(x, y, z);
		if (te != null) {
			ItemStack stack = te.getStackInSlot(0);
			if (stack != null) {
				float f = world.rand.nextFloat() * 0.8F + 0.1F;
				float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
				float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityitem = new EntityItem(world, (float) x + f, (float) y + 1 + f1, (float) z + f2, new ItemStack(stack.getItem(), stack.stackSize, stack.getCurrentDurability()));
				if (stack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
				world.spawnEntityInWorld(entityitem);
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	@Override public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int i1, float f1, float f2, float f3) {
		TileEntity te = world.getTileEntity(i, j, k);
		if (te == null || !(te instanceof TileEntityMagicChest)) { return true; }
		if (world.isSideSolid(i, j + 1, k, ForgeDirection.DOWN)) { return true; }
		if (world.isRemote) { return true; }
		if (((TileEntityMagicChest) te).getOwner().equals(player.getUniqueID().toString())) player.openGui(RandomUtilities.instance, 0, world, i, j, k);
		return true;
	}
	
	public void registerIcons(IIconRegister icon) {
		this.blockIcon = icon.registerIcon(Reference.MOD_ID.toLowerCase() + ":" + "MagicChestItem");
	}
}