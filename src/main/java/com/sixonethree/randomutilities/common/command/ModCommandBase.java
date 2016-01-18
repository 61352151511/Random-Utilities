package com.sixonethree.randomutilities.common.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.sixonethree.randomutilities.utility.homewarp.Location;
import com.sixonethree.randomutilities.utility.homewarp.TeleporterHome;

public abstract class ModCommandBase extends CommandBase {
	
	/* Variables */
	
	public static MinecraftServer serverInstance = FMLCommonHandler.instance().getMinecraftServerInstance();
	public static ServerConfigurationManager configHandler = serverInstance.getConfigurationManager();
	public static ICommandManager commandManager = serverInstance.getCommandManager();
	
	public static String noHomeCalled = "homes.message.nohomecalled";
	public static String noWarpCalled = "warps.message.nowarpcalled";
	
	/* One Liners */
	
	public String getLocalBase() {
		return "command." + getCommandName().toLowerCase() + ".";
	}
	
	public void processCommandPlayer(EntityPlayer player, String[] args) throws CommandException {}
	
	public void processCommandConsole(ICommandSender sender, String[] args) {}
	
	public void processCommandBlock(TileEntityCommandBlock block, String[] args) {
		processCommandConsole((ICommandSender) block, args);
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
		return configHandler.canSendCommands(((EntityPlayerMP) player).getGameProfile());
	}
	
	public static IChatComponent colorPlayer(EntityPlayer player) {
		return player.getDisplayName();
	}
	
	public static IChatComponent colorPlayer(ICommandSender sender) {
		return sender.getDisplayName();
	}
	
	public static IChatComponent colorPlayer(EntityLivingBase entity) {
		return entity.getDisplayName();
	}
	
	public static Integer doubleToInt(double d) {
		return Double.valueOf(d).intValue();
	}
	
	@Override public String getCommandName() {
		return this.getClass().getSimpleName().replace("Command", "").toLowerCase();
	}
	
	@Override public boolean isUsernameIndex(String[] par1ArrayOfStr, int par1) {
		return true;
	}
	
	/* Functions */
	
	@Override public String getCommandUsage(ICommandSender sender) {
		if (getUsageType() == 0) {
			return getLocalBase() + "usage";
		} else {
			return "/" + getCommandName();
		}
	}
	
	@Override public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (sender instanceof EntityPlayer) {
			processCommandPlayer((EntityPlayer) sender, args);
		} else if (sender instanceof TileEntityCommandBlock) {
			processCommandBlock((TileEntityCommandBlock) sender, args);
		} else {
			processCommandConsole(sender, args);
		}
	}
	
	@Override public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if (sender instanceof EntityPlayer) {
			return canPlayerUseCommand((EntityPlayer) sender);
		} else if (sender instanceof TileEntityCommandBlock) {
			return canCommandBlockUseCommand((TileEntityCommandBlock) sender);
		} else {
			return canConsoleUseCommand();
		}
	}
	
	@Override public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (tabCompletesOnlinePlayers()) { return args.length >= 1 ? getListOfStringsMatchingLastWord(args, serverInstance.getAllUsernames()) : null; }
		return null;
	}
	
	public void messageAll(String message, boolean Translatable, boolean AppendBase, Object... formatargs) {
		List<?> players = configHandler.playerEntityList;
		for (int i = 0; i < players.size(); ++ i) {
			Object somethin = players.get(i);
			if (somethin instanceof EntityPlayer) {
				outputMessage((ICommandSender) somethin, message, Translatable, true, formatargs);
			}
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
				player.addChatComponentMessage(new ChatComponentText((appendBase ? getLocalBase() : "") + message));
			}
		} else {
			sender.addChatMessage(new ChatComponentText((appendBase ? getLocalBase() : "") + message));
		}
	}
	
	public void outputMessageLocal(ICommandSender sender, String message, boolean AppendBase, Object... formatargs) {
		if (sender instanceof EntityPlayer) {
			((EntityPlayer) sender).addChatComponentMessage(new ChatComponentTranslation((AppendBase ? getLocalBase() : "") + message, formatargs));
		}
	}
	
	/**
	 * Outputs how to properly use the command.
	 * @param sender - Who are we outputting the message to?
	 * @param translatable - Is the message translatable?
	 */
	public void outputUsage(ICommandSender sender, Boolean translatable) {
		outputMessage(sender, getCommandUsage(sender), translatable, false);
	}
	
	public static void transferDimension(EntityPlayerMP player, Location loc) {
		if (player.dimension == 1) {
			player.addChatComponentMessage(new ChatComponentTranslation("command.teleport.inend", new Object[0]));
		} else {
			configHandler.transferPlayerToDimension(player, loc.dimension, new TeleporterHome((WorldServer) player.worldObj, loc.dimension, (int) loc.posX, (int) loc.posY, (int) loc.posZ, 0F, 0F));
			player = configHandler.getPlayerByUsername(player.getName());
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
		
		@Override public IChatComponent getDeathMessage(EntityLivingBase entity) {
			return new ChatComponentTranslation(this.message, colorPlayer(entity));
		}
	}
}