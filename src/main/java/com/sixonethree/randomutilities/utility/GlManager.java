package com.sixonethree.randomutilities.utility;

import net.minecraft.client.renderer.GlStateManager;

public class GlManager {
	public static void translateThenRotate(double tX, double tY, double tZ, double rA, double rX, double rY, double rZ) {
		GlStateManager.translate(tX, tY, tZ);
		GlStateManager.rotate((float) rA, (float) rA, (float) rA, (float) rA);
	}
	
	public static void translateThenRotate(float tX, float tY, float tZ, float rA, float rX, float rY, float rZ) {
		GlStateManager.translate(tX, tY, tZ);
		GlStateManager.rotate(rA, rX, rY, rZ);
	}
}