package com.sixonethree.randomutilities.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;

import com.sixonethree.randomutilities.utility.Location;

public class CommandReference {
	public static class LastLocations {
		private static HashMap<UUID, Location> LastPlaces = new HashMap<UUID, Location>();
		public static void set(EntityPlayerMP player, Location loc) {
			LastPlaces.put(player.getUniqueID(), loc);
		}
		
		public static void set(EntityPlayerMP player, double X, double Y, double Z, int dim) {
			LastPlaces.put(player.getUniqueID(), new Location(X, Y, Z, dim));
		}
		
		public static Location get(EntityPlayerMP player) {
			UUID puid = player.getUniqueID();
			if (LastPlaces.containsKey(puid)) {
				return LastPlaces.get(puid);
			}
			return null;
		}
	}
	
	public static class MutedPlayers {
		private static ArrayList<UUID> mutes = new ArrayList<UUID>();
		public static void add(UUID target) { mutes.add(target); }
		public static boolean isMuted(UUID target) { return mutes.contains(target); }
		public static void remove(UUID target) {
			if (isMuted(target)) {
				mutes.remove(target);
			}
		}
		/**
		 * Toggles Mute Status for a player.
		 * @param target UUID For Target Player.
		 * @return false if removed, true if added.
		 */
		public static boolean toggle(UUID target) {
			if (isMuted(target)) {
				remove(target);
				return false;
			} else {
				add(target);
				return true;
			}
		}
	}
	
	public static class TeleportRequests {
		private static HashMap<UUID, UUID> requests = new HashMap<UUID, UUID>(); //Target, Requester
		public static void add(UUID target, UUID requester) { requests.put(target, requester); }
		public static boolean pending(UUID target) { return requests.containsKey(target); }
		public static void remove(UUID target) {
			if (pending(target)) {
				requests.remove(target);
			}
		}
		public static UUID fromWho(UUID target) {
			if (pending(target)) {
				return requests.get(target);
			}
			return null;
		}
	}
	
	public static class AfkPlayers {
		private static ArrayList<UUID> afks = new ArrayList<UUID>();
		public static void add(UUID target) { afks.add(target); }
		public static boolean isAfk(UUID target) { return afks.contains(target); }
		public static void remove(UUID target) {
			if (isAfk(target)) {
				afks.remove(target);
			}
		}
		/**
		 * Toggles AFK Status for a player.
		 * @param target UUID For Target Player.
		 * @return false if removed, true if added.
		 */
		public static boolean toggle(UUID target) {
			if (isAfk(target)) {
				remove(target);
				return false;
			} else {
				add(target);
				return true;
			}
		}
	}
}