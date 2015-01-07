package com.sixonethree.randomutilities.event;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.input.Keyboard;

public class TooltipEvents {
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event) {
		if (event.itemStack != null) {
			ItemStack is = event.itemStack;
			if (is.getItem() instanceof ItemFood) {
				ItemFood itemfood = (ItemFood) is.getItem();
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					String Wolves = EnumChatFormatting.WHITE + "Can be fed to wolves.";
					String Saturation = EnumChatFormatting.RED + "Saturation: " + itemfood.getHealAmount(is);
					String FoodPoints = EnumChatFormatting.RED + "Food Points: " + itemfood.getHealAmount(is);
					if (itemfood.isWolfsFavoriteMeat()) {
						if (!event.toolTip.contains(Wolves)) event.toolTip.add(Wolves);
					}
					if (!event.toolTip.contains(Saturation)) event.toolTip.add(Saturation);
					if (!event.toolTip.contains(FoodPoints)) event.toolTip.add(FoodPoints);
				}
			}
		}
	}
}