package com.sixonethree.randomutilities.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.EnumMap;

import com.sixonethree.randomutilities.RandomUtilities;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLIndexedMessageToMessageCodec;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
			if (world != null) {
				TileEntity te = world.getTileEntity(new BlockPos(msg.x, msg.y, msg.z));
				if (te instanceof TileEntityMagicChest) {
					TileEntityMagicChest icte = (TileEntityMagicChest) te;
					icte.setFacing(msg.facing);
					icte.handlePacketData(msg.itemStacks);
				}
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
			ByteBufUtils.writeItemStack(target, msg.itemStacks[1]);
		}
		
		@Override public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, RandomUtilitiesMessage msg) {
			msg.x = dat.readInt();
			msg.y = dat.readInt();
			msg.z = dat.readInt();
			msg.facing = dat.readInt();
			msg.itemStacks = new ItemStack[] {ByteBufUtils.readItemStack(dat), ByteBufUtils.readItemStack(dat)};
		}
	}
	
	public static Packet getPacket(TileEntityMagicChest temc) {
		RandomUtilitiesMessage msg = new RandomUtilitiesMessage();
		msg.x = temc.getPos().getX();
		msg.y = temc.getPos().getY();
		msg.z = temc.getPos().getZ();
		msg.facing = temc.getFacing();
		msg.itemStacks = new ItemStack[] {temc.getStackInSlot(0), temc.getStackInSlot(1)};
		return INSTANCE.channels.get(Side.SERVER).generatePacketFrom(msg);
	}
}