package com.sixonethree.randomutilities.common.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.reference.NBTTagKeys;
import com.sixonethree.randomutilities.utility.Utilities;

public class ItemCombined extends ItemBase implements ILunchbox, IHeartCanister {
	public ItemCombined() {
		super();
		this.setUnlocalizedName("combined");
		this.setFull3D();
	}
	
	@Override @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean bool) {
		list.add(TextFormatting.AQUA + I18n.translateToLocal("tooltip.heartcanister.stores"));
		list.add(TextFormatting.AQUA + I18n.translateToLocal("tooltip.lunchbox.stores"));
		list.add(TextFormatting.GREEN + I18n.translateToLocal("tooltip.heartcanister.auto"));
		list.add(TextFormatting.GREEN + I18n.translateToLocal("tooltip.lunchbox.auto"));
		list.add(TextFormatting.RED + I18n.translateToLocal("tooltip.lunchbox.fill"));
		
		/* Heart Canister */
		
		float shoredHealth = getCurrentHealthStorage(stack);
		float maxStoredHealth = getMaxHealthStorage(stack);
		String storedHealthAsString = String.valueOf(shoredHealth / 2);
		if (storedHealthAsString.contains(".")) storedHealthAsString = storedHealthAsString.substring(0, storedHealthAsString.indexOf(".") + 2);
		if (storedHealthAsString.endsWith(".0")) storedHealthAsString = storedHealthAsString.replace(".0", "");
		String maxStoredHealthAsString = String.valueOf(maxStoredHealth / 2);
		if (maxStoredHealthAsString.contains(".")) maxStoredHealthAsString = maxStoredHealthAsString.substring(0, maxStoredHealthAsString.indexOf(".") + 2);
		if (maxStoredHealthAsString.endsWith(".0")) maxStoredHealthAsString = maxStoredHealthAsString.replace(".0", "");
		
		/* Lunchbox */
		
		float storedFood = getCurrentFoodStorage(stack);
		float maxStoredFood = getMaxFoodStorage(stack);
		String storedFoodAsString = String.valueOf(storedFood / 2);
		String maxStoredFoodAsString = String.valueOf(maxStoredFood / 2);
		if (storedFoodAsString.contains(".")) storedFoodAsString = storedFoodAsString.substring(0, storedFoodAsString.indexOf(".") + 2);
		if (storedFoodAsString.endsWith(".0")) storedFoodAsString = storedFoodAsString.replace(".0", "");
		if (maxStoredFoodAsString.endsWith(".0")) maxStoredFoodAsString = maxStoredFoodAsString.replace(".0", "");
		
		list.add(Utilities.translateFormatted("tooltip.heartcanister.stored", storedHealthAsString, maxStoredHealthAsString));
		list.add(Utilities.translateFormatted("tooltip.lunchbox.stored", storedFoodAsString, maxStoredFoodAsString));
	}
	
	@Override public boolean hasEffect(ItemStack stack) { return true; }
	
	@Override public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			
			/* HEART CANISTER */
			
			float storedHealth = getCurrentHealthStorage(stack);
			float maxStoredHealth = getMaxHealthStorage(stack);
			float playerHealth = player.getHealth();
			
			if (playerHealth < player.getMaxHealth() - 2F) {
				float healthToGive = (player.getMaxHealth() - 2F) - playerHealth;
				if (healthToGive > storedHealth) healthToGive = storedHealth;
				
				player.setHealth(playerHealth + healthToGive);
				float setTo = storedHealth - healthToGive;
				stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, setTo <= maxStoredHealth ? setTo : maxStoredHealth);
				stack.getTagCompound().setFloat(NBTTagKeys.MAX_HEALTH_STORED, maxStoredHealth);
			}
			if (playerHealth > player.getMaxHealth() - 2F) {
				float healthToTake = playerHealth - (player.getMaxHealth() - 2F);
				if (storedHealth + healthToTake > maxStoredHealth) {
					healthToTake = maxStoredHealth - storedHealth;
				}
				player.setHealth(playerHealth - healthToTake);
				float setTo = storedHealth + healthToTake;
				stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, setTo <= maxStoredHealth ? setTo : maxStoredHealth);
				stack.getTagCompound().setFloat(NBTTagKeys.MAX_HEALTH_STORED, maxStoredHealth);
			}
			
			/* LUNCHBOX */
			
			int playerFood = player.getFoodStats().getFoodLevel();
			if (playerFood < 20) {
				int storedFood = (int) getCurrentFoodStorage(stack);
				int foodToGive = 20 - playerFood;
				if (foodToGive > storedFood) foodToGive = storedFood;
				player.getFoodStats().addStats(foodToGive, foodToGive > 0 ? 20F : 0F);
				stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_FOOD_STORED, storedFood - foodToGive);
			}
		}
	}
	
	/* ILunchbox */
	
	@Override public boolean isLunchboxAutomatic(ItemStack stack) {
		return stack.getMetadata() == 1;
	}
	
	@Override public void setCurrentFoodStorage(ItemStack stack, float storage) {
		this.tagCompoundVerification(stack);
		stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_FOOD_STORED, storage);
	}
	
	/* IHeartCanister */
	
	@Override public boolean isHeartCanisterAutomatic(ItemStack stack) { return true; }
	@Override public boolean isLarge(ItemStack stack) { return true; }
}