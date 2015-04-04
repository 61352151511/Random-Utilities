package com.sixonethree.randomutilities.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.ISmartBlockModel;
import net.minecraftforge.client.model.ISmartItemModel;
import net.minecraftforge.common.property.IExtendedBlockState;

import com.google.common.primitives.Ints;
import com.sixonethree.randomutilities.RandomUtilities;
import com.sixonethree.randomutilities.common.block.BlockTest;

@SuppressWarnings("deprecation") public class ModelTest implements IBakedModel, ISmartBlockModel, ISmartItemModel {
	private final TextureAtlasSprite base;
	private final TextureAtlasSprite overlay;
	@SuppressWarnings("unused") private boolean hasStateSet = false;
	@SuppressWarnings("unused") private final IExtendedBlockState state;
	
	public ModelTest(TextureAtlasSprite base, TextureAtlasSprite overlay) {
		this(base, overlay, null);
	}
	
	public ModelTest(TextureAtlasSprite base, TextureAtlasSprite overlay, IExtendedBlockState state) {
		this.base = base;
		this.overlay = overlay;
		this.state = state;
	}
	
	@Override public List<BakedQuad> getFaceQuads(EnumFacing side) {
		return Collections.emptyList();
	}
	
	private int[] vertexToInts(float x, float y, float z, int color, TextureAtlasSprite texture, float u, float v) {
		return new int[] {Float.floatToRawIntBits(x), Float.floatToRawIntBits(y), Float.floatToRawIntBits(z), color, Float.floatToRawIntBits(texture.getInterpolatedU(u)), Float.floatToRawIntBits(texture.getInterpolatedV(v)), 0};
	}
	
	private BakedQuad createSidedBakedQuad(float x1, float x2, float z1, float z2, float y, TextureAtlasSprite texture, EnumFacing side) {
		Vec3 v1 = ModelTest.rotate(new Vec3(x1 - .5, y - .5, z1 - .5), side).addVector(.5, .5, .5);
		Vec3 v2 = ModelTest.rotate(new Vec3(x1 - .5, y - .5, z2 - .5), side).addVector(.5, .5, .5);
		Vec3 v3 = ModelTest.rotate(new Vec3(x2 - .5, y - .5, z2 - .5), side).addVector(.5, .5, .5);
		Vec3 v4 = ModelTest.rotate(new Vec3(x2 - .5, y - .5, z1 - .5), side).addVector(.5, .5, .5);
		return new BakedQuad(Ints.concat(vertexToInts((float) v1.xCoord, (float) v1.yCoord, (float) v1.zCoord, -1, texture, 0, 0), vertexToInts((float) v2.xCoord, (float) v2.yCoord, (float) v2.zCoord, -1, texture, 0, 16), vertexToInts((float) v3.xCoord, (float) v3.yCoord, (float) v3.zCoord, -1, texture, 16, 16), vertexToInts((float) v4.xCoord, (float) v4.yCoord, (float) v4.zCoord, -1, texture, 16, 0)), -1, side);
	}
	
	@Override public List<BakedQuad> getGeneralQuads() {
		List<BakedQuad> ret = new ArrayList<BakedQuad>();
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/cobblestone");
		
		for (EnumFacing facing : EnumFacing.VALUES) {
			ret.add(this.createSidedBakedQuad(0, 1, 0, 1, 1, this.base, facing));
			ret.add(this.createSidedBakedQuad(0, 0.2F, 0, 0.2F, 1.001F, this.overlay, facing));
			ret.add(this.createSidedBakedQuad(0.2F, 0.4F, 0.2F, 0.4F, 1.002F, sprite, facing));
		}
		
/*		EnumFacing facing = EnumFacing.EAST;
		ret.add(this.createSidedBakedQuad(0, 0.2F, 0, 0.2F, 1.001F, this.overlay, facing));
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/cobblestone");
		ret.add(this.createSidedBakedQuad(0.2F, 0.4F, 0.2F, 0.4F, 1.002F, sprite, facing));*/
		
		return ret;
	}
	
	@Override public boolean isGui3d() {
		return true;
	}
	
	@Override public boolean isAmbientOcclusion() {
		return true;
	}
	
	@Override public boolean isBuiltInRenderer() {
		return false;
	}
	
	@Override public TextureAtlasSprite getTexture() {
		return this.base;
	}
	
	@Override public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}
	
	@Override public IBakedModel handleBlockState(IBlockState state) {
		return new ModelTest(this.base, this.overlay, (IExtendedBlockState) state);
	}
	
	@Override public IBakedModel handleItemState(ItemStack stack) {
		IExtendedBlockState itemState = ((IExtendedBlockState) BlockTest.instance.getDefaultState()).withProperty(RandomUtilities.properties[1], (1 << (3 * 3)) - 1);
		return new ModelTest(this.base, this.overlay, itemState);
	}
	
	private static Vec3 rotate(Vec3 vec, EnumFacing side) {
		switch (side) {
			case DOWN:
				return new Vec3(vec.xCoord, -vec.yCoord, -vec.zCoord);
			case UP:
				return new Vec3(vec.xCoord, vec.yCoord, vec.zCoord);
			case NORTH:
				return new Vec3(vec.xCoord, vec.zCoord, -vec.yCoord);
			case SOUTH:
				return new Vec3(vec.xCoord, -vec.zCoord, vec.yCoord);
			case WEST:
				return new Vec3(-vec.yCoord, vec.xCoord, vec.zCoord);
			case EAST:
				return new Vec3(vec.yCoord, -vec.xCoord, vec.zCoord);
		}
		return null;
	}
}