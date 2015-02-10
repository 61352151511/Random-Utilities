package com.sixonethree.randomutilities.item;

import java.util.List;

import morph.api.Api;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.sixonethree.randomutilities.utility.Utilities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHeartCanister extends ItemBase {
	public IIcon[] itemIcons = new IIcon[2];
	
	public ItemHeartCanister() {
		super();
		setMaxStackSize(1);
		setUnlocalizedName("heartCanister");
		setFull3D();
		setHasSubtypes(true);
	}
	
	public boolean hasEffect(ItemStack stack) {
		int meta = stack.getCurrentDurability();
		return meta > 1;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!player.isSneaking()) {
			float StoredHealth = 0F;
			float MaxStoredHealth = getMaxStorage(stack);
			if (stack.getTagCompound() == null) {
				stack.setTagCompound(new NBTTagCompound());
			}
			try {
				StoredHealth = stack.getTagCompound().getFloat("Health Stored");
			} catch (NullPointerException e) {
				StoredHealth = 0F;
			}
			float PlayerHealth = player.getHealth();
			float HealthToTake = PlayerHealth - 2F;
			if (HealthToTake < 0F) {
				HealthToTake = 0F;
			}
			if (StoredHealth + HealthToTake > MaxStoredHealth) {
				HealthToTake = HealthToTake - ((StoredHealth + HealthToTake) - MaxStoredHealth);
			}
			player.setHealth(PlayerHealth - HealthToTake);
			stack.getTagCompound().setFloat("Health Stored", StoredHealth + HealthToTake);
		} else {
			float StoredHealth = 0F;
			if (stack.getTagCompound() == null) {
				stack.setTagCompound(new NBTTagCompound());
			}
			try {
				StoredHealth = stack.getTagCompound().getFloat("Health Stored");
			} catch (NullPointerException e) {
				StoredHealth = 0F;
			}
			float PlayerHealth = player.getHealth();
			float HealthToGive = player.getMaxHealth() - PlayerHealth;
			if (HealthToGive > StoredHealth) {
				HealthToGive = StoredHealth;
			}
			player.setHealth(PlayerHealth + HealthToGive);
			stack.getTagCompound().setFloat("Health Stored", StoredHealth - HealthToGive);
		}
		return stack;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.AQUA + Utilities.Translate("tooltip.heartcanister.stores"));
		float StoredHealth = 0F;
		float MaxStoredHealth = getMaxStorage(stack);
		try {
			StoredHealth = stack.getTagCompound().getFloat("Health Stored");
		} catch (NullPointerException e) {
			StoredHealth = 0F;
		}
		if (stack.getCurrentDurability() > 1) {
			list.add(EnumChatFormatting.GREEN + Utilities.Translate("tooltip.heartcanister.auto"));
		} else {
			list.add(EnumChatFormatting.GREEN + Utilities.Translate("tooltip.heartcanister.rclick"));
			list.add(EnumChatFormatting.GREEN + Utilities.Translate("tooltip.heartcanister.rclick2"));
		}
		String StoredAsString = String.valueOf(StoredHealth / 2);
		if (StoredAsString.contains(".")) {
			StoredAsString = StoredAsString.substring(0, StoredAsString.indexOf(".") + 2);
		}
		if (StoredAsString.endsWith(".0")) {
			StoredAsString = StoredAsString.replace(".0", "");
		}
		String MaxStorageString = String.valueOf(MaxStoredHealth / 2);
		if (MaxStorageString.contains(".")) {
			MaxStorageString = MaxStorageString.substring(0, MaxStorageString.indexOf(".") + 2);
		}
		if (MaxStorageString.endsWith(".0")) {
			MaxStorageString = MaxStorageString.replace(".0", "");
		}
		list.add(Utilities.TranslateFormatted("tooltip.heartcanister.stored", StoredAsString, MaxStorageString));
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
	}
	
	@SideOnly(Side.CLIENT) public void registerIcons(IIconRegister register) {
		String BaseName = getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1);
		itemIcon = itemIcons[0];
		itemIcons[0] = register.registerIcon(BaseName);
		itemIcons[1] = register.registerIcon(BaseName + "_overlay");
	}
	
	@Override @SideOnly(Side.CLIENT) public int getColorFromItemStack(ItemStack stack, int pass) {
		if (pass == 0) {
			return 0xFFFFFF;
		} else {
			switch (stack.getCurrentDurability()) {
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
	
	@Override @SideOnly(Side.CLIENT) public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override public int getRenderPasses(int meta) {
		return 2;
	}
	
	@Override public IIcon getIcon(ItemStack stack, int pass) {
		return itemIcons[pass];
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		String[] NameSuffixes = new String[] {"", "_large", "_auto", "_large_auto"};
		return super.getUnlocalizedName() + NameSuffixes[stack.getCurrentDurability()];
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int param4, boolean param5) {
		if (stack.getCurrentDurability() > 1 && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (Api.morphProgress(player.getCommandSenderName(), false) == 1.0F) {
				float StoredHealth = 0F;
				float MaxStoredHealth = getMaxStorage(stack);
				if (stack.getTagCompound() == null) {
					stack.setTagCompound(new NBTTagCompound());
				}
				try {
					StoredHealth = stack.getTagCompound().getFloat("Health Stored");
				} catch (NullPointerException e) {
					StoredHealth = 0F;
				}
				float PlayerHealth = player.getHealth();
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
			}
		}
	}
	
	public float getMaxStorage(ItemStack stack) {
		float Maximum = 0F;
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		if (stack.getTagCompound().hasKey("Maximum Health Stored")) {
			Maximum = stack.getTagCompound().getFloat("Maximum Health Stored");
		} else {
			if ((stack.getCurrentDurability() % 2) + 1 == 1) return 200F;
			if ((stack.getCurrentDurability() % 2) + 1 == 2) return 2000F;
		}
		return Maximum;
	}
	
	@Override public IIcon[] getIcons() {
		return itemIcons;
	}
}