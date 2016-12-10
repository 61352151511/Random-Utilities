package com.sixonethree.randomutilities.common.block;

import com.sixonethree.randomutilities.common.creativetab.CreativeTab;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class BlockContainerBase extends BlockContainer {
	
	/* Constructors */
	
	public BlockContainerBase() {
		this(Material.ROCK);
	}
	
	public BlockContainerBase(Material material) {
		super(material);
		this.setCreativeTab(CreativeTab.RANDOM_UTILITIES);
	}
	
	/* Overridden */
	
	@Override public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
	@Override public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}