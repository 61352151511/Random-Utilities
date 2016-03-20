package com.sixonethree.randomutilities.common.event;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sixonethree.randomutilities.reference.CommandReference.AfkPlayers;
import com.sixonethree.randomutilities.utility.homewarp.HomePoint;
import com.sixonethree.randomutilities.utility.homewarp.SaveFile;
import com.sixonethree.randomutilities.utility.homewarp.WarpPoint;

public class PlayerEvents {
	private static ITextComponent colorPlayer(EntityPlayer player) {
		return player.getDisplayName();
	}
	
	private static void messageAll(String message, Object... formatargs) {
		List<EntityPlayerMP> players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerList();
		for (int i = 0; i < players.size(); i ++) {
			EntityPlayerMP player = players.get(i);
			player.addChatComponentMessage(new TextComponentTranslation(message, formatargs));
		}
	}
	
	@SubscribeEvent public void onPlayerInteract(PlayerInteractEvent event) {
		EntityPlayer player = event.entityPlayer;
		if (AfkPlayers.isAfk(player.getUniqueID())) {
			AfkPlayers.remove(player.getUniqueID());
			messageAll("command.afk.notafk", colorPlayer(player));
		}
	}
	
	@SubscribeEvent public void onPlayerLoadFromFileEvent(PlayerEvent.LoadFromFile event) {
		HomePoint.homesSaveFile = new SaveFile("/homes.txt", event.playerDirectory.getParent());
		WarpPoint.warpsSaveFile = new SaveFile("/warps.txt", event.playerDirectory.getParent());
		HomePoint.loadAll();
		WarpPoint.loadAll();
	}
	
	@SubscribeEvent public void onPlayerSaveToFileEvent(PlayerEvent.SaveToFile event) {
		HomePoint.saveAll();
		WarpPoint.saveAll();
	}
}