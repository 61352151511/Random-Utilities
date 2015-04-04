package com.sixonethree.randomutilities.common.block.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockDisplayTable extends ItemBlock {
	public ItemBlockDisplayTable(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(false);
	}
}