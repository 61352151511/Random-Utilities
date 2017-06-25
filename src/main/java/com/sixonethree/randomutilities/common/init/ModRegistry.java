package com.sixonethree.randomutilities.common.init;

import com.sixonethree.randomutilities.RandomUtilities;
import com.sixonethree.randomutilities.common.block.BlockContainerBase;
import com.sixonethree.randomutilities.common.block.BlockDisplayTable;
import com.sixonethree.randomutilities.common.block.BlockMagicChest;
import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.common.item.ItemBase;
import com.sixonethree.randomutilities.common.item.ItemCombined;
import com.sixonethree.randomutilities.common.item.ItemHeartCanister;
import com.sixonethree.randomutilities.common.item.ItemLunchbox;
import com.sixonethree.randomutilities.common.item.ItemMagicCard;
import com.sixonethree.randomutilities.common.recipes.RecipesCombinedCombining;
import com.sixonethree.randomutilities.common.recipes.RecipesCombinedCreating;
import com.sixonethree.randomutilities.common.recipes.RecipesCombining;
import com.sixonethree.randomutilities.common.recipes.RecipesLunchboxDyeing;
import com.sixonethree.randomutilities.common.recipes.RecipesLunchboxFeeding;
import com.sixonethree.randomutilities.common.recipes.RecipesUpgrading;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber()
public class ModRegistry {
	public static final BlockContainerBase magicChest = new BlockMagicChest();
	public static final BlockContainerBase displayTable = new BlockDisplayTable();
	
	public static final ItemBase lunchbox = new ItemLunchbox();
	public static final ItemBase heartCanister = new ItemHeartCanister();
	public static final ItemBase combined = new ItemCombined();
	public static final ItemBase magicCard = new ItemMagicCard();
	
	@SubscribeEvent public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		registry.register(magicChest);
		registry.register(displayTable);
		GameRegistry.registerTileEntity(TileEntityMagicChest.class, "TileEntityMagicChest");
		GameRegistry.registerTileEntity(TileEntityDisplayTable.class, "TileEntityDisplayTable");
	}
	
	@SubscribeEvent public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		
		registry.register(lunchbox);
		registry.register(heartCanister);
		registry.register(combined);
		registry.register(magicCard);
		
		registry.register(new ItemBlock(magicChest).setRegistryName(magicChest.getRegistryName()));
		registry.register(new ItemBlock(displayTable).setRegistryName(displayTable.getRegistryName()));
	}
	
	@SubscribeEvent public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		IForgeRegistry<IRecipe> registry = event.getRegistry();
		
		registry.register(new RecipesCombinedCombining());
		registry.register(new RecipesCombinedCreating());
		registry.register(new RecipesCombining());
		registry.register(new RecipesLunchboxDyeing());
		registry.register(new RecipesLunchboxFeeding());
		registry.register(new RecipesUpgrading());
		
		Recipes.init();
	}
	
	@SubscribeEvent public static void registerModels(ModelRegistryEvent event) {
		RandomUtilities.proxy.registerModels();
	}
}