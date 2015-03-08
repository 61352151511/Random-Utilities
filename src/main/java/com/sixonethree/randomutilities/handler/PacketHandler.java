package com.sixonethree.randomutilities.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.EnumMap;

import com.sixonethree.randomutilities.RandomUtilities;
import com.sixonethree.randomutilities.block.tile.TileEntityMagicChest;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public enum PacketHandler {
	INSTANCE;
	
	private EnumMap<Side, FMLEmbeddedChannel> channels;
	
	private PacketHandler() {
		this.channels = NetworkRegistry.INSTANCE.newChannel("RandomUtilities", new RandomUtilitiesCodec());
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			addClientHandler();
		}
	}
	
	@SideOnly(Side.CLIENT) private void addClientHandler() {
		FMLEmbeddedChannel clientChannel = this.channels.get(Side.CLIENT);
		String codec = clientChannel.findChannelHandlerNameForType(RandomUtilitiesCodec.class);
		clientChannel.pipeline().addAfter(codec, "ClientHandler", new RandomUtilitiesMessageHandler());
	}
	
	private static class RandomUtilitiesMessageHandler extends SimpleChannelInboundHandler<RandomUtilitiesMessage> {
		@Override protected void channelRead0(ChannelHandlerContext ctx, RandomUtilitiesMessage msg) throws Exception {
			World world = RandomUtilities.proxy.getClientWorld();
			TileEntity te = world.getTileEntity(msg.x, msg.y, msg.z);
			if (te instanceof TileEntityMagicChest) {
				TileEntityMagicChest icte = (TileEntityMagicChest) te;
				icte.setFacing(msg.facing);
				icte.handlePacketData(msg.itemStacks);
			}
		}
	}
	
	public static class RandomUtilitiesMessage {
		int x;
		int y;
		int z;
		int facing;
		ItemStack[] itemStacks;
	}
	
	private class RandomUtilitiesCodec extends FMLIndexedMessageToMessageCodec<RandomUtilitiesMessage> {
		public RandomUtilitiesCodec() {
			addDiscriminator(0, RandomUtilitiesMessage.class);
		}
		
		@Override public void encodeInto(ChannelHandlerContext ctx, RandomUtilitiesMessage msg, ByteBuf target) throws Exception {
			target.writeInt(msg.x);
			target.writeInt(msg.y);
			target.writeInt(msg.z);
			target.writeInt(msg.facing);
			ByteBufUtils.writeItemStack(target, msg.itemStacks[0]);
		}
		
		@Override public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, RandomUtilitiesMessage msg) {
			msg.x = dat.readInt();
			msg.y = dat.readInt();
			msg.z = dat.readInt();
			msg.facing = dat.readInt();
			msg.itemStacks = new ItemStack[] {ByteBufUtils.readItemStack(dat)};
		}
	}
	
	public static Packet getPacket(TileEntityMagicChest temc) {
		RandomUtilitiesMessage msg = new RandomUtilitiesMessage();
		msg.x = temc.xCoord;
		msg.y = temc.yCoord;
		msg.z = temc.zCoord;
		msg.facing = temc.getFacing();
		msg.itemStacks = new ItemStack[] {temc.getStackInSlot(0)};
		return INSTANCE.channels.get(Side.SERVER).generatePacketFrom(msg);
	}
}