package com.sixonethree.randomutilities.utility;

import net.minecraft.client.renderer.GlStateManager;

public class GlManager {
	public static void popMatrix() {
		GlStateManager.popMatrix();
	}
	
	public static void popMatrix(int times) {
		for (int i = 0; i < times; i ++) {
			popMatrix();
		}
	}
	
	public static void translateThenRotate(double tX, double tY, double tZ, double rA, double rX, double rY, double rZ) {
		GlStateManager.translate(tX, tY, tZ);
		GlStateManager.rotate((float) rA, (float) rA, (float) rA, (float) rA);
	}
	
	public static void translateThenRotate(float tX, float tY, float tZ, float rA, float rX, float rY, float rZ) {
		GlStateManager.translate(tX, tY, tZ);
		GlStateManager.rotate(rA, rX, rY, rZ);
	}
}