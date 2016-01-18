package com.sixonethree.randomutilities.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import com.sixonethree.randomutilities.common.handler.ConfigurationHandler;
import com.sixonethree.randomutilities.reference.Reference;

public class ModGuiConfig extends GuiConfig {
	static List<IConfigElement> configElements = new ArrayList<IConfigElement>();
	{
		configElements.addAll(new ConfigElement(ConfigurationHandler.configuration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
		configElements.addAll(new ConfigElement(ConfigurationHandler.configuration.getCategory(ConfigurationHandler.CATEGORY_COMMANDS)).getChildElements());
	}
	
	public ModGuiConfig(GuiScreen guiScreen) {
		super(guiScreen, configElements, Reference.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString()));
	}
}