package com.sixonethree.randomutilities.common.event;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sixonethree.randomutilities.common.init.ModItems;
import com.sixonethree.randomutilities.reference.CommandReference.AfkPlayers;
import com.sixonethree.randomutilities.utility.homewarp.HomePoint;
import com.sixonethree.randomutilities.utility.homewarp.SaveFile;
import com.sixonethree.randomutilities.utility.homewarp.WarpPoint;

public class PlayerEvents {
	
	/* Methods */
	
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
	
	/* Events */
	
	@SubscribeEvent public void onItemDrop(ItemTossEvent event) {
		if (!event.getEntity().worldObj.isRemote) {
			Entity ent = event.getEntity();
			if (ent instanceof EntityItem) {
				EntityItem drop = (EntityItem) ent;
				if (drop.getEntityItem() != null) {
					if (drop.getEntityItem().getItem() != null) {
						if (drop.getEntityItem().getItem() == Items.PAPER) {
							boolean switchItem = false;
							for (int x = -1; x <= 1; x ++) {
								for (int y = -1; y <= 1; y ++) {
									for (int z = -1; z <= 1; z ++) {
										if (event.getPlayer().worldObj.getBlockState(new BlockPos(((int) Math.round(event.getPlayer().posX)) + x, ((int) Math.round(event.getPlayer().posY)) + y, ((int) Math.round(event.getPlayer().posZ)) + z)) == Blocks.ENCHANTING_TABLE) {
											switchItem = true;
											break;
										}
									}
								}
							}
							if (switchItem) {
								drop.setEntityItemStack(new ItemStack(ModItems.MAGIC_CARD));
							}
						}
					}
				}
			}
		}
    }
	
	@SubscribeEvent public void onPlayerInteract(PlayerInteractEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		if (AfkPlayers.isAfk(player.getUniqueID())) {
			AfkPlayers.remove(player.getUniqueID());
			messageAll("command.afk.notafk", colorPlayer(player));
		}
	}
	
	@SubscribeEvent public void onPlayerLoadFromFileEvent(PlayerEvent.LoadFromFile event) {
		HomePoint.homesSaveFile = new SaveFile("/homes.txt", event.getPlayerDirectory().getParent());
		WarpPoint.warpsSaveFile = new SaveFile("/warps.txt", event.getPlayerDirectory().getParent());
		HomePoint.loadAll();
		WarpPoint.loadAll();
	}
	
	@SubscribeEvent public void onPlayerSaveToFileEvent(PlayerEvent.SaveToFile event) {
		HomePoint.saveAll();
		WarpPoint.saveAll();
	}
}