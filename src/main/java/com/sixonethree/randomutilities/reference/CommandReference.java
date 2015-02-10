package com.sixonethree.randomutilities.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.sixonethree.randomutilities.utility.Location;

import net.minecraft.entity.player.EntityPlayerMP;

public class CommandReference {
	public static class LastLocations {
		private static HashMap<UUID, Location> LastPlaces = new HashMap<UUID, Location>();
		public static void Set(EntityPlayerMP player, Location loc) {
			LastPlaces.put(player.getUniqueID(), loc);
		}
		
		public static void Set(EntityPlayerMP player, double X, double Y, double Z, int dim) {
			LastPlaces.put(player.getUniqueID(), new Location(X, Y, Z, dim));
		}
		
		public static Location Get(EntityPlayerMP player) {
			UUID puid = player.getUniqueID();
			if (LastPlaces.containsKey(puid)) {
				return LastPlaces.get(puid);
			}
			return null;
		}
	}
	
	public static class MutedPlayers {
		private static ArrayList<UUID> Mutes = new ArrayList<UUID>();
		public static void Add(UUID Target) { Mutes.add(Target); }
		public static boolean isMuted(UUID Target) { return Mutes.contains(Target); }
		public static void Remove(UUID Target) {
			if (isMuted(Target)) {
				Mutes.remove(Target);
			}
		}
		/**
		 * Toggles Mute Status for a player.
		 * @param Target UUID For Target Player.
		 * @return false if removed, true if added.
		 */
		public static boolean Toggle(UUID Target) {
			if (isMuted(Target)) {
				Remove(Target);
				return false;
			} else {
				Add(Target);
				return true;
			}
		}
	}
	
	public static class TeleportRequests {
		private static HashMap<UUID, UUID> Requests = new HashMap<UUID, UUID>(); //Target, Requester
		public static void Add(UUID Target, UUID Requester) { Requests.put(Target, Requester); }
		public static boolean Pending(UUID Target) { return Requests.containsKey(Target); }
		public static void Remove(UUID Target) {
			if (Pending(Target)) {
				Requests.remove(Target);
			}
		}
		public static UUID FromWho(UUID Target) {
			if (Pending(Target)) {
				return Requests.get(Target);
			}
			return null;
		}
	}
	
	public static class AfkPlayers {
		private static ArrayList<UUID> Afks = new ArrayList<UUID>();
		public static void Add(UUID Target) { Afks.add(Target); }
		public static boolean isAfk(UUID Target) { return Afks.contains(Target); }
		public static void Remove(UUID Target) {
			if (isAfk(Target)) {
				Afks.remove(Target);
			}
		}
		/**
		 * Toggles AFK Status for a player.
		 * @param Target UUID For Target Player.
		 * @return false if removed, true if added.
		 */
		public static boolean Toggle(UUID Target) {
			if (isAfk(Target)) {
				Remove(Target);
				return false;
			} else {
				Add(Target);
				return true;
			}
		}
	}
}