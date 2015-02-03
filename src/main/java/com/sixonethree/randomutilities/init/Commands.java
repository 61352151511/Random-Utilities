package com.sixonethree.randomutilities.init;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import com.sixonethree.randomutilities.command.CommandAfk;
import com.sixonethree.randomutilities.command.CommandBack;
import com.sixonethree.randomutilities.command.CommandBurn;
import com.sixonethree.randomutilities.command.CommandDelHome;
import com.sixonethree.randomutilities.command.CommandDepth;
import com.sixonethree.randomutilities.command.CommandEnderChest;
import com.sixonethree.randomutilities.command.CommandExtinguish;
import com.sixonethree.randomutilities.command.CommandFeed;
import com.sixonethree.randomutilities.command.CommandHat;
import com.sixonethree.randomutilities.command.CommandHeal;
import com.sixonethree.randomutilities.command.CommandHome;
import com.sixonethree.randomutilities.command.CommandKillPlayer;
import com.sixonethree.randomutilities.command.CommandMore;
import com.sixonethree.randomutilities.command.CommandMute;
import com.sixonethree.randomutilities.command.CommandPing;
import com.sixonethree.randomutilities.command.CommandPvP;
import com.sixonethree.randomutilities.command.CommandRepair;
import com.sixonethree.randomutilities.command.CommandSetHome;
import com.sixonethree.randomutilities.command.CommandSuicide;
import com.sixonethree.randomutilities.command.CommandTpAccept;
import com.sixonethree.randomutilities.command.CommandTpDeny;
import com.sixonethree.randomutilities.command.CommandTpa;
import com.sixonethree.randomutilities.command.CommandWhoIs;

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
		e.registerServerCommand(new CommandKillPlayer());
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