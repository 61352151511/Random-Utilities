package com.sixonethree.randomutilities.common.handler;

import java.io.File;
import java.util.HashMap;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sixonethree.randomutilities.reference.Reference;

public class ConfigurationHandler {
	public static Configuration configuration;
	public static final String CATEGORY_COMMANDS = "Commands";
	private static HashMap<String, Boolean> enabledCommands = new HashMap<String, Boolean>();
	
	public static void init(File configFile) {
		if (configuration == null) {
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	@SubscribeEvent public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		enabledCommands.clear();
		enabledCommands.put("Afk", configuration.getBoolean("Afk", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("Back", configuration.getBoolean("Back", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("Burn", configuration.getBoolean("Burn", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("DelHome", configuration.getBoolean("DelHome", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("DelWarp", configuration.getBoolean("DelWarp", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("Depth", configuration.getBoolean("Depth", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("EnderChest", configuration.getBoolean("EnderChest", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("Extinguish", configuration.getBoolean("Extinguish", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("Feed", configuration.getBoolean("Feed", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("Hat", configuration.getBoolean("Hat", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("Heal", configuration.getBoolean("Heal", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("Home", configuration.getBoolean("Home", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("More", configuration.getBoolean("More", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("Ping", configuration.getBoolean("Ping", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("PvP", configuration.getBoolean("PvP", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("Repair", configuration.getBoolean("Repair", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("SetHome", configuration.getBoolean("SetHome", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("SetWarp", configuration.getBoolean("SetWarp", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("Suicide", configuration.getBoolean("Suicide", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("Tpa", configuration.getBoolean("Tpa", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("TpAccept", configuration.getBoolean("TpAccept", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("TpDeny", configuration.getBoolean("TpDeny", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("Warp", configuration.getBoolean("Warp", CATEGORY_COMMANDS, true, ""));
		enabledCommands.put("WhoIs", configuration.getBoolean("WhoIs", CATEGORY_COMMANDS, true, ""));
		if (configuration.hasChanged()) configuration.save();
	}
	
	public static boolean isCommandEnabled(String command) {
		if (enabledCommands.containsKey(command)) {
			return enabledCommands.get(command).booleanValue();
		}
		return false;
	}
}