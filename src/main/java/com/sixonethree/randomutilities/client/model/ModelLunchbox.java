package com.sixonethree.randomutilities.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLunchbox extends ModelBase {
	public ModelRenderer shape1;
	public ModelRenderer shape1_1;
	public ModelRenderer shape1_2;
	public ModelRenderer shape1_3;
	public ModelRenderer shape1_4;
	public ModelRenderer shape1_5;
	
	public ModelLunchbox() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.shape1 = new ModelRenderer(this, 0, 24);
		this.shape1.setRotationPoint(7.0F, 17.0F, 7.0F);
		this.shape1.addBox(0.0F, 0.0F, 0.0F, 14, 6, 1, 0.0F);
		this.setRotateAngle(shape1, 0.0F, 1.5707963267948966F, 0.0F);
		this.shape1_2 = new ModelRenderer(this, 0, 17);
		this.shape1_2.setRotationPoint(-8.0F, 17.0F, -8.0F);
		this.shape1_2.addBox(0.0F, 0.0F, 0.0F, 16, 6, 1, 0.0F);
		this.shape1_4 = new ModelRenderer(this, 0, 24);
		this.shape1_4.setRotationPoint(-8.0F, 17.0F, 7.0F);
		this.shape1_4.addBox(0.0F, 0.0F, 0.0F, 14, 6, 1, 0.0F);
		this.setRotateAngle(shape1_4, 0.0F, 1.5707963267948966F, 0.0F);
		this.shape1_5 = new ModelRenderer(this, 0, 0);
		this.shape1_5.setRotationPoint(-8.0F, 23.0F, -8.0F);
		this.shape1_5.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16, 0.0F);
		this.shape1_1 = new ModelRenderer(this, 0, 0);
		this.shape1_1.setRotationPoint(-8.0F, 17.0F, -8.0F);
		this.shape1_1.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16, 0.0F);
		this.setRotateAngle(shape1_1, 0.6829473363053812F, 0.0F, 0.0F);
		this.shape1_3 = new ModelRenderer(this, 0, 17);
		this.shape1_3.setRotationPoint(-8.0F, 17.0F, 7.0F);
		this.shape1_3.addBox(0.0F, 0.0F, 0.0F, 16, 6, 1, 0.0F);
	}
	
	@Override public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.shape1.render(f5);
		this.shape1_2.render(f5);
		this.shape1_4.render(f5);
		this.shape1_5.render(f5);
		this.shape1_1.render(f5);
		this.shape1_3.render(f5);
	}
	
	/** This is a helper function from Tabula to set the rotation of model parts */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
