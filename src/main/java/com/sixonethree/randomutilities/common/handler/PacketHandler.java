package com.sixonethree.randomutilities.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.EnumMap;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
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

import com.sixonethree.randomutilities.RandomUtilities;
import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;

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
			TileEntity te = world.getTileEntity(new BlockPos(msg.x, msg.y, msg.z));
			if (msg.TEType == 0) {
				if (te instanceof TileEntityMagicChest) {
					TileEntityMagicChest mcte = (TileEntityMagicChest) te;
					mcte.handlePacketData(msg.itemStacks);
				}
			} else if (msg.TEType == 1) {
				if (te instanceof TileEntityDisplayTable) {
					TileEntityDisplayTable sctte = (TileEntityDisplayTable) te;
					sctte.setFacing(msg.facing);
					sctte.handlePacketData(msg.itemStacks);
				}
			}
		}
	}
	
	public static class RandomUtilitiesMessage {
		int x;
		int y;
		int z;
		int facing;
		int TEType;
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
			target.writeInt(msg.TEType);
			target.writeInt(msg.facing);
			target.writeInt(msg.itemStacks.length);
			for (ItemStack stack : msg.itemStacks) {
				ByteBufUtils.writeItemStack(target, stack);
			}
		}
		
		@Override public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, RandomUtilitiesMessage msg) {
			msg.x = dat.readInt();
			msg.y = dat.readInt();
			msg.z = dat.readInt();
			msg.TEType = dat.readInt();
			msg.facing = dat.readInt();
			int stacksLength = dat.readInt();
			ItemStack[] stacks = new ItemStack[stacksLength];
			for (int i = 0; i < stacksLength; i ++) {
				stacks[i] = ByteBufUtils.readItemStack(dat);
			}
			msg.itemStacks = stacks;
		}
	}
	
	@SuppressWarnings("unchecked") public static Packet<INetHandlerPlayClient> getPacket(TileEntityMagicChest temc) {
		RandomUtilitiesMessage msg = new RandomUtilitiesMessage();
		msg.x = temc.getPos().getX();
		msg.y = temc.getPos().getY();
		msg.z = temc.getPos().getZ();
		msg.TEType = 0;
		msg.facing = 0;
		msg.itemStacks = new ItemStack[] {temc.getStackInSlot(0), temc.getStackInSlot(1)};
		return (Packet<INetHandlerPlayClient>) INSTANCE.channels.get(Side.SERVER).generatePacketFrom(msg);
	}
	
	@SuppressWarnings("unchecked") public static Packet<INetHandlerPlayClient> getPacket(TileEntityDisplayTable tesct) {
		RandomUtilitiesMessage msg = new RandomUtilitiesMessage();
		msg.x = tesct.getPos().getX();
		msg.y = tesct.getPos().getY();
		msg.z = tesct.getPos().getZ();
		msg.TEType = 1;
		msg.facing = tesct.getFacing();
		msg.itemStacks = tesct.getInventory();
		return (Packet<INetHandlerPlayClient>) INSTANCE.channels.get(Side.SERVER).generatePacketFrom(msg);
	}
}