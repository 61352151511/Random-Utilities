package com.sixonethree.randomutilities.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;

import com.sixonethree.randomutilities.init.ModItems;
import com.sixonethree.randomutilities.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTab {
	public static final CreativeTabs randomUtilities = new CreativeTabs(Reference.MOD_ID) {
		@Override public Item getTabIconItem() {
			return ModItems.lunchbox;
		}
		
		@Override @SideOnly(Side.CLIENT) public String getTranslatedTabLabel() {
			return StatCollector.translateToLocal("itemGroup.randomUtilities");
		}
	};
}