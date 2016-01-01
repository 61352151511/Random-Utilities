package com.sixonethree.randomutilities.common.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.reference.NBTTagKeys;

public class ItemMagicCard extends ItemBase {
	public ItemMagicCard() {
		super();
		this.setUnlocalizedName("magicCard");
	}
	
	@Override @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
		String signers1 = this.tagOrDefault(stack, NBTTagKeys.MAGIC_CARD_SIGNERS, "");
		String[] signers = signers1.split(";");
		list.add("Signers: ");
		for (String signer : signers) {
			String name = null;
			try {
				name = UsernameCache.getLastKnownUsername(UUID.fromString(signer));
			} catch (IllegalArgumentException e) {}
			if (name != null) {
				list.add(name);
			} else {
				list.add(signer);
			}
		}
	}
	
	@Override public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!player.worldObj.isRemote) {
			String signersA = this.tagOrDefault(stack, NBTTagKeys.MAGIC_CARD_SIGNERS, "");
			String[] splitSigners = signersA.split(";");
			ArrayList<String> signers;
			if (splitSigners.length == 1) {
				if (splitSigners[0].isEmpty()) {
					signers = new ArrayList<String>();
				} else {
					signers = new ArrayList<String>(Arrays.asList(splitSigners));
				}
			} else {
				signers = new ArrayList<String>(Arrays.asList(splitSigners));
			}
			if (signers.contains(player.getPersistentID().toString())) {
				signers.remove(player.getPersistentID().toString());
			} else {
				signers.add(player.getPersistentID().toString());
			}
			String compiled = "";
			for (int i = 0; i < signers.size(); i ++) {
				compiled += signers.get(i);
				if (i != signers.size() - 1) {
					compiled += ";";
				}
			}
			if (compiled.isEmpty()) {
				stack.getTagCompound().removeTag(NBTTagKeys.MAGIC_CARD_SIGNERS);
			} else {
				stack.getTagCompound().setString(NBTTagKeys.MAGIC_CARD_SIGNERS, compiled);
			}
			return stack;
		} else return stack;
	}
}