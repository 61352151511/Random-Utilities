package com.sixonethree.randomutilities.client.creativetab;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.common.init.ModItems;
import com.sixonethree.randomutilities.reference.Reference;

public class CreativeTab {
	public static final CreativeTabs randomUtilitiesTab = new CreativeTabs(Reference.MOD_ID) {
		@Override public Item getTabIconItem() {
			return ModItems.lunchbox;
		}
		
		@Override @SideOnly(Side.CLIENT) public String getTranslatedTabLabel() {
			return I18n.format("itemGroup.randomUtilities");
		}
	};
}