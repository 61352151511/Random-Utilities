package com.sixonethree.randomutilities.common.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMagicChest extends ItemBlock {
	public ItemBlockMagicChest(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(false);
	}
	
	@Override public String getUnlocalizedName() { return "block.randomutilities:magicchest"; }
	
	@Override public String getUnlocalizedName(ItemStack stack) {
		return "block.randomutilities:magicchest";
	}
}