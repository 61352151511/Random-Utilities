package com.sixonethree.randomutilities.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class CommandSetBiome extends ModCommandBase implements ICommand {
	@SuppressWarnings("deprecation") ArrayList<BiomeDictionary.Type> AllTypes = new ArrayList<BiomeDictionary.Type>() {
		private static final long serialVersionUID = -8115509882760535247L;
		{
			add(Type.BEACH);
			add(Type.COLD);
			add(Type.CONIFEROUS);
			add(Type.DEAD);
			add(Type.DENSE);
			add(Type.DESERT);
			add(Type.DRY);
			add(Type.END);
			add(Type.FOREST);
			add(Type.FROZEN);
			add(Type.HILLS);
			add(Type.HOT);
			add(Type.JUNGLE);
			add(Type.LUSH);
			add(Type.MAGICAL);
			add(Type.MESA);
			add(Type.MOUNTAIN);
			add(Type.MUSHROOM);
			add(Type.NETHER);
			add(Type.OCEAN);
			add(Type.PLAINS);
			add(Type.RIVER);
			add(Type.SANDY);
			add(Type.SAVANNA);
			add(Type.SNOWY);
			add(Type.SPARSE);
			add(Type.SPOOKY);
			add(Type.SWAMP);
			add(Type.WASTELAND);
			add(Type.WATER);
			add(Type.WET);
		}
	};
	
	@Override public int getUsageType() { return 0; }
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean TabCompletesOnlinePlayers() { return false; }
	
	@Override
	public void executeCommandPlayer(EntityPlayer player, String[] args) {
		if (args.length >= 1) {
			String biome = args[0];
			int range = 0;
			if (args.length >= 2) {
				try {
					range = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {}
			}
			int newBiomeID = -1;
			String newBiomeName = "";
			for (Type bt : AllTypes) {
				for (BiomeGenBase e : BiomeDictionary.getBiomesForType(bt)) {
					if (e.biomeName.replace(" ", "").equalsIgnoreCase(biome)) {
						newBiomeID = e.biomeID;
						newBiomeName = e.biomeName.replace(" ", "");
						break;
					}
				}
			}
			if (newBiomeID == -1) {
				outputMessage(player, "invalidbiome", true, true);
				return;
			}
			for (int x = range * -1; x <= range; x ++) { // This is inefficient, determine whether or not the blocks are in the same biome and then set it
				// Rather than setting the biome array multiple times
				for (int z = range * -1; z <= range; z ++) {
					byte[] biomeChanged = new byte[256];
					byte[] oldBiome = player.worldObj.getChunkFromBlockCoords(new BlockPos((int) player.posX + x, 0, (int) player.posZ + z)).getBiomeArray();
					int chunkX = Math.abs(((int) player.posX + x) % 16);
					int chunkZ = Math.abs(((int) player.posZ + z) % 16);
					if ((int) player.posX + x < 0) chunkX = 15 - chunkX; // Figure out why 15's don't get set
					if ((int) player.posZ + z < 0) chunkZ = 15 - chunkZ;
					int Count = 0;
					for (int z2 = 0; z2 < 16; z2 ++) {
						for (int x2 = 0; x2 < 16; x2 ++) {
							if (x2 == chunkX && z2 == chunkZ) {
								biomeChanged[Count] = Byte.valueOf(String.valueOf(newBiomeID));
							} else {
								biomeChanged[Count] = oldBiome[Count];
							}
							Count ++;
						}
					}
					player.worldObj.getChunkFromBlockCoords(new BlockPos((int) player.posX + x, 0, (int) player.posZ + z)).setBiomeArray(biomeChanged);
					player.worldObj.getChunkFromBlockCoords(new BlockPos((int) player.posX + x, 0, (int) player.posZ + z)).setChunkModified();
				}
			}
			outputMessageLocal(player, "biomeset", true, newBiomeName);
		} else {
			outputMessage(player, "providebiome", true, true);
		}
	}
	
	@Override public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		List<String> retList = new ArrayList<String>();
		for (Type bt : AllTypes) {
			for (BiomeGenBase e : BiomeDictionary.getBiomesForType(bt)) {
				if (args.length > 0) {
					if (e.biomeName.replace(" ", "").toLowerCase().startsWith(args[0].toLowerCase())) {
						if (!retList.contains(e.biomeName.replace(" ", ""))) {
							retList.add(e.biomeName.replace(" ", ""));
						}
					}
				} else {
					if (!retList.contains(e.biomeName.replace(" ", ""))) {
						retList.add(e.biomeName.replace(" ", ""));
					}
				}
			}
		}
		return retList;
	}
}
