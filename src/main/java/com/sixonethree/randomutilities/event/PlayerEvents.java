package com.sixonethree.randomutilities.event;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import com.sixonethree.randomutilities.block.BlockMagicChest;
import com.sixonethree.randomutilities.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.init.ModItems;
import com.sixonethree.randomutilities.reference.CommandReference.AfkPlayers;
import com.sixonethree.randomutilities.utility.HomePoint;
import com.sixonethree.randomutilities.utility.SaveFile;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

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
	
	private static IChatComponent ColorPlayer(EntityPlayer player) { return player.getFormattedCommandSenderName(); }
	
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
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		if (event.block instanceof BlockMagicChest) {
			if (event.world.getTileEntity(event.x, event.y, event.z) instanceof TileEntityMagicChest) {
				if (((TileEntityMagicChest) event.world.getTileEntity(event.x, event.y, event.z)).getOwner().equals(event.getPlayer().getUniqueID().toString())) {
				} else {
					if (!FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().canSendCommands(event.getPlayer().getGameProfile()))
					event.setCanceled(true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onItemDrop(ItemTossEvent event) {
		if (!event.entity.worldObj.isRemote) {
			Entity ent = event.entity;
			if (ent instanceof EntityItem) {
				EntityItem drop = (EntityItem) ent;
				if (drop.getEntityItem() != null) {
					if (drop.getEntityItem().getItem() != null) {
						if (drop.getEntityItem().getItem() == Items.paper) {
							boolean switchItem = false;
							for (int x = -1; x <= 1; x ++) {
								for (int y = -1; y <= 1; y ++) {
									for (int z = -1; z <= 1; z ++) {
										if (event.player.worldObj.getBlock(((int) Math.round(event.player.posX)) + x, ((int) Math.round(event.player.posY)) + y, ((int) Math.round(event.player.posZ)) + z) == Blocks.enchanting_table) {
											switchItem = true;
											break;
										}
									}
								}
							}
							if (switchItem) {
								drop.getEntityItem().setItem(ModItems.magicCard);
							}
						}
					}
				}
			}
		}
	}
}