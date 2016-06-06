package com.sixonethree.randomutilities.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

import com.sixonethree.randomutilities.common.creativetab.CreativeTab;
import com.sixonethree.randomutilities.reference.Reference;

public class BlockContainerBase extends BlockContainer {
	
	/* Constructors */
	
	public BlockContainerBase() {
		this(Material.ROCK);
	}
	
	public BlockContainerBase(Material material) {
		super(material);
		this.setCreativeTab(CreativeTab.RANDOM_UTILITIES);
	}
	
	/* Custom Methods */
	
	public String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
	/* Overridden */
	
	@Override public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
	@Override public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override public String getUnlocalizedName() {
		return String.format("tile.%s%s", Reference.RESOURCE_PREFIX, this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}