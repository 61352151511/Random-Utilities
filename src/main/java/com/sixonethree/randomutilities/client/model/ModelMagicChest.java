package com.sixonethree.randomutilities.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMagicChest extends ModelBase {
	public ModelRenderer shape1;
	public ModelRenderer shape1_1;
	public ModelRenderer shape1_2;
	public ModelRenderer shape1_3;
	public ModelRenderer shape1_4;
	public ModelRenderer shape1_5;
	public ModelRenderer shape1_6;
	public ModelRenderer shape1_7;
	
	public ModelMagicChest() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.shape1 = new ModelRenderer(this, 0, 17);
		this.shape1.setRotationPoint(-7.0F, 9.0F, -7.0F);
		this.shape1.addBox(0.0F, 0.0F, 0.0F, 14, 1, 14, 0.0F);
		this.shape1_6 = new ModelRenderer(this, 0, 0);
		this.shape1_6.setRotationPoint(-8.0F, 23.0F, -8.0F);
		this.shape1_6.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16, 0.0F);
		this.shape1_5 = new ModelRenderer(this, 0, 48);
		this.shape1_5.setRotationPoint(-8.0F, 9.0F, -8.0F);
		this.shape1_5.addBox(0.0F, 0.0F, 0.0F, 1, 14, 1, 0.0F);
		this.shape1_7 = new ModelRenderer(this, 0, 17);
		this.shape1_7.setRotationPoint(-7.0F, 22.0F, -7.0F);
		this.shape1_7.addBox(0.0F, 0.0F, 0.0F, 14, 1, 14, 0.0F);
		this.shape1_2 = new ModelRenderer(this, 0, 0);
		this.shape1_2.setRotationPoint(-8.0F, 8.0F, -8.0F);
		this.shape1_2.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16, 0.0F);
		this.shape1_4 = new ModelRenderer(this, 0, 48);
		this.shape1_4.setRotationPoint(7.0F, 9.0F, 7.0F);
		this.shape1_4.addBox(0.0F, 0.0F, 0.0F, 1, 14, 1, 0.0F);
		this.shape1_3 = new ModelRenderer(this, 0, 48);
		this.shape1_3.setRotationPoint(7.0F, 9.0F, -8.0F);
		this.shape1_3.addBox(0.0F, 0.0F, 0.0F, 1, 14, 1, 0.0F);
		this.shape1_1 = new ModelRenderer(this, 0, 48);
		this.shape1_1.setRotationPoint(-8.0F, 9.0F, 7.0F);
		this.shape1_1.addBox(0.0F, 0.0F, 0.0F, 1, 14, 1, 0.0F);
	}
	
	@Override public void render(Entity entityIn, float float_1, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.shape1.render(scale);
		this.shape1_6.render(scale);
		this.shape1_5.render(scale);
		this.shape1_7.render(scale);
		this.shape1_2.render(scale);
		this.shape1_4.render(scale);
		this.shape1_3.render(scale);
		this.shape1_1.render(scale);
	}
	
	/** This is a helper function from Tabula to set the rotation of model parts */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}