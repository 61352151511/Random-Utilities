package com.sixonethree.randomutilities.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.sixonethree.randomutilities.reference.Reference;

public class BlockBase extends Block implements ITileEntityProvider {
	public BlockBase(Material materialIn) {
		super(materialIn);
	}
	
	@Override public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
	@Override public String getUnlocalizedName() {
		return String.format("tile.%s%s", Reference.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	public String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
}