package com.sixonethree.randomutilities.utility.homewarp;

import net.minecraft.entity.player.EntityPlayerMP;

public class Location {
	public int x, y, z;
	public double posX, posY, posZ;
	public int dimension;
	
	public Location(int x, int y, int z, int dimension) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.dimension = dimension;
	}
	
	public Location(double posX, double posY, double posZ, int dimension) {
		this.init(posX, posY, posZ, dimension);
	}
	
	public Location(EntityPlayerMP player) {
		this.init(player.posX, player.posY, player.posZ, player.dimension);
	}
	
	public Location(String info) {
		String[] part = info.split("[,]");
		try {
			this.init(Double.parseDouble(part[0]), Double.parseDouble(part[1]), Double.parseDouble(part[2]), Integer.parseInt(part[3]));
		} catch (Exception e) {
			System.err.println("Exception on attemping to rebuild Location from String.");
			this.init(0, 256, 0, 0);
		}
	}
	
	@Override public boolean equals(Object o) {
		if (o instanceof Location) {
			Location location = (Location) o;
			boolean equal = true;
			equal = equal && this.posX == location.posX;
			equal = equal && this.posY == location.posY;
			equal = equal && this.posZ == location.posZ;
			equal = equal && this.dimension == location.dimension;
			return equal;
		}
		return false;
	}
	
	@Override public String toString() {
		return posX + "," + posY + "," + posZ + "," + dimension;
	}
	
	private void init(double posX, double posY, double posZ, int dimension) {
		this.x = round(posX);
		this.y = round(posY);
		this.z = round(posZ);
		
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		
		this.dimension = dimension;
	}
	
	private static int round(double pos) {
		return (int) Math.floor(pos);
	}
}