package com.sixonethree.randomutilities.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.client.ColorLogic;
import com.sixonethree.randomutilities.utility.Utilities;

public class ItemCombined extends ItemBase {
	public ItemCombined() {
		super();
		setMaxStackSize(1);
		setUnlocalizedName("combined_lunchbox_heart_canister");
		setFull3D();
	}
	
	@Override public boolean hasEffect(ItemStack stack) { return true; }
	
	@SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.AQUA + Utilities.Translate("tooltip.heartcanister.stores"));
		list.add(EnumChatFormatting.AQUA + Utilities.Translate("tooltip.lunchbox.stores"));
		list.add(EnumChatFormatting.GREEN + Utilities.Translate("tooltip.heartcanister.auto"));
		list.add(EnumChatFormatting.GREEN + Utilities.Translate("tooltip.lunchbox.auto"));
		list.add(EnumChatFormatting.RED + Utilities.Translate("tooltip.lunchbox.fill"));
		
		/* Heart Canister */
		
		float StoredHealth = stack.hasTagCompound() ? stack.getTagCompound().getFloat("Health Stored") : 0F;
		float MaxStoredHealth = getMaxStorage(stack, "Maximum Health Stored");
		String StoredHealthAsString = String.valueOf(StoredHealth / 2);
		if (StoredHealthAsString.contains(".")) StoredHealthAsString = StoredHealthAsString.substring(0, StoredHealthAsString.indexOf(".") + 2);
		if (StoredHealthAsString.endsWith(".0")) StoredHealthAsString = StoredHealthAsString.replace(".0", "");
		String MaxStorageHealthString = String.valueOf(MaxStoredHealth / 2);
		if (MaxStorageHealthString.contains(".")) MaxStorageHealthString = MaxStorageHealthString.substring(0, MaxStorageHealthString.indexOf(".") + 2);
		if (MaxStorageHealthString.endsWith(".0")) MaxStorageHealthString = MaxStorageHealthString.replace(".0", "");
		
		/* Lunchbox */
		
		float StoredFood = stack.hasTagCompound() ? stack.getTagCompound().getFloat("Food Stored") : 0F;
		float Maximum = getMaxStorage(stack, "Maximum Food Stored");
		String StoredAsFoodString = String.valueOf(StoredFood / 2);
		String MaximumStorageFoodString = String.valueOf(Maximum / 2);
		if (StoredAsFoodString.contains(".")) StoredAsFoodString = StoredAsFoodString.substring(0, StoredAsFoodString.indexOf(".") + 2);
		if (StoredAsFoodString.endsWith(".0")) StoredAsFoodString = StoredAsFoodString.replace(".0", "");
		if (MaximumStorageFoodString.endsWith(".0")) MaximumStorageFoodString = MaximumStorageFoodString.replace(".0", "");
		
		list.add(Utilities.TranslateFormatted("tooltip.heartcanister.stored", StoredHealthAsString, MaxStorageHealthString));
		list.add(Utilities.TranslateFormatted("tooltip.lunchbox.stored", StoredAsFoodString, MaximumStorageFoodString));
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int param4, boolean param5) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			
			/* HEART CANISTER */
			
			float StoredHealth = stack.hasTagCompound() ? stack.getTagCompound().getFloat("Health Stored") : 0F;
			float MaxStoredHealth = getMaxStorage(stack, "Maximum Health Stored");
			float PlayerHealth = player.getHealth();
			
			if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
			
			if (PlayerHealth < player.getMaxHealth() - 2F) {
				float HealthToGive = (player.getMaxHealth() - 2F) - PlayerHealth;
				if (HealthToGive > StoredHealth) {
					HealthToGive = StoredHealth;
				}
				player.setHealth(PlayerHealth + HealthToGive);
				float SetTo = StoredHealth - HealthToGive;
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
				stack.getTagCompound().setFloat("Health Stored", SetTo <= MaxStoredHealth ? SetTo : MaxStoredHealth);
				stack.getTagCompound().setFloat("Max Health Stored", MaxStoredHealth);
			}
			
			/* LUNCHBOX */
			
			int PlayerFood = player.getFoodStats().getFoodLevel();
			if (PlayerFood < 20) {
				int StoredFood = (int) Math.floor(stack.hasTagCompound() ? stack.getTagCompound().getFloat("Food Stored") : 0F);
				int FoodToGive = 20 - PlayerFood;
				if (FoodToGive > StoredFood) {
					FoodToGive = StoredFood;
				}
				player.getFoodStats().addStats(FoodToGive, FoodToGive > 0 ? 20F : 0F);
				stack.getTagCompound().setFloat("Food Stored", StoredFood - FoodToGive);
			}
		}
	}
	
	@Override @SideOnly(Side.CLIENT) public int getColorFromItemStack(ItemStack stack, int pass) {
		if (stack.getItemDamage() == 0) { // LUNCHBOX
			return pass == 0 ? 0xFFFFFF : ColorLogic.getColorFromMeta(stack.hasTagCompound() ? stack.getTagCompound().hasKey("Color") ? stack.getTagCompound().getInteger("Color") : 16 : 16);
		} else { // HEART CANISTER
			return pass == 0 ? 0xFFFFFF : 0x00FFFF;
		}
	}
	
	public float getMaxStorage(ItemStack stack, String type) {
		float Maximum = 0F;
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		if (stack.getTagCompound().hasKey(type)) {
			Maximum = stack.getTagCompound().getFloat(type);
		} else {
			return type == "Maximum Health Stored" ? 2000F : 200F;
		}
		return Maximum;
	}
}
