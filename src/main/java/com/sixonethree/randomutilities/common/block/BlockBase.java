package com.sixonethree.randomutilities.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.sixonethree.randomutilities.reference.Reference;

public class BlockBase extends Block {
	public BlockBase(Material materialIn) {
		super(materialIn);
	}
	
	@Override public String getUnlocalizedName() {
		return String.format("tile.%s%s", Reference.RESOURCE_PREFIX, this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	public String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
}