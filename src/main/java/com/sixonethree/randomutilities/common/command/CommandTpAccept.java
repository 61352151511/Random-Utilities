package com.sixonethree.randomutilities.common.command;

import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.sixonethree.randomutilities.reference.CommandReference.LastLocations;
import com.sixonethree.randomutilities.reference.CommandReference.TeleportRequests;
import com.sixonethree.randomutilities.utility.Location;

public class CommandTpAccept extends ModCommandBase implements ICommand {
	@Override public void processCommandPlayer(EntityPlayer player, String[] args) {
		if (TeleportRequests.pending(player.getUniqueID())) {
			List<EntityPlayerMP> playerlist = configHandler.playerEntityList;
			Boolean playerFound = false;
			for (int i = 0; i < playerlist.size(); ++ i) {
				if (playerlist.get(i).getUniqueID().equals(TeleportRequests.fromWho((player.getUniqueID())))) {
					playerFound = true;
					EntityPlayerMP teleporter = playerlist.get(i);
					EntityPlayerMP teleportTo = (EntityPlayerMP) player;
					LastLocations.set(teleporter, new Location(teleporter));
					if (teleportTo.dimension != teleporter.dimension) {
						transferDimension(teleporter, new Location(teleportTo));
					} else {
						outputMessage(teleporter, "gotaccepted", true, true);
						outputMessage(teleportTo, "youaccepted", true, true);
						teleporter.setPositionAndUpdate(teleportTo.posX, teleportTo.posY, teleportTo.posZ);
					}
				}
			}
			if (!playerFound) {
				outputMessage(player, "notonline", true, true);
			}
			TeleportRequests.remove(player.getUniqueID());
		} else {
			outputMessage(player, "nonetoaccept", true, true);
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 1; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}