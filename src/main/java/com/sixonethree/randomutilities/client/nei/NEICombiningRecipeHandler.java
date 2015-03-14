/*package com.sixonethree.randomutilities.client.nei;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.sixonethree.randomutilities.api.ItemCombiningRecipe;
import com.sixonethree.randomutilities.api.ItemCombiningRecipeRegistry;
import com.sixonethree.randomutilities.utility.LogHelper;
import com.sixonethree.randomutilities.utility.Utilities;

public class NEICombiningRecipeHandler extends TemplateRecipeHandler {
	public class CachedCombiningRecipe extends CachedRecipe {
		PositionedStack Ing1;
		PositionedStack Ing2;
		PositionedStack Ing3;
		PositionedStack Output;
		int Size;
		
		public CachedCombiningRecipe(ItemCombiningRecipe recipe) {
			if (recipe.getSize() == 2) {
				Ing1 = new PositionedStack(recipe.getIngredient(0), 26, 10, false);
				Ing2 = new PositionedStack(recipe.getIngredient(1), 68, 10, false);
			} else if (recipe.getSize() == 3) {
				Ing1 = new PositionedStack(recipe.getIngredient(0), 26, 10, false);
				Ing2 = new PositionedStack(recipe.getIngredient(1), 47, 10, false);
				Ing3 = new PositionedStack(recipe.getIngredient(2), 68, 10, false);
			} else {
				LogHelper.warn("-- Random Utilities NEI Has Failed, Sorry --");
			}
			Output = new PositionedStack(recipe.getResult(), 132, 10, false);
			Size = recipe.getSize();
		}
		
		@Override public List<PositionedStack> getIngredients() {
			ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
			if (Ing1 != null) stacks.add(Ing1);
			if (Ing2 != null) stacks.add(Ing2);
			if (Ing3 != null) stacks.add(Ing3);
			return stacks;
		}
		
		@Override public PositionedStack getResult() {
			return Output;
		}
	}
	
	@Override public String getRecipeName() {
		return StatCollector.translateToLocal("gui.randomutilities.text");
	}
	
	@Override public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("randomutilities") && getClass() == NEICombiningRecipeHandler.class) {
			for (ItemCombiningRecipe recipe : ItemCombiningRecipeRegistry.combiningRecipes) {
				if (recipe.getResult() != null) {
					arecipes.add(new CachedCombiningRecipe(recipe));
				}
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	@Override public void loadCraftingRecipes(ItemStack result) {
		for (ItemCombiningRecipe recipe : ItemCombiningRecipeRegistry.combiningRecipes) {
			if (recipe.getResult() != null) {
				if (Utilities.areItemStacksEqualIgnoreNBT(recipe.getResult(), result) && recipe.getResult().getItemDamage() == result.getItemDamage()) {
					arecipes.add(new CachedCombiningRecipe(recipe));
				}
			}
		}
	}
	
	@Override public void loadUsageRecipes(ItemStack ingredient) {
		for (ItemCombiningRecipe recipe : ItemCombiningRecipeRegistry.combiningRecipes) {
			for (ItemStack stack : recipe.getIngredients()) {
				if (Utilities.areItemStacksEqualIgnoreNBT(stack, ingredient) && stack.getItemDamage() == ingredient.getItemDamage()) {
					arecipes.add(new CachedCombiningRecipe(recipe));
					return;
				}
			}
		}
	}
	
	public Point getMouse(int width, int height) {
		Point mousepos = getMousePosition();
		int guiLeft = (width - 176) / 2;
		int guiTop = (height - 166) / 2;
		Point relMouse = new Point(mousepos.x - guiLeft, mousepos.y - guiTop);
		return relMouse;
	}
	
	public int getGuiWidth(GuiRecipe gui) {
		try {
			Field f = gui.getClass().getField("width");
			return (Integer) f.get(gui);
		} catch (NoSuchFieldException e) {
			try {
				Field f = gui.getClass().getField("field_146294_l");
				return (Integer) f.get(gui);
			} catch (Exception e2) {
				return 0;
			}
		} catch (Exception e2) {
			return 0;
		}
	}
	
	public int getGuiHeight(GuiRecipe gui) {
		try {
			Field f = gui.getClass().getField("height");
			return (Integer) f.get(gui);
		} catch (NoSuchFieldException e) {
			try {
				Field f = gui.getClass().getField("field_146295_m");
				return (Integer) f.get(gui);
			} catch (Exception e2) {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override public void drawExtras(int id) {}
	
	@Override public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int id) {
		currenttip.clear(); // Don't show the recipes tooltip where it was.
		return currenttip;
	}
	
	@Override public String getOverlayIdentifier() {
		return "lunchboxfillingrecipes";
	}
	
	@Override public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(90, 32, 22, 16), "randomutilities"));
	}
	
	@Override public String getGuiTexture() {
		return new ResourceLocation("randomutilities", "textures/gui/nei/itemupgrading.png").toString();
	}
	
	public static Point getMousePosition() {
		Dimension size = displaySize();
		Dimension res = displayRes();
		return new Point(Mouse.getX() * size.width / res.width, size.height - Mouse.getY() * size.height / res.height - 1);
	}
	
	public static Dimension displaySize() {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		return new Dimension(res.getScaledWidth(), res.getScaledHeight());
	}
	
	public static Dimension displayRes() {
		Minecraft mc = Minecraft.getMinecraft();
		return new Dimension(mc.displayWidth, mc.displayHeight);
	}
}*/