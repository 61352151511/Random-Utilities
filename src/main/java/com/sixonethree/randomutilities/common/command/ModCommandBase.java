package com.sixonethree.randomutilities.common.command;

import java.util.List;

import com.sixonethree.randomutilities.utility.homewarp.Location;
import com.sixonethree.randomutilities.utility.homewarp.TeleporterHome;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public abstract class ModCommandBase extends CommandBase {
	
	/* Variables */
	
	public static MinecraftServer serverInstance = FMLCommonHandler.instance().getMinecraftServerInstance();
	public static PlayerList playerList = serverInstance.getPlayerList();
	public static ICommandManager commandManager = serverInstance.getCommandManager();
	
	public static String noHomeCalled = "homes.message.nohomecalled";
	public static String noWarpCalled = "warps.message.nowarpcalled";
	
	/* One Liners */
	
	public String getLocalBase() {
		return "command." + getName().toLowerCase() + ".";
	}
	
	public void executeCommandPlayer(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException {}
	
	public void executeCommandConsole(MinecraftServer server, ICommandSender sender, String[] args) {}
	
	public void executeCommandBlock(MinecraftServer server, TileEntityCommandBlock block, String[] args) {
		executeCommandConsole(server, (ICommandSender) block, args);
	}
	
	/**
	 * Determines if the console can use this command.
	 * @return Can the console use the command?
	 */
	public abstract boolean canConsoleUseCommand();
	/**
	 * Determines if this command is for server operators only.
	 * @return True if op only, false if anyone can use it.
	 */
	public abstract boolean isOpOnly();
	/**
	 * Determines if pressing the tab key fills out player names for arguments.
	 * @return True if pressing tab fills out online players.
	 */
	public abstract boolean tabCompletesOnlinePlayers();
	/**
	 * The usage type.
	 * @return 0 for "command.commandname.usage" or 1 for /commandname
	 */
	public abstract int getUsageType(); // 0 = command.<Command Name>.usage || 1
										// = /<Command Name>
	
	public boolean canCommandBlockUseCommand(TileEntityCommandBlock block) {
		return canConsoleUseCommand();
	}
	
	public boolean canPlayerUseCommand(EntityPlayer player) {
		return isOpOnly() ? checkOp(player) : true;
	}
	
	public static boolean checkOp(EntityPlayer player) {
		return playerList.canSendCommands(((EntityPlayerMP) player).getGameProfile());
	}
	
	public static ITextComponent colorPlayer(EntityPlayer player) {
		return player.getDisplayName();
	}
	
	public static ITextComponent colorPlayer(ICommandSender sender) {
		return sender.getDisplayName();
	}
	
	public static ITextComponent colorPlayer(EntityLivingBase entity) {
		return entity.getDisplayName();
	}
	
	public static Integer doubleToInt(double d) {
		return Double.valueOf(d).intValue();
	}
	
	@Override public String getName() {
		return this.getClass().getSimpleName().replace("Command", "").toLowerCase();
	}
	
	@Override public boolean isUsernameIndex(String[] par1ArrayOfStr, int par1) {
		return true;
	}
	
	/* Functions */
	
	@Override public String getUsage(ICommandSender sender) {
		if (getUsageType() == 0) {
			return getLocalBase() + "usage";
		} else {
			return "/" + getName();
		}
	}
	
	@Override public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (sender instanceof EntityPlayer) {
			executeCommandPlayer(server, (EntityPlayer) sender, args);
		} else if (sender instanceof TileEntityCommandBlock) {
			executeCommandBlock(server, (TileEntityCommandBlock) sender, args);
		} else {
			executeCommandConsole(server, sender, args);
		}
	}
	
	@Override public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		if (sender instanceof EntityPlayer) {
			return canPlayerUseCommand((EntityPlayer) sender);
		} else if (sender instanceof TileEntityCommandBlock) {
			return canCommandBlockUseCommand((TileEntityCommandBlock) sender);
		} else {
			return canConsoleUseCommand();
		}
	}
	
	@Override public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
		if (tabCompletesOnlinePlayers()) { return args.length >= 1 ? getListOfStringsMatchingLastWord(args, serverInstance.getOnlinePlayerNames()) : null; }
		return null;
	}
	
	public void messageAll(String message, boolean Translatable, boolean AppendBase, Object... formatargs) {
		List<EntityPlayerMP> players = playerList.getPlayers();
		for (int i = 0; i < players.size(); ++ i) {
			EntityPlayerMP player = players.get(i);
			outputMessage((ICommandSender) player, message, Translatable, true, formatargs);
		}
	}
	
	/**
	 * Outputs a message to an ICommandSender
	 * @param sender - Who to send the message to
	 * @param message - The message to send
	 * @param translatable - Whether the message is translatable or not
	 * @param appendBase - Append the command base?
	 * @param formatargs - Translation arguments.
	 */
	public void outputMessage(ICommandSender sender, String message, boolean translatable, boolean appendBase, Object... formatargs) {
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;
			if (translatable) {
				outputMessageLocal(sender, message, appendBase, formatargs);
			} else {
				player.sendMessage(new TextComponentString((appendBase ? getLocalBase() : "") + message));
			}
		} else {
			sender.sendMessage(new TextComponentString((appendBase ? getLocalBase() : "") + message));
		}
	}
	
	public void outputMessageLocal(ICommandSender sender, String message, boolean appendBase, Object... formatargs) {
		if (sender instanceof EntityPlayer) {
			((EntityPlayer) sender).sendMessage(new TextComponentTranslation((appendBase ? getLocalBase() : "") + message, formatargs));
		}
	}
	
	/**
	 * Outputs how to properly use the command.
	 * @param sender - Who are we outputting the message to?
	 * @param translatable - Is the message translatable?
	 */
	public void outputUsage(ICommandSender sender, Boolean translatable) {
		outputMessage(sender, getUsage(sender), translatable, false);
	}
	
	public static void transferDimension(EntityPlayerMP player, Location loc) {
		if (player.dimension == 1) {
			player.sendMessage(new TextComponentTranslation("command.teleport.inend", new Object[0]));
		} else {
			playerList.transferPlayerToDimension(player, loc.dimension, new TeleporterHome((WorldServer) player.world, loc.dimension, (int) loc.posX, (int) loc.posY, (int) loc.posZ, 0F, 0F));
			player = playerList.getPlayerByUsername(player.getName());
		}
	}
	
	public static class DamageSourceCustom extends DamageSource {
		String message;
		
		public DamageSourceCustom(String type, String deathmessage) {
			super(type);
			this.setDamageAllowedInCreativeMode();
			this.setDamageBypassesArmor();
			message = deathmessage;
		}
		
		@Override public ITextComponent getDeathMessage(EntityLivingBase entity) {
			return new TextComponentTranslation(this.message, colorPlayer(entity));
		}
	}
}