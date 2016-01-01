package com.sixonethree.randomutilities.common.init;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import com.sixonethree.randomutilities.common.command.CommandAfk;
import com.sixonethree.randomutilities.common.command.CommandBack;
import com.sixonethree.randomutilities.common.command.CommandBurn;
import com.sixonethree.randomutilities.common.command.CommandDelHome;
import com.sixonethree.randomutilities.common.command.CommandDepth;
import com.sixonethree.randomutilities.common.command.CommandEnderChest;
import com.sixonethree.randomutilities.common.command.CommandExtinguish;
import com.sixonethree.randomutilities.common.command.CommandFeed;
import com.sixonethree.randomutilities.common.command.CommandHat;
import com.sixonethree.randomutilities.common.command.CommandHeal;
import com.sixonethree.randomutilities.common.command.CommandHome;
import com.sixonethree.randomutilities.common.command.CommandMore;
import com.sixonethree.randomutilities.common.command.CommandMute;
import com.sixonethree.randomutilities.common.command.CommandPing;
import com.sixonethree.randomutilities.common.command.CommandPvP;
import com.sixonethree.randomutilities.common.command.CommandRepair;
import com.sixonethree.randomutilities.common.command.CommandSetHome;
import com.sixonethree.randomutilities.common.command.CommandSuicide;
import com.sixonethree.randomutilities.common.command.CommandTpAccept;
import com.sixonethree.randomutilities.common.command.CommandTpDeny;
import com.sixonethree.randomutilities.common.command.CommandTpa;
import com.sixonethree.randomutilities.common.command.CommandWhoIs;

public class Commands {
	public static void init(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandAfk());
		event.registerServerCommand(new CommandBack());
		event.registerServerCommand(new CommandBurn());
		event.registerServerCommand(new CommandDelHome());
		event.registerServerCommand(new CommandDepth());
		event.registerServerCommand(new CommandEnderChest());
		event.registerServerCommand(new CommandExtinguish());
		event.registerServerCommand(new CommandFeed());
		event.registerServerCommand(new CommandHat());
		event.registerServerCommand(new CommandHeal());
		event.registerServerCommand(new CommandHome());
		event.registerServerCommand(new CommandMore());
		event.registerServerCommand(new CommandMute());
		event.registerServerCommand(new CommandPing());
		event.registerServerCommand(new CommandPvP());
		event.registerServerCommand(new CommandRepair());
		event.registerServerCommand(new CommandSetHome());
		event.registerServerCommand(new CommandSuicide());
		event.registerServerCommand(new CommandTpa());
		event.registerServerCommand(new CommandTpAccept());
		event.registerServerCommand(new CommandTpDeny());
		event.registerServerCommand(new CommandWhoIs());
	}
}