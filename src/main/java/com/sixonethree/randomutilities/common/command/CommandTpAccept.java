package com.sixonethree.randomutilities.common.command;

import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.sixonethree.randomutilities.reference.CommandReference.LastLocations;
import com.sixonethree.randomutilities.reference.CommandReference.TeleportRequests;
import com.sixonethree.randomutilities.utility.Location;

public class CommandTpAccept extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() {
		return 1;
	}
	
	@Override public boolean canConsoleUseCommand() {
		return false;
	}
	
	@Override public boolean isOpOnly() {
		return false;
	}
	
	@Override public boolean tabCompletesOnlinePlayers() {
		return false;
	}
	
	@SuppressWarnings("rawtypes") @Override public void processCommandPlayer(EntityPlayer player, String[] args) {
		if (TeleportRequests.pending(player.getUniqueID())) {
			List playerlist = configHandler.playerEntityList;
			Boolean PlayerFound = false;
			for (int i = 0; i < playerlist.size(); ++ i) {
				if (((EntityPlayerMP) playerlist.get(i)).getUniqueID().equals(TeleportRequests.fromWho((player.getUniqueID())))) {
					PlayerFound = true;
					EntityPlayerMP teleporter = (EntityPlayerMP) playerlist.get(i);
					EntityPlayerMP teleportto = (EntityPlayerMP) player;
					LastLocations.set(teleporter, new Location(teleporter));
					if (teleportto.dimension != teleporter.dimension) {
						transferDimension(teleporter, new Location(teleportto));
					} else {
						outputMessage(teleporter, "gotaccepted", true, true);
						outputMessage(teleportto, "youaccepted", true, true);
						teleporter.setPositionAndUpdate(teleportto.posX, teleportto.posY, teleportto.posZ);
					}
				}
			}
			if (!PlayerFound) {
				outputMessage(player, "notonline", true, true);
			}
			TeleportRequests.remove(player.getUniqueID());
		} else {
			outputMessage(player, "nonetoaccept", true, true);
		}
	}
}