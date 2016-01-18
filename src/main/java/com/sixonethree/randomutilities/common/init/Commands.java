package com.sixonethree.randomutilities.common.init;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import com.sixonethree.randomutilities.common.command.*;
import com.sixonethree.randomutilities.common.command.home.CommandDelHome;
import com.sixonethree.randomutilities.common.command.home.CommandHome;
import com.sixonethree.randomutilities.common.command.home.CommandSetHome;
import com.sixonethree.randomutilities.common.command.warp.CommandDelWarp;
import com.sixonethree.randomutilities.common.command.warp.CommandSetWarp;
import com.sixonethree.randomutilities.common.command.warp.CommandWarp;
import com.sixonethree.randomutilities.common.handler.ConfigurationHandler;

public class Commands {
	public static boolean registered = false;
	public static void init(FMLServerStartingEvent event) {
		if (ConfigurationHandler.isCommandEnabled("Afk")) event.registerServerCommand(new CommandAfk());
		if (ConfigurationHandler.isCommandEnabled("Back")) event.registerServerCommand(new CommandBack());
		if (ConfigurationHandler.isCommandEnabled("Burn")) event.registerServerCommand(new CommandBurn());
		if (ConfigurationHandler.isCommandEnabled("DelHome")) event.registerServerCommand(new CommandDelHome());
		if (ConfigurationHandler.isCommandEnabled("DelWarp")) event.registerServerCommand(new CommandDelWarp());
		if (ConfigurationHandler.isCommandEnabled("Depth")) event.registerServerCommand(new CommandDepth());
		if (ConfigurationHandler.isCommandEnabled("EnderChest")) event.registerServerCommand(new CommandEnderChest());
		if (ConfigurationHandler.isCommandEnabled("Extinguish")) event.registerServerCommand(new CommandExtinguish());
		if (ConfigurationHandler.isCommandEnabled("Feed")) event.registerServerCommand(new CommandFeed());
		if (ConfigurationHandler.isCommandEnabled("Hat")) event.registerServerCommand(new CommandHat());
		if (ConfigurationHandler.isCommandEnabled("Heal")) event.registerServerCommand(new CommandHeal());
		if (ConfigurationHandler.isCommandEnabled("Home")) event.registerServerCommand(new CommandHome());
		if (ConfigurationHandler.isCommandEnabled("More")) event.registerServerCommand(new CommandMore());
		if (ConfigurationHandler.isCommandEnabled("Mute")) event.registerServerCommand(new CommandMute());
		if (ConfigurationHandler.isCommandEnabled("Ping")) event.registerServerCommand(new CommandPing());
		if (ConfigurationHandler.isCommandEnabled("PvP")) event.registerServerCommand(new CommandPvP());
		if (ConfigurationHandler.isCommandEnabled("Repair")) event.registerServerCommand(new CommandRepair());
		if (ConfigurationHandler.isCommandEnabled("SetHome")) event.registerServerCommand(new CommandSetHome());
		if (ConfigurationHandler.isCommandEnabled("SetWarp")) event.registerServerCommand(new CommandSetWarp());
		if (ConfigurationHandler.isCommandEnabled("Suicide")) event.registerServerCommand(new CommandSuicide());
		if (ConfigurationHandler.isCommandEnabled("Tpa")) event.registerServerCommand(new CommandTpa());
		if (ConfigurationHandler.isCommandEnabled("TpAccept")) event.registerServerCommand(new CommandTpAccept());
		if (ConfigurationHandler.isCommandEnabled("TpDeny")) event.registerServerCommand(new CommandTpDeny());
		if (ConfigurationHandler.isCommandEnabled("Warp")) event.registerServerCommand(new CommandWarp());
		if (ConfigurationHandler.isCommandEnabled("WhoIs")) event.registerServerCommand(new CommandWhoIs());
	}
}