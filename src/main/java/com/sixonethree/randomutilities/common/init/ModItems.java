package com.sixonethree.randomutilities.common.init;

import com.sixonethree.randomutilities.common.item.ItemBase;
import com.sixonethree.randomutilities.common.item.ItemCombined;
import com.sixonethree.randomutilities.common.item.ItemHeartCanister;
import com.sixonethree.randomutilities.common.item.ItemLunchbox;
import com.sixonethree.randomutilities.common.item.ItemMagicCard;
import com.sixonethree.randomutilities.reference.Reference;

import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID) public class ModItems {
	public static final ItemBase LUNCHBOX = new ItemLunchbox();
	public static final ItemBase HEART_CANISTER = new ItemHeartCanister();
	public static final ItemBase COMBINED = new ItemCombined();
	public static final ItemBase MAGIC_CARD = new ItemMagicCard();
	
	public static void init() {
		GameRegistry.register(LUNCHBOX);
		GameRegistry.register(HEART_CANISTER);
		GameRegistry.register(COMBINED);
		GameRegistry.register(MAGIC_CARD);
	}
}