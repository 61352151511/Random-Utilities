package com.sixonethree.randomutilities.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.ExtendedBlockState;

import com.sixonethree.randomutilities.RandomUtilities;

public class BlockTest extends Block {
	
	public static BlockTest instance = new BlockTest();
	
	public BlockTest() {
		super(Material.circuits);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName("test");
	}
	
	@Override public int getRenderType() { return 3; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean isFullCube() { return false; }
	@Override public boolean isVisuallyOpaque() { return false; }
	
	@Override public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state;
	}
	
	@Override public BlockState createBlockState() {
		return new ExtendedBlockState(this, new IProperty[0], RandomUtilities.properties);
	}
}