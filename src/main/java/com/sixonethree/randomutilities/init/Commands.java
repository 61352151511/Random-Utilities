package com.sixonethree.randomutilities.init;

import java.util.HashMap;

import net.minecraft.command.ICommand;
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
import com.sixonethree.randomutilities.command.CommandSetBiome;
import com.sixonethree.randomutilities.command.CommandSetHome;
import com.sixonethree.randomutilities.command.CommandSuicide;
import com.sixonethree.randomutilities.command.CommandTpAccept;
import com.sixonethree.randomutilities.command.CommandTpDeny;
import com.sixonethree.randomutilities.command.CommandTpa;
import com.sixonethree.randomutilities.command.CommandWhoIs;

public class Commands {
	public static void init(FMLServerStartingEvent e, HashMap<String, Boolean> EnabledCommands) {
		HashMap<String, ICommand> Commands = new HashMap<String, ICommand>() {
			private static final long serialVersionUID = 868343478939626591L;
			{
				put("Afk", new CommandAfk());
				put("Back", new CommandBack());
				put("Burn", new CommandBurn());
				put("DelHome", new CommandDelHome());
				put("Depth", new CommandDepth());
				put("EnderChest", new CommandEnderChest());
				put("Extinguish", new CommandExtinguish());
				put("Feed", new CommandFeed());
				put("Hat", new CommandHat());
				put("Heal", new CommandHeal());
				put("Home", new CommandHome());
				put("KillPlayer", new CommandKillPlayer());
				put("More", new CommandMore());
				put("Mute", new CommandMute());
				put("Ping", new CommandPing());
				put("PvP", new CommandPvP());
				put("Repair", new CommandRepair());
				put("SetBiome", new CommandSetBiome());
				put("SetHome", new CommandSetHome());
				put("Suicide", new CommandSuicide());
				put("Tpa", new CommandTpa());
				put("TpAccept", new CommandTpAccept());
				put("TpDeny", new CommandTpDeny());
				put("WhoIs", new CommandWhoIs());
			}
		};
		
		for (String CommandName : EnabledCommands.keySet()) {
			if (EnabledCommands.get(CommandName).booleanValue() == true) {
				e.registerServerCommand(Commands.get(CommandName));
			}
		}
	}
}