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
	public static void init(FMLServerStartingEvent e) {
		e.registerServerCommand(new CommandAfk());
		e.registerServerCommand(new CommandBack());
		e.registerServerCommand(new CommandBurn());
		e.registerServerCommand(new CommandDelHome());
		e.registerServerCommand(new CommandDepth());
		e.registerServerCommand(new CommandEnderChest());
		e.registerServerCommand(new CommandExtinguish());
		e.registerServerCommand(new CommandFeed());
		e.registerServerCommand(new CommandHat());
		e.registerServerCommand(new CommandHeal());
		e.registerServerCommand(new CommandHome());
		e.registerServerCommand(new CommandMore());
		e.registerServerCommand(new CommandMute());
		e.registerServerCommand(new CommandPing());
		e.registerServerCommand(new CommandPvP());
		e.registerServerCommand(new CommandRepair());
		e.registerServerCommand(new CommandSetHome());
		e.registerServerCommand(new CommandSuicide());
		e.registerServerCommand(new CommandTpa());
		e.registerServerCommand(new CommandTpAccept());
		e.registerServerCommand(new CommandTpDeny());
		e.registerServerCommand(new CommandWhoIs());
	}
}