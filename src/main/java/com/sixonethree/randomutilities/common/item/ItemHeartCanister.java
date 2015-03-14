package com.sixonethree.randomutilities.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.utility.Utilities;

public class ItemHeartCanister extends ItemBase {
	public ItemHeartCanister() {
		super();
		setMaxStackSize(1);
		setUnlocalizedName("heartCanister");
		setFull3D();
		setHasSubtypes(true);
	}
	
	public boolean hasEffect(ItemStack stack) {
		return stack.getItemDamage() > 1;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (stack.getItemDamage() < 2) {
			if (!player.isSneaking()) { // TAKE HEALTH
				float StoredHealth = stack.hasTagCompound() ? stack.getTagCompound().getFloat("Health Stored") : 0F;
				float MaxStoredHealth = getMaxStorage(stack);
				float PlayerHealth = player.getHealth();
				float HealthToTake = PlayerHealth - 2F;
				
				if (HealthToTake < 0F) HealthToTake = 0F;
				if (StoredHealth + HealthToTake > MaxStoredHealth) HealthToTake -= (StoredHealth + HealthToTake) - MaxStoredHealth;
				
				player.setHealth(PlayerHealth - HealthToTake);
				if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setFloat("Health Stored", StoredHealth + HealthToTake);
			} else { // GIVE HEALTH
				float StoredHealth = stack.hasTagCompound() ? stack.getTagCompound().getFloat("Health Stored") : 0F;
				float PlayerHealth = player.getHealth();
				float HealthToGive = player.getMaxHealth() - PlayerHealth;
				
				if (HealthToGive > StoredHealth) {
					HealthToGive = StoredHealth;
				}
				
				player.setHealth(PlayerHealth + HealthToGive);
				
				if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setFloat("Health Stored", StoredHealth - HealthToGive);
			}
		}
		return stack;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.AQUA + Utilities.Translate("tooltip.heartcanister.stores"));
		float StoredHealth = stack.hasTagCompound() ? stack.getTagCompound().getFloat("Health Stored") : 0F;
		float MaxStoredHealth = getMaxStorage(stack);
		if (stack.getItemDamage() > 1) {
			list.add(EnumChatFormatting.GREEN + Utilities.Translate("tooltip.heartcanister.auto"));
		} else {
			list.add(EnumChatFormatting.GREEN + Utilities.Translate("tooltip.heartcanister.rclick"));
			list.add(EnumChatFormatting.GREEN + Utilities.Translate("tooltip.heartcanister.rclick2"));
		}
		
		String StoredAsString = String.valueOf(StoredHealth / 2);
		String MaxStorageString = String.valueOf(MaxStoredHealth / 2);
		if (StoredAsString.contains(".")) StoredAsString = StoredAsString.substring(0, StoredAsString.indexOf(".") + 2);
		if (StoredAsString.endsWith(".0")) StoredAsString = StoredAsString.replace(".0", "");
		if (MaxStorageString.contains(".")) MaxStorageString = MaxStorageString.substring(0, MaxStorageString.indexOf(".") + 2);
		if (MaxStorageString.endsWith(".0")) MaxStorageString = MaxStorageString.replace(".0", "");
		
		list.add(Utilities.TranslateFormatted("tooltip.heartcanister.stored", StoredAsString, MaxStorageString));
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
	}
	
	@Override @SideOnly(Side.CLIENT) public int getColorFromItemStack(ItemStack stack, int pass) {
		if (pass == 0) {
			return 0xFFFFFF;
		} else {
			switch (stack.getItemDamage()) {
				case 0:
					return 0xFF0000;
				case 1:
					return 0xFFFF00;
				case 2:
					return 0x00FF00;
				case 3:
					return 0x00FFFF;
				default:
					return 0xFFFFFF;
			}
		}
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		String[] NameSuffixes = new String[] {"", "_large", "_auto", "_large_auto"};
		return super.getUnlocalizedName() + NameSuffixes[stack.getItemDamage()];
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int param4, boolean param5) {
		if (stack.getItemDamage() > 1 && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			
			float StoredHealth = stack.hasTagCompound() ? stack.getTagCompound().getFloat("Health Stored") : 0F;
			float MaxStoredHealth = getMaxStorage(stack);
			float PlayerHealth = player.getHealth();
			
			if (PlayerHealth < player.getMaxHealth() - 2F) {
				float HealthToGive = (player.getMaxHealth() - 2F) - PlayerHealth;
				if (HealthToGive > StoredHealth) {
					HealthToGive = StoredHealth;
				}
				player.setHealth(PlayerHealth + HealthToGive);
				float SetTo = StoredHealth - HealthToGive;
				
				if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setFloat("Health Stored", SetTo <= MaxStoredHealth ? SetTo : MaxStoredHealth);
				stack.getTagCompound().setFloat("Max Health Stored", MaxStoredHealth);
			}
			if (PlayerHealth > player.getMaxHealth() - 2F) {
				float HealthToTake = PlayerHealth - (player.getMaxHealth() - 2F);
				if (StoredHealth + HealthToTake > MaxStoredHealth) {
					HealthToTake = MaxStoredHealth - StoredHealth;
				}
				player.setHealth(PlayerHealth - HealthToTake);
				float SetTo = StoredHealth + HealthToTake;
				
				if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setFloat("Health Stored", SetTo <= MaxStoredHealth ? SetTo : MaxStoredHealth);
				stack.getTagCompound().setFloat("Max Health Stored", MaxStoredHealth);
			}
		}
	}
	
	public float getMaxStorage(ItemStack stack) {
		float Maximum = 0F;
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		if (stack.getTagCompound().hasKey("Maximum Health Stored")) {
			Maximum = stack.getTagCompound().getFloat("Maximum Health Stored");
		} else {
			if ((stack.getItemDamage() % 2) + 1 == 1) return 200F;
			if ((stack.getItemDamage() % 2) + 1 == 2) return 2000F;
		}
		return Maximum;
	}
}