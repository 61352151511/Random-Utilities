package com.sixonethree.randomutilities.common.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.client.ColorLogic;
import com.sixonethree.randomutilities.reference.NBTTagKeys;
import com.sixonethree.randomutilities.utility.Utilities;

public class ItemCombined extends ItemBase implements ILunchbox, IHeartCanister {
	public ItemCombined() {
		super();
		setMaxStackSize(1);
		setUnlocalizedName("combined");
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
		
		float StoredHealth = getCurrentHealthStorage(stack);
		float MaxStoredHealth = getMaxHealthStorage(stack);
		String StoredHealthAsString = String.valueOf(StoredHealth / 2);
		if (StoredHealthAsString.contains(".")) StoredHealthAsString = StoredHealthAsString.substring(0, StoredHealthAsString.indexOf(".") + 2);
		if (StoredHealthAsString.endsWith(".0")) StoredHealthAsString = StoredHealthAsString.replace(".0", "");
		String MaxStorageHealthString = String.valueOf(MaxStoredHealth / 2);
		if (MaxStorageHealthString.contains(".")) MaxStorageHealthString = MaxStorageHealthString.substring(0, MaxStorageHealthString.indexOf(".") + 2);
		if (MaxStorageHealthString.endsWith(".0")) MaxStorageHealthString = MaxStorageHealthString.replace(".0", "");
		
		/* Lunchbox */
		
		float StoredFood = getCurrentFoodStorage(stack);
		float Maximum = getMaxFoodStorage(stack);
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
				if (StoredHealth + HealthToTake > MaxStoredHealth) {
					HealthToTake = MaxStoredHealth - StoredHealth;
				}
				player.setHealth(PlayerHealth - HealthToTake);
				float SetTo = StoredHealth + HealthToTake;
				stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, SetTo <= MaxStoredHealth ? SetTo : MaxStoredHealth);
				stack.getTagCompound().setFloat(NBTTagKeys.MAX_HEALTH_STORED, MaxStoredHealth);
			}
			
			/* LUNCHBOX */
			
			int PlayerFood = player.getFoodStats().getFoodLevel();
			if (PlayerFood < 20) {
				int StoredFood = (int) getCurrentFoodStorage(stack);
				int FoodToGive = 20 - PlayerFood;
				if (FoodToGive > StoredFood) FoodToGive = StoredFood;
				player.getFoodStats().addStats(FoodToGive, FoodToGive > 0 ? 20F : 0F);
				stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_FOOD_STORED, StoredFood - FoodToGive);
			}
		}
	}
	
	@Override @SideOnly(Side.CLIENT) public int getColorFromItemStack(ItemStack stack, int pass) {
		if (pass == 1) return 0x00FFFF;
		if (pass == 2) return ColorLogic.getColorFromMeta(getColor(stack));
		return 0xFFFFFF;
	}

	@Override public float getCurrentHealthStorage(ItemStack stack) {
		tagCompoundVerification(stack);
		return tagOrDefault(stack, NBTTagKeys.CURRENT_HEALTH_STORED, 0F);
	}

	@Override public float getMaxHealthStorage(ItemStack stack) {
		tagCompoundVerification(stack);
		return tagOrDefault(stack, NBTTagKeys.MAX_HEALTH_STORED, 2000F);
	}

	@Override public float getCurrentFoodStorage(ItemStack stack) {
		tagCompoundVerification(stack);
		return tagOrDefault(stack, NBTTagKeys.CURRENT_FOOD_STORED, 0F);
	}

	@Override public float getMaxFoodStorage(ItemStack stack) {
		tagCompoundVerification(stack);
		return tagOrDefault(stack, NBTTagKeys.MAX_FOOD_STORED, 200F);
	}
	
	@Override public int getColor(ItemStack stack) {
		tagCompoundVerification(stack);
		return tagOrDefault(stack, NBTTagKeys.COLOR, 16);
	}
	
	@Override public boolean hasColor(ItemStack stack) {
		tagCompoundVerification(stack);
		return stack.getTagCompound().hasKey(NBTTagKeys.COLOR);
	}
}
