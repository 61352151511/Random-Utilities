package com.sixonethree.randomutilities.client;

public class ColorLogic {
	public static int getColorFromMeta(int meta) {
		switch (meta) {
			case 0:
				return 0x191919;
			case 1:
				return 0x993333;
			case 2:
				return 0x667F33;
			case 3:
				return 0x664C33;
			case 4:
				return 0x334CB2;
			case 5:
				return 0x7F3FB2;
			case 6:
				return 0x4C7F99;
			case 7:
				return 0x999999;
			case 8:
				return 0x4C4C4C;
			case 9:
				return 0xF27FA5;
			case 10:
				return 0x7FCC19;
			case 11:
				return 0xE5E533;
			case 12:
				return 0x6699D8;
			case 13:
				return 0xB24CD8;
			case 14:
				return 0xD87F33;
			case 15:
				return 0xFFFFFF;
			default:
				return 0xC3C3C3;
		}
	}
}