package com.sixonethree.randomutilities.utility;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterHome extends Teleporter {
	public int dimension;
	public int xPos;
	public int yPos;
	public int zPos;
	public float playerYaw;
	public float playerPitch;
	
	public TeleporterHome(WorldServer worldIn) {
		super(worldIn);
	}
	
	public TeleporterHome(WorldServer server, int dimensionId, int posX, int posY, int posZ, float yaw, float pitch) {
		this(server);
		dimension = dimensionId;
		xPos = posX;
		yPos = posY;
		zPos = posZ;
		playerYaw = yaw;
		playerPitch = pitch;
	}

	@Override public void placeInPortal(Entity entityIn, float rotationYaw) {
		entityIn.setLocationAndAngles((double) xPos + 0.5D, (double) yPos, (double) zPos + 0.5D, playerYaw, playerPitch);
		entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0D;
	}
	
	@Override public void removeStalePortalLocations(long worldTime) {}
}