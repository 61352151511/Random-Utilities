package com.sixonethree.randomutilities.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDisplayTable extends ModelBase {
	public ModelRenderer frontRightLeg;
	public ModelRenderer backRightLeg;
	public ModelRenderer top;
	public ModelRenderer frontLeftLeg;
	public ModelRenderer backLeftLeg;
	
	public ModelDisplayTable() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.frontLeftLeg = new ModelRenderer(this, 0, 0);
		this.frontLeftLeg.setRotationPoint(-8.0F, 11.0F, -8.0F);
		this.frontLeftLeg.addBox(0.0F, 0.0F, 0.0F, 2, 13, 2, 0.0F);
		this.frontRightLeg = new ModelRenderer(this, 0, 0);
		this.frontRightLeg.setRotationPoint(6.0F, 11.0F, -8.0F);
		this.frontRightLeg.addBox(0.0F, 0.0F, 0.0F, 2, 13, 2, 0.0F);
		this.backRightLeg = new ModelRenderer(this, 0, 0);
		this.backRightLeg.setRotationPoint(6.0F, 11.0F, 6.0F);
		this.backRightLeg.addBox(0.0F, 0.0F, 0.0F, 2, 13, 2, 0.0F);
		this.backLeftLeg = new ModelRenderer(this, 0, 0);
		this.backLeftLeg.setRotationPoint(-8.0F, 11.0F, 6.0F);
		this.backLeftLeg.addBox(0.0F, 0.0F, 0.0F, 2, 13, 2, 0.0F);
		this.top = new ModelRenderer(this, 0, 14);
		this.top.setRotationPoint(-8.0F, 9.0F, -8.0F);
		this.top.addBox(0.0F, 0.0F, 0.0F, 16, 2, 16, 0.0F);
	}
	
	@Override public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.frontLeftLeg.render(f5);
		this.frontRightLeg.render(f5);
		this.backRightLeg.render(f5);
		this.backLeftLeg.render(f5);
		this.top.render(f5);
	}
	
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}