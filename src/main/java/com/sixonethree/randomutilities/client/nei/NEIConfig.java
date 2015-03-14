/*package com.sixonethree.randomutilities.client.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.TemplateRecipeHandler;

//TODO: Clicking on the NEI page where the arrow SHOULD be causes it to show recipes. Change it so that it shows "Recipes"
//TODO: where the arrow is now and that is where the player has to click in order for it to show the recipes. (ALL PAGES)
public class NEIConfig implements IConfigureNEI {
	@Override public void loadConfig() {
		register(new NEICombiningRecipeHandler());
	}
	
	@Override public String getName() {
		return "Random Utilities";
	}
	
	@Override public String getVersion() {
		return "1.0";
	}
	
	private void register(TemplateRecipeHandler handler) {
		API.registerRecipeHandler(handler);
		API.registerUsageHandler(handler);
	}
}*/