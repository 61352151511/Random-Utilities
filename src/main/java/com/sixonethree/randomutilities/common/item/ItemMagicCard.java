package com.sixonethree.randomutilities.common.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.sixonethree.randomutilities.reference.NBTTagKeys;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagicCard extends ItemBase {
	
	/* Constructors */
	
	public ItemMagicCard() {
		super();
		this.setNames("magic_card");
	}
	
	/* Overridden */
	
	@Override @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
		String signers1 = this.tagOrDefault(stack, NBTTagKeys.MAGIC_CARD_SIGNERS, "");
		String[] signers = signers1.split(";");
		tooltip.add("Signers: ");
		for (String signer : signers) {
			String name = null;
			try {
				name = UsernameCache.getLastKnownUsername(UUID.fromString(signer));
			} catch (IllegalArgumentException e) {}
			if (name != null) {
				tooltip.add(name);
			} else {
				tooltip.add(signer);
			}
		}
	}
	
	@Override public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		if (!playerIn.world.isRemote) {
			String signersA = this.tagOrDefault(itemStackIn, NBTTagKeys.MAGIC_CARD_SIGNERS, "");
			String[] splitSigners = signersA.split(";");
			ArrayList<String> signers;
			if (splitSigners.length == 1) {
				if (splitSigners[0].isEmpty()) {
					signers = new ArrayList<>();
				} else {
					signers = new ArrayList<>(Arrays.asList(splitSigners));
				}
			} else {
				signers = new ArrayList<>(Arrays.asList(splitSigners));
			}
			if (signers.contains(playerIn.getPersistentID().toString())) {
				signers.remove(playerIn.getPersistentID().toString());
			} else {
				signers.add(playerIn.getPersistentID().toString());
			}
			String compiled = "";
			for (int i = 0; i < signers.size(); i ++) {
				compiled += signers.get(i);
				if (i != signers.size() - 1) {
					compiled += ";";
				}
			}
			if (compiled.isEmpty()) {
				itemStackIn.getTagCompound().removeTag(NBTTagKeys.MAGIC_CARD_SIGNERS);
			} else {
				itemStackIn.getTagCompound().setString(NBTTagKeys.MAGIC_CARD_SIGNERS, compiled);
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}