package com.sixonethree.randomutilities.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.client.creativetab.CreativeTab;
import com.sixonethree.randomutilities.reference.Reference;

public class BlockContainerBase extends BlockContainer {
	private Random rand = new Random();
	private IBlockState particleBlockState = null;
	
	public BlockContainerBase() {
		this(Material.ROCK);
	}
	
	public BlockContainerBase(Material material) {
		super(material);
		this.setCreativeTab(CreativeTab.randomUtilitiesTab);
	}
	
	@Override public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
	@Override public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override public String getUnlocalizedName() {
		return String.format("tile.%s%s", Reference.RESOURCE_PREFIX, this.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	public String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
	protected void setParticleBlockState(IBlockState state) {
		this.particleBlockState = state;
	}
	
	@Override @SideOnly(Side.CLIENT) public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
		if (particleBlockState == null) return true;
		ParticleDigging.Factory digFX = new ParticleDigging.Factory();
		EnumFacing side = target.sideHit;
		BlockPos pos = target.getBlockPos();
		IBlockState iblockstate = this.particleBlockState;
		
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		float f = 0.1F;
		double d0 = (double) i + rand.nextDouble() * (1 - (double) (f * 2.0F)) + (double) f;
		double d1 = (double) j + rand.nextDouble() * (1 - (double) (f * 2.0F)) + (double) f;
		double d2 = (double) k + rand.nextDouble() * (1 - (double) (f * 2.0F)) + (double) f;
		
		if (side == EnumFacing.DOWN) d1 = (double) j - (double) f;
		if (side == EnumFacing.UP) d1 = (double) j + 1 + (double) f;
		if (side == EnumFacing.NORTH) d2 = (double) k - (double) f;
		if (side == EnumFacing.SOUTH) d2 = (double) k + 1 + (double) f;
		if (side == EnumFacing.WEST) d0 = (double) i - (double) f;
		if (side == EnumFacing.EAST) d0 = (double) i + 1 + (double) f;
		
		Particle fx = digFX.getEntityFX(0, worldObj, d0, d1, d2, 0, 0, 0, Block.getStateId(iblockstate));
		ParticleDigging dfx = (ParticleDigging) fx;
		manager.addEffect(dfx.setBlockPos(pos).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
		return true;
	}
	
	@Override @SideOnly(Side.CLIENT) public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		if (this.particleBlockState == null) return true;
		ParticleDigging.Factory digFX = new ParticleDigging.Factory();
		IBlockState state = this.particleBlockState;
		
		for (int i = 0; i < 4; i ++) {
			for (int j = 0; j < 4; j ++) {
				for (int k = 0; k < 4; k ++) {
					double d0 = (double) pos.getX() + ((double) i + 0.5D) / (double) 4;
					double d1 = (double) pos.getY() + ((double) j + 0.5D) / (double) 4;
					double d2 = (double) pos.getZ() + ((double) k + 0.5D) / (double) 4;
					manager.addEffect(digFX.getEntityFX(0, world, d0, d1, d2, d0 - (double) pos.getX() - 0.5D, d1 - (double) pos.getY() - 0.5D, d2 - (double) pos.getZ() - 0.5D, Block.getStateId(state)));
				}
			}
		}
		return true;
	}
	
	@Override public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
		worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), numberOfParticles, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] {Block.getStateId(this.particleBlockState)});
		return true;
	}
}