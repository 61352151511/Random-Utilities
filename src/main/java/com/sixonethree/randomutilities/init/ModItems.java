package com.sixonethree.randomutilities.init;

import com.sixonethree.randomutilities.item.ItemBase;
import com.sixonethree.randomutilities.item.ItemCombined;
import com.sixonethree.randomutilities.item.ItemHeartCanister;
import com.sixonethree.randomutilities.item.ItemLunchbox;
import com.sixonethree.randomutilities.item.ItemMagicCard;
import com.sixonethree.randomutilities.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID) public class ModItems {
	
	public static final ItemBase heartCanister = new ItemHeartCanister();
	public static final ItemBase lunchbox = new ItemLunchbox();
	public static final ItemBase combined = new ItemCombined();
	public static final ItemBase magicCard = new ItemMagicCard();
	
	public static void init() {
		GameRegistry.registerItem(heartCanister, "HeartCanister");
		GameRegistry.registerItem(lunchbox, "Lunchbox");
		GameRegistry.registerItem(combined, "Combined");
		GameRegistry.registerItem(magicCard, "magicCard");
	}
}