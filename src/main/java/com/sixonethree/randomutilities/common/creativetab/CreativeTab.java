package com.sixonethree.randomutilities.common.creativetab;

import com.sixonethree.randomutilities.common.init.ModItems;
import com.sixonethree.randomutilities.reference.Reference;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTab {
	public static final CreativeTabs RANDOM_UTILITIES = new CreativeTabs(Reference.MOD_ID) {
		@Override @SideOnly(Side.CLIENT) public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.LUNCHBOX);
		}
		
		@Override @SideOnly(Side.CLIENT) public String getTranslatedTabLabel() {
			return I18n.format("itemGroup.randomUtilities");
		}
	};
}