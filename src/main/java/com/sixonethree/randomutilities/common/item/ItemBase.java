package com.sixonethree.randomutilities.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.sixonethree.randomutilities.client.creativetab.CreativeTab;
import com.sixonethree.randomutilities.reference.Reference;

public class ItemBase extends Item {
	public ItemBase() {
		super();
		setMaxStackSize(1);
		setCreativeTab(CreativeTab.randomUtilitiesTab);
		setNoRepair();
	}
	
	@Override public String getUnlocalizedName() {
		return String.format("item.%s%s", Reference.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override public String getUnlocalizedName(ItemStack itemStack) {
		return String.format("item.%s%s", Reference.RESOURCE_PREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
	public void tagCompoundVerification(ItemStack stack) {
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
	}
	
	public int tagOrDefault(ItemStack stack, String key, int def) {
		tagCompoundVerification(stack);
		return stack.getTagCompound().hasKey(key) ? stack.getTagCompound().getInteger(key) : def;
	}
	
	public float tagOrDefault(ItemStack stack, String key, float def) {
		tagCompoundVerification(stack);
		return stack.getTagCompound().hasKey(key) ? stack.getTagCompound().getFloat(key) : def;
	}
}