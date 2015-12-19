package com.sixonethree.randomutilities.utility;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;

public class HomePoint {
	public String name;
	public Location location;
	
	public static SaveFile homesSaveFile;
	public static ArrayList<HomePoint> homes = new ArrayList<HomePoint>();
	
	public HomePoint(EntityPlayerMP player, String name) {
		this.name = name;
		location = new Location(player);
	}
	
	public HomePoint(EntityPlayer player, String name) {
		this((EntityPlayerMP) player, name);
	}
	
	public HomePoint(String name, Location location) {
		this.name = name;
		this.location = location;
	}
	
	public static ArrayList<String> getPlayerHomes(EntityPlayer player) {
		String playerhomes = "";
		for (int cur = 0; cur < homes.size(); cur ++) {
			if (homes.get(cur).name.startsWith(player.getUniqueID().toString())) {
				if (playerhomes.length() == 0) {
					if (homes.get(cur).location.dimension != player.dimension) {
						playerhomes += EnumChatFormatting.RED + homes.get(cur).name.substring(player.getUniqueID().toString().length());
					} else {
						playerhomes += EnumChatFormatting.GREEN + homes.get(cur).name.substring(player.getUniqueID().toString().length());
						Location aloc = homes.get(cur).location;
						playerhomes += EnumChatFormatting.RESET + " (" + ((int) Math.floor(Math.sqrt(player.getDistanceSq(aloc.x, aloc.y, aloc.z)))) + ")";
					}
				} else {
					Location aloc = homes.get(cur).location;
					playerhomes += EnumChatFormatting.RESET + ", " + (aloc.dimension == player.dimension ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + homes.get(cur).name.substring(player.getUniqueID().toString().length()) + ((aloc.dimension == player.dimension) ? EnumChatFormatting.RESET + " (" + ((int) Math.floor(Math.sqrt(player.getDistanceSq(aloc.x, aloc.y, aloc.z)))) + ")" : "");
				}
			}
		}
		if (playerhomes == "") {
			ArrayList<String> returns = new ArrayList<String>();
			returns.add("homes.message.nohome");
			return returns;
		} else {
			ArrayList<String> returns = new ArrayList<String>();
			returns.add("homes.message.yourhomes");
			returns.add(playerhomes);
			return returns;
		}
	}
	
	public static List<String> getPlayerHomesAsList(EntityPlayer player, String currenttyped) {
		ArrayList<String> playerhomes = new ArrayList<String>();
		for (int cur = 0; cur < homes.size(); cur ++) {
			if (homes.get(cur).name.startsWith(player.getUniqueID().toString())) {
				String curhome = homes.get(cur).name.substring(player.getUniqueID().toString().length());
				if (curhome.startsWith(currenttyped)) {
					playerhomes.add(curhome);
				}
			}
		}
		return playerhomes;
	}
	
	public static HomePoint getHome(String name) {
		HomePoint target = new HomePoint(name, null);
		if (homes.contains(target)) { return homes.get(homes.indexOf(target)); }
		return null;
	}
	
	public static void delHome(String name) {
		HomePoint target = new HomePoint(name, null);
		if (homes.contains(target)) {
			homes.remove(target);
			saveAll();
		}
	}
	
	public static void setHome(EntityPlayerMP player, String homename) {
		Location location = new Location(player);
		HomePoint home = new HomePoint(player.getUniqueID().toString() + homename, location);
		
		if (homes.contains(home)) {
			homes.remove(home);
		}
		homes.add(home);
		saveAll();
	}
	
	public static void loadAll() {
		homesSaveFile.load();
		homes.clear();
		
		for (String info : homesSaveFile.data) {
			homes.add(new HomePoint(info));
		}
	}
	
	public static void saveAll() {
		homesSaveFile.clear();
		for (HomePoint home : homes) {
			homesSaveFile.data.add(home.toString());
		}
		homesSaveFile.save();
	}
	
	public HomePoint(String info) {
		try {
			this.name = info.substring(0, info.indexOf("("));
			String locationInfo = info.substring(info.indexOf("(") + 1, info.indexOf(")"));
			this.location = new Location(locationInfo);
		} catch (Exception e) {
			System.err.println("Exception on attemping to rebuild WarpPoint from String.");
			name = "Error";
			location = new Location(0, 256, 0, 0);
		}
	}
	
	@Override public String toString() {
		if (location == null) { return ""; }
		return name + "(" + location.toString() + ")";
	}
	
	@Override public boolean equals(Object o) {
		if (o instanceof HomePoint) { return name.equals(((HomePoint) o).name); }
		
		return false;
	}
	
	@Override public int hashCode() {
		return name.hashCode();
	}
}