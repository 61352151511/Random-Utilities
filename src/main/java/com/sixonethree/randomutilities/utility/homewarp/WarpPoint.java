package com.sixonethree.randomutilities.utility.homewarp;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextFormatting;

public class WarpPoint {
	public String name;
	public Location location;
	
	public static SaveFile warpsSaveFile;
	public static ArrayList<WarpPoint> warps = new ArrayList<WarpPoint>();
	
	public WarpPoint(EntityPlayerMP player, String name) {
		this.name = name;
		this.location = new Location(player);
	}
	
	public WarpPoint(EntityPlayer player, String name) {
		this((EntityPlayerMP) player, name);
	}
	
	public WarpPoint(String name, Location location) {
		this.name = name;
		this.location = location;
	}
	
	public WarpPoint(String info) {
		try {
			this.name = info.substring(0, info.indexOf("("));
			String locationInfo = info.substring(info.indexOf("(") + 1, info.indexOf(")"));
			this.location = new Location(locationInfo);
		} catch (Exception e) {
			System.err.println("Exception on attemping to rebuild WarpPoint from String.");
			this.name = "Error";
			this.location = new Location(0, 256, 0, 0);
		}
	}
	
	@Override public boolean equals(Object o) {
		if (o instanceof WarpPoint) { return this.name.equals(((WarpPoint) o).name); }
		return false;
	}
	
	@Override public int hashCode() {
		return this.name.hashCode();
	}
	
	@Override public String toString() {
		if (this.location == null) { return ""; }
		return this.name + "(" + this.location.toString() + ")";
	}
	
	public static void delWarp(String name) {
		WarpPoint target = new WarpPoint(name, null);
		if (warps.contains(target)) {
			warps.remove(target);
			saveAll();
		}
	}
	
	public static WarpPoint getWarp(String name) {
		WarpPoint target = new WarpPoint(name, null);
		if (warps.contains(target)) { return warps.get(warps.indexOf(target)); }
		return null;
	}
	
	public static ArrayList<String> getWarps(EntityPlayer player) {
		String warpList = "";
		for (int cur = 0; cur < warps.size(); cur ++) {
			if (warpList.length() == 0) {
				if (warps.get(cur).location.dimension != player.dimension) {
					warpList += TextFormatting.RED + warps.get(cur).name;
				} else {
					warpList += TextFormatting.GREEN + warps.get(cur).name;
					Location warpLoc = warps.get(cur).location;
					warpList += TextFormatting.RESET + " (" + ((int) Math.floor(Math.sqrt(player.getDistanceSq(warpLoc.x, warpLoc.y, warpLoc.z)))) + ")";
				}
			} else {
				Location warpLoc = warps.get(cur).location;
				warpList += TextFormatting.RESET + ", " + (warpLoc.dimension == player.dimension ? TextFormatting.GREEN : TextFormatting.RED) + warps.get(cur).name + ((warpLoc.dimension == player.dimension) ? TextFormatting.RESET + " (" + ((int) Math.floor(Math.sqrt(player.getDistanceSq(warpLoc.x, warpLoc.y, warpLoc.z)))) + ")" : "");
			}
		}
		if (warpList == "") {
			ArrayList<String> returns = new ArrayList<String>();
			returns.add("warps.message.nowarp");
			return returns;
		} else {
			ArrayList<String> returns = new ArrayList<String>();
			returns.add("warps.message.serverwarps");
			returns.add(warpList);
			return returns;
		}
	}
	
	public static List<String> getWarpsAsList(String currenttyped) {
		ArrayList<String> matchingWarps = new ArrayList<String>();
		for (int cur = 0; cur < warps.size(); cur ++) {
			if (warps.get(cur).name.startsWith(currenttyped)) {
				matchingWarps.add(warps.get(cur).name);
			}
		}
		return matchingWarps;
	}
	
	public static void loadAll() {
		warpsSaveFile.load();
		warps.clear();
		
		for (String info : warpsSaveFile.data) {
			warps.add(new WarpPoint(info));
		}
	}
	
	public static void saveAll() {
		warpsSaveFile.clear();
		for (WarpPoint home : warps) {
			warpsSaveFile.data.add(home.toString());
		}
		warpsSaveFile.save();
	}
	
	public static void setWarp(EntityPlayerMP player, String warpname) {
		Location location = new Location(player);
		WarpPoint home = new WarpPoint(warpname, location);
		
		if (warps.contains(home)) {
			warps.remove(home);
		}
		warps.add(home);
		saveAll();
	}
}