package com.sixonethree.randomutilities.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.reference.NBTTagKeys;
import com.sixonethree.randomutilities.utility.Utilities;

public class ItemHeartCanister extends ItemBase implements IHeartCanister {
	public ItemHeartCanister() {
		super();
		setMaxStackSize(1);
		setUnlocalizedName("heartCanister");
		setFull3D();
		setHasSubtypes(true);
	}
	
	@Override public boolean hasEffect(ItemStack stack) {
		return stack.getItemDamage() > 1;
	}
	
	@Override public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (stack.getItemDamage() < 2) {
			if (!player.isSneaking()) { // TAKE HEALTH
				float StoredHealth = getCurrentHealthStorage(stack);
				float MaxStoredHealth = getMaxHealthStorage(stack);
				float PlayerHealth = player.getHealth();
				float HealthToTake = PlayerHealth - 2F;
				
				if (HealthToTake < 0F) HealthToTake = 0F;
				if (StoredHealth + HealthToTake > MaxStoredHealth) HealthToTake -= (StoredHealth + HealthToTake) - MaxStoredHealth;
				
				player.setHealth(PlayerHealth - HealthToTake);
				stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, StoredHealth + HealthToTake);
			} else { // GIVE HEALTH
				float StoredHealth = getCurrentHealthStorage(stack);
				float PlayerHealth = player.getHealth();
				float HealthToGive = player.getMaxHealth() - PlayerHealth;
				
				if (HealthToGive > StoredHealth) HealthToGive = StoredHealth;
				
				player.setHealth(PlayerHealth + HealthToGive);
				stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, StoredHealth - HealthToGive);
			}
		}
		return stack;
	}
	
	@Override @SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("tooltip.heartcanister.stores"));
		float StoredHealth = getCurrentHealthStorage(stack);
		float MaxStoredHealth = getMaxHealthStorage(stack);
		if (stack.getItemDamage() > 1) {
			list.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("tooltip.heartcanister.auto"));
		} else {
			list.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("tooltip.heartcanister.rclick"));
			list.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("tooltip.heartcanister.rclick2"));
		}
		
		String StoredAsString = String.valueOf(StoredHealth / 2);
		String MaxStorageString = String.valueOf(MaxStoredHealth / 2);
		if (StoredAsString.contains(".")) StoredAsString = StoredAsString.substring(0, StoredAsString.indexOf(".") + 2);
		if (StoredAsString.endsWith(".0")) StoredAsString = StoredAsString.replace(".0", "");
		if (MaxStorageString.contains(".")) MaxStorageString = MaxStorageString.substring(0, MaxStorageString.indexOf(".") + 2);
		if (MaxStorageString.endsWith(".0")) MaxStorageString = MaxStorageString.replace(".0", "");
		
		list.add(Utilities.translateFormatted("tooltip.heartcanister.stored", StoredAsString, MaxStorageString));
	}
	
	@Override @SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i <= 3; i ++)
			list.add(new ItemStack(item, 1, i));
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
	
	@Override public String getUnlocalizedName(ItemStack stack) {
		String[] NameSuffixes = new String[] {"", "_large", "_auto", "_large_auto"};
		return super.getUnlocalizedName() + NameSuffixes[stack.getItemDamage()];
	}
	
	@Override public void onUpdate(ItemStack stack, World world, Entity entity, int param4, boolean param5) {
		if (stack.getItemDamage() > 1 && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			
			float StoredHealth = getCurrentHealthStorage(stack);
			float MaxStoredHealth = getMaxHealthStorage(stack);
			float PlayerHealth = player.getHealth();
			
			if (PlayerHealth < player.getMaxHealth() - 2F) {
				float HealthToGive = (player.getMaxHealth() - 2F) - PlayerHealth;
				if (HealthToGive > StoredHealth) HealthToGive = StoredHealth;
				
				player.setHealth(PlayerHealth + HealthToGive);
				float SetTo = StoredHealth - HealthToGive;
				
				stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, SetTo <= MaxStoredHealth ? SetTo : MaxStoredHealth);
				stack.getTagCompound().setFloat(NBTTagKeys.MAX_HEALTH_STORED, MaxStoredHealth);
			}
			
			if (PlayerHealth > player.getMaxHealth() - 2F) {
				float HealthToTake = PlayerHealth - (player.getMaxHealth() - 2F);
				if (StoredHealth + HealthToTake > MaxStoredHealth) HealthToTake = MaxStoredHealth - StoredHealth;
				
				player.setHealth(PlayerHealth - HealthToTake);
				float SetTo = StoredHealth + HealthToTake;
				
				stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, SetTo <= MaxStoredHealth ? SetTo : MaxStoredHealth);
				stack.getTagCompound().setFloat(NBTTagKeys.MAX_HEALTH_STORED, MaxStoredHealth);
			}
		}
	}
	
	@Override public float getCurrentHealthStorage(ItemStack stack) {
		tagCompoundVerification(stack);
		return tagOrDefault(stack, NBTTagKeys.CURRENT_HEALTH_STORED, 0F);
	}
	
	@Override public float getMaxHealthStorage(ItemStack stack) {
		tagCompoundVerification(stack);
		return tagOrDefault(stack, NBTTagKeys.MAX_HEALTH_STORED, ((stack.getItemDamage() % 2) + 1) == 1 ? 200F : 2000F);
	}
}