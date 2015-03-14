package com.sixonethree.randomutilities.common.event;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sixonethree.randomutilities.reference.CommandReference.AfkPlayers;
import com.sixonethree.randomutilities.utility.HomePoint;
import com.sixonethree.randomutilities.utility.SaveFile;

public class PlayerEvents {
	
	private static void messageAll(String message, Object...formatargs) {
		List<?> players = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
		for (int i = 0; i < players.size(); i++) {
			Object something = players.get(i);
			if (something instanceof EntityPlayer) {
				((EntityPlayer) something).addChatComponentMessage(new ChatComponentTranslation(message, formatargs));
			}
		}
	}
	
	private static IChatComponent ColorPlayer(EntityPlayer player) { return player.getDisplayName(); }
	
	@SubscribeEvent
	public void onPlayerLoadFromFileEvent(PlayerEvent.LoadFromFile event) {
		HomePoint.homesSaveFile = new SaveFile("/homes.txt", event.playerDirectory.getParent());
		HomePoint.loadAll();
	}
	
	@SubscribeEvent
	public void onPlayerSaveToFileEvent(PlayerEvent.SaveToFile event) {
		HomePoint.saveAll();
	}
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		EntityPlayer player = event.entityPlayer;
		if (AfkPlayers.isAfk(player.getUniqueID())) {
			AfkPlayers.Remove(player.getUniqueID());
			messageAll("command.afk.notafk", ColorPlayer(player));
		}
	}
}