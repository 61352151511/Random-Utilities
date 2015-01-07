package com.sixonethree.randomutilities.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.init.ModItems;
import com.sixonethree.randomutilities.reference.Reference;

public class CreativeTab {
	public static final CreativeTabs randomUtilitiesTab = new CreativeTabs(Reference.MOD_ID) {
		@Override public Item getTabIconItem() {
			return ModItems.lunchbox;
		}
		
		@Override @SideOnly(Side.CLIENT) public String getTranslatedTabLabel() {
			return StatCollector.translateToLocal("itemGroup.randomUtilities");
		}
	};
}