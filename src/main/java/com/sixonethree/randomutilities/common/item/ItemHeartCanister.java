package com.sixonethree.randomutilities.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.sixonethree.randomutilities.reference.NBTTagKeys;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHeartCanister extends ItemBase implements IHeartCanister {
	String[] nameSuffixes = new String[] {"", "_large", "_auto", "_large_auto"};
	
	/* Constructors */
	
	public ItemHeartCanister() {
		super();
		this.setHasSubtypes(true);
		this.setNames("heart_canister");
	}
	
	/* Overridden */
	
	@Override @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.heartcanister.stores"));
		float storedHealth = getCurrentHealthStorage(stack);
		float maxStoredHealth = getMaxHealthStorage(stack);
		if (stack.getItemDamage() > 1) {
			tooltip.add(TextFormatting.GREEN + I18n.format("tooltip.heartcanister.auto"));
		} else {
			tooltip.add(TextFormatting.GREEN + I18n.format("tooltip.heartcanister.rclick"));
			tooltip.add(TextFormatting.GREEN + I18n.format("tooltip.heartcanister.rclick2"));
		}
		
		String storedAsString = String.valueOf(storedHealth / 2);
		String maxStorageString = String.valueOf(maxStoredHealth / 2);
		if (storedAsString.contains(".")) storedAsString = storedAsString.substring(0, storedAsString.indexOf(".") + 2);
		if (storedAsString.endsWith(".0")) storedAsString = storedAsString.replace(".0", "");
		if (maxStorageString.contains(".")) maxStorageString = maxStorageString.substring(0, maxStorageString.indexOf(".") + 2);
		if (maxStorageString.endsWith(".0")) maxStorageString = maxStorageString.replace(".0", "");
		
		tooltip.add(I18n.format("tooltip.heartcanister.stored", storedAsString, maxStorageString));
	}
	
	@Override @SideOnly(Side.CLIENT) public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (int i = 0; i <= 3; i ++)
			subItems.add(new ItemStack(this, 1, i));
	}
	
	@Override public String getUnlocalizedName(ItemStack stack) {
		return stack.getItemDamage() < this.nameSuffixes.length ? super.getUnlocalizedName() + this.nameSuffixes[stack.getItemDamage()] : super.getUnlocalizedName();
	}
	
	@Override public boolean hasEffect(ItemStack stack) {
		return this.isHeartCanisterAutomatic(stack);
	}
	
	@Override public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		if (!this.isHeartCanisterAutomatic(itemStackIn)) {
			if (!playerIn.isSneaking()) { // TAKE HEALTH
				float storedHealth = this.getCurrentHealthStorage(itemStackIn);
				float maxStoredHealth = this.getMaxHealthStorage(itemStackIn);
				float playerHealth = playerIn.getHealth();
				float healthToTake = playerHealth - 2F;
				
				if (healthToTake < 0F) healthToTake = 0F;
				if (storedHealth + healthToTake > maxStoredHealth) healthToTake -= (storedHealth + healthToTake) - maxStoredHealth;
				
				playerIn.setHealth(playerHealth - healthToTake);
				itemStackIn.getTagCompound().setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, storedHealth + healthToTake);
			} else { // GIVE HEALTH
				float storedHealth = this.getCurrentHealthStorage(itemStackIn);
				float playerHealth = playerIn.getHealth();
				float healthToGive = playerIn.getMaxHealth() - playerHealth;
				
				if (healthToGive > storedHealth) healthToGive = storedHealth;
				
				playerIn.setHealth(playerHealth + healthToGive);
				itemStackIn.getTagCompound().setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, storedHealth - healthToGive);
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (stack.getItemDamage() > 1 && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			
			float storedHealth = this.getCurrentHealthStorage(stack);
			float maxStoredHealth = this.getMaxHealthStorage(stack);
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
				if (storedHealth + healthToTake > maxStoredHealth) healthToTake = maxStoredHealth - storedHealth;
				
				player.setHealth(playerHealth - healthToTake);
				float setTo = storedHealth + healthToTake;
				
				stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_HEALTH_STORED, setTo <= maxStoredHealth ? setTo : maxStoredHealth);
				stack.getTagCompound().setFloat(NBTTagKeys.MAX_HEALTH_STORED, maxStoredHealth);
			}
		}
	}
	
	/* IHeartCanister */
	
	@Override public boolean isLarge(ItemStack stack) {
		return stack.getItemDamage() % 2 == 1;
	}
	
	@Override public boolean isHeartCanisterAutomatic(ItemStack stack) {
		return stack.getItemDamage() >= 2;
	}
}