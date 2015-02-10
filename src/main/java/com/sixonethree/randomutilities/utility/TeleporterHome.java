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
	
	public TeleporterHome(WorldServer par1WorldServer) {
		super(par1WorldServer);
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

	public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
		par1Entity.setLocationAndAngles((double) xPos + 0.5D, (double) yPos, (double) zPos + 0.5D, playerYaw, playerPitch);
		par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
	}
	
	@Override
	public void removeStalePortalLocations(long par1) {}
}