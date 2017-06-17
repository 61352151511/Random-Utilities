package com.sixonethree.randomutilities.client;

import javax.annotation.Nonnull;

import com.sixonethree.randomutilities.common.item.ItemCombined;
import com.sixonethree.randomutilities.common.item.ItemLunchbox;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class RandomUtilitiesItemColors {
	public static IItemColor combined = new IItemColor() {
		@Override public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex) {
			if (tintIndex == 1) return 0x00FFFF;
			if (tintIndex == 2) return ColorLogic.getColorFromMeta(((ItemCombined) stack.getItem()).getColor(stack));
			return 0xFFFFFF;
		}
	};
	
	public static IItemColor heartCanister = new IItemColor() {
		@Override public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex) {
			if (tintIndex == 0) {
				return 0xFFFFFF;
			} else {
				switch (stack.getItemDamage()) {
					case 0:
						return 0xFF0000;
					case 1:
						return 0xFFFF00;
					case 2:
						return 0x00FF00;
					case 3:
						return 0x00FFFF;
					default:
						return 0xFFFFFF;
				}
			}
		}
	};
	
	public static IItemColor lunchbox = new IItemColor() {
		@Override public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex) {
			return tintIndex == 0 ? 0xFFFFFF : ColorLogic.getColorFromMeta(((ItemLunchbox) stack.getItem()).getColor(stack));
		}
	};
}