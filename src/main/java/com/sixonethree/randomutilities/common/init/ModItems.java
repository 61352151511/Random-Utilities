package com.sixonethree.randomutilities.common.init;

import net.minecraftforge.fml.common.registry.GameRegistry;

import com.sixonethree.randomutilities.common.item.ItemBase;
import com.sixonethree.randomutilities.common.item.ItemCombined;
import com.sixonethree.randomutilities.common.item.ItemHeartCanister;
import com.sixonethree.randomutilities.common.item.ItemLunchbox;
import com.sixonethree.randomutilities.common.item.ItemMagicCard;
import com.sixonethree.randomutilities.reference.Reference;

@GameRegistry.ObjectHolder(Reference.MOD_ID) public class ModItems {
	public static final ItemBase lunchbox = new ItemLunchbox();
	public static final ItemBase heartCanister = new ItemHeartCanister();
	public static final ItemBase combined = new ItemCombined();
	public static final ItemBase magicCard = new ItemMagicCard();
	
	public static void init() {
		GameRegistry.registerItem(lunchbox, "lunchbox");
		GameRegistry.registerItem(heartCanister, "heartCanister");
		GameRegistry.registerItem(combined, "combined");
		GameRegistry.registerItem(magicCard, "magicCard");
	}
}