package com.sixonethree.randomutilities.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMagicChest extends ItemBlock {
	public ItemBlockMagicChest(Block block) {
		super(block);
		setMaxDurability(0);
		setHasSubtypes(false);
	}
	
	@Override public String getUnlocalizedName(ItemStack stack) {
		return "block.magicchest";
	}
}