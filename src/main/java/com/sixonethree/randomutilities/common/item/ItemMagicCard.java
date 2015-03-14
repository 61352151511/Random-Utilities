package com.sixonethree.randomutilities.common.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.reference.NBTTagKeys;

public class ItemMagicCard extends ItemBase {
	public ItemMagicCard() {
		super();
		setMaxStackSize(1);
		setUnlocalizedName("magicCard");
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) @Override public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		String signers1 = stack.getTagCompound().hasKey(NBTTagKeys.MAGIC_CARD_SIGNER_NAMES) ? stack.getTagCompound().getString(NBTTagKeys.MAGIC_CARD_SIGNER_NAMES) : ";";
		if (signers1.isEmpty()) signers1 = ";";
		String[] signers = signers1.split(";");
		list.add("Signers: ");
		for (String signer : signers) {
			list.add(signer);
		}
	}
	
	@Override public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!player.worldObj.isRemote) {
			if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
			String signersA = stack.getTagCompound().hasKey(NBTTagKeys.MAGIC_CARD_SIGNERS) ? stack.getTagCompound().getString(NBTTagKeys.MAGIC_CARD_SIGNERS) : "";
			String[] splitSigners = signersA.split(";");
			ArrayList<String> signers = new ArrayList<String>(Arrays.asList(splitSigners));
			if (signers.contains(player.getPersistentID().toString())) {
				signers.remove(player.getPersistentID().toString());
			} else {
				signers.add(player.getPersistentID().toString());
			}
			String compiled = "";
			for (int i = 0; i < signers.size(); i ++) {
				compiled += signers.get(i);
				if (i != signers.size()) compiled += ";";
			}
			String compiled_names = "";
			for (int i = 0; i < signers.size(); i ++) {
				try {
					compiled_names += MinecraftServer.getServer().getPlayerProfileCache().getProfileByUUID(UUID.fromString(signers.get(i))).getName();
				} catch (IllegalArgumentException e) {
					compiled_names += "";
				}
				if (i != signers.size()) compiled_names += ";";
			}
			if (compiled == ";") {
				stack.getTagCompound().removeTag(NBTTagKeys.MAGIC_CARD_SIGNER_NAMES);
				stack.getTagCompound().removeTag(NBTTagKeys.MAGIC_CARD_SIGNERS);
			} else {
				stack.getTagCompound().setString(NBTTagKeys.MAGIC_CARD_SIGNERS, compiled);
				stack.getTagCompound().setString(NBTTagKeys.MAGIC_CARD_SIGNER_NAMES, compiled_names);
			}
			return stack;
		} else return stack;
	}
}