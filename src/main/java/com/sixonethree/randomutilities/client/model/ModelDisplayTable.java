package com.sixonethree.randomutilities.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDisplayTable extends ModelBase {
	public ModelRenderer FrontRightLeg;
	public ModelRenderer BackRightLeg;
	public ModelRenderer Top;
	public ModelRenderer FrontLeftLeg;
	public ModelRenderer BackLeftLeg;
	
	public ModelDisplayTable() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.FrontLeftLeg = new ModelRenderer(this, 0, 0);
		this.FrontLeftLeg.setRotationPoint(-8.0F, 11.0F, -8.0F);
		this.FrontLeftLeg.addBox(0.0F, 0.0F, 0.0F, 2, 13, 2, 0.0F);
		this.FrontRightLeg = new ModelRenderer(this, 0, 0);
		this.FrontRightLeg.setRotationPoint(6.0F, 11.0F, -8.0F);
		this.FrontRightLeg.addBox(0.0F, 0.0F, 0.0F, 2, 13, 2, 0.0F);
		this.BackRightLeg = new ModelRenderer(this, 0, 0);
		this.BackRightLeg.setRotationPoint(6.0F, 11.0F, 6.0F);
		this.BackRightLeg.addBox(0.0F, 0.0F, 0.0F, 2, 13, 2, 0.0F);
		this.BackLeftLeg = new ModelRenderer(this, 0, 0);
		this.BackLeftLeg.setRotationPoint(-8.0F, 11.0F, 6.0F);
		this.BackLeftLeg.addBox(0.0F, 0.0F, 0.0F, 2, 13, 2, 0.0F);
		this.Top = new ModelRenderer(this, 0, 14);
		this.Top.setRotationPoint(-8.0F, 9.0F, -8.0F);
		this.Top.addBox(0.0F, 0.0F, 0.0F, 16, 2, 16, 0.0F);
	}
	
	@Override public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.FrontLeftLeg.render(f5);
		this.FrontRightLeg.render(f5);
		this.BackRightLeg.render(f5);
		this.BackLeftLeg.render(f5);
		this.Top.render(f5);
	}
	
	/** This is a helper function from Tabula to set the rotation of model parts */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}