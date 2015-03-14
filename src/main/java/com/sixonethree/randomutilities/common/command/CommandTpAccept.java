package com.sixonethree.randomutilities.common.command;

import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.sixonethree.randomutilities.reference.CommandReference.LastLocations;
import com.sixonethree.randomutilities.reference.CommandReference.TeleportRequests;
import com.sixonethree.randomutilities.utility.Location;

public class CommandTpAccept extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() { return 1; }
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean TabCompletesOnlinePlayers() { return false; }
	
	@SuppressWarnings("rawtypes")
	@Override
	public void processCommandPlayer(EntityPlayer player, String[] args) {
		if (TeleportRequests.Pending(player.getUniqueID())) {
			List playerlist = ConfigHandler.playerEntityList;
			Boolean PlayerFound = false;
			for (int i = 0; i < playerlist.size(); ++i) {
				if (((EntityPlayerMP) playerlist.get(i)).getUniqueID().equals(TeleportRequests.FromWho((player.getUniqueID())))) {
					PlayerFound = true;
					EntityPlayerMP teleporter = (EntityPlayerMP) playerlist.get(i);
					EntityPlayerMP teleportto = (EntityPlayerMP) player;
					LastLocations.Set(teleporter, new Location(teleporter));
					if (teleportto.dimension != teleporter.dimension) {
						TransferDimension(teleporter, new Location(teleportto));
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
			TeleportRequests.Remove(player.getUniqueID());
		} else {
			outputMessage(player, "nonetoaccept", true, true);
		}
	}
}