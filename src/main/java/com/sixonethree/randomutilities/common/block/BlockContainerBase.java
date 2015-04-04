package com.sixonethree.randomutilities.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.client.creativetab.CreativeTab;
import com.sixonethree.randomutilities.reference.Reference;

public class BlockContainerBase extends BlockContainer {
	public BlockContainerBase() {
		this(Material.rock);
	}
	
	public BlockContainerBase(Material material) {
		super(material);
		this.setCreativeTab(CreativeTab.randomUtilitiesTab);
	}
	
	@Override public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
	@Override public boolean isOpaqueCube() {
		return false;
	}
	
	@Override public String getUnlocalizedName() {
		return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	public String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
	protected void setName(String name) {
		this.setUnlocalizedName(name);
	}
	
	@Override @SideOnly(Side.CLIENT) public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
		return true;
	}
	
	@Override @SideOnly(Side.CLIENT) public boolean addDestroyEffects(World world, BlockPos pos, EffectRenderer effectRenderer) {
		return true;
	}
}