package com.sixonethree.randomutilities.common.init;

import com.sixonethree.randomutilities.common.item.ItemBase;
import com.sixonethree.randomutilities.common.item.ItemCombined;
import com.sixonethree.randomutilities.common.item.ItemHeartCanister;
import com.sixonethree.randomutilities.common.item.ItemLunchbox;
import com.sixonethree.randomutilities.common.item.ItemMagicCard;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	public static final ItemBase lunchbox = new ItemLunchbox();
	public static final ItemBase heartCanister = new ItemHeartCanister();
	public static final ItemBase combined = new ItemCombined();
	public static final ItemBase magicCard = new ItemMagicCard();
	
	public static void init() {
		GameRegistry.register(lunchbox);
		GameRegistry.register(heartCanister);
		GameRegistry.register(combined);
		GameRegistry.register(magicCard);
	}
}