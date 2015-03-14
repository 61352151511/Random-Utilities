package com.sixonethree.randomutilities.common.item;

import com.sixonethree.randomutilities.client.creativetab.CreativeTab;
import com.sixonethree.randomutilities.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item {
	public ItemBase() {
		super();
		setMaxStackSize(1);
		setCreativeTab(CreativeTab.randomUtilitiesTab);
		setNoRepair();
	}
	
	@Override public String getUnlocalizedName() { return String.format("item.%s%s", Reference.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName())); }
	@Override public String getUnlocalizedName(ItemStack itemStack) { return String.format("item.%s%s", Reference.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName())); }
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName) { return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1); }
}