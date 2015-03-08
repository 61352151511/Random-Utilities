package com.sixonethree.randomutilities.item;

import java.util.List;

import morph.api.Api;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.sixonethree.randomutilities.client.ColorLogic;
import com.sixonethree.randomutilities.utility.Utilities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCombined extends ItemBase {
	public IIcon[] itemIcons = new IIcon[3];
	
	public ItemCombined() {
		super();
		setMaxStackSize(1);
		setUnlocalizedName("combined");
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
			
			if (Api.morphProgress(player.getCommandSenderName(), false) == 1.0F) {
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
	
	@Override @SideOnly(Side.CLIENT) public int getColorFromItemStack(ItemStack stack, int pass) {
		if (pass == 1) return 0x00FFFF;
		if (pass == 2) return ColorLogic.getColorFromMeta(stack.hasTagCompound() ? stack.getTagCompound().hasKey("Color") ? stack.getTagCompound().getInteger("Color") : 16 : 16);
		return 0xFFFFFF;
	}
	
	/* STUFF REMOVED IN 1.8 */
	
	@SideOnly(Side.CLIENT) public void registerIcons(IIconRegister register) {
		String BaseName = getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1);
		itemIcon = itemIcons[0];
		itemIcons[0] = register.registerIcon(BaseName);
		itemIcons[1] = register.registerIcon(BaseName + "_overlay1");
		itemIcons[2] = register.registerIcon(BaseName + "_overlay2");
	}
	
	@Override @SideOnly(Side.CLIENT) public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override public int getRenderPasses(int meta) {
		return 3;
	}
	
	@Override public IIcon getIcon(ItemStack stack, int pass) {
		return itemIcons[pass];
	}
}
