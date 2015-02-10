package com.sixonethree.randomutilities.item;

import java.util.List;

import com.sixonethree.randomutilities.client.ColorLogic;
import com.sixonethree.randomutilities.utility.Utilities;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLunchbox extends ItemBase {
	public IIcon[] itemIcons = new IIcon[2];
	
	public ItemLunchbox() {
		super();
		setMaxStackSize(1);
		setUnlocalizedName("lunchbox");
		setFull3D();
		setHasSubtypes(true);
	}
	
	public boolean hasEffect(ItemStack stack) {
		return stack.getCurrentDurability() == 1;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		String[] NameSuffixes = new String[] {"", "_auto"};
		return super.getUnlocalizedName() + NameSuffixes[stack.getCurrentDurability()];
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int param4, boolean param5) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		if (!stack.getTagCompound().hasKey("Maximum Food Stored")) {
			stack.getTagCompound().setFloat("Maximum Food Stored", 200F);
		}
		if (stack.getCurrentDurability() == 1 && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			int PlayerFood = player.getFoodStats().getFoodLevel();
			if (PlayerFood < 20) {
				float StoredFoodA = 0F;
				if (stack.getTagCompound() == null) {
					stack.setTagCompound(new NBTTagCompound());
				}
				try {
					StoredFoodA = stack.getTagCompound().getFloat("Food Stored");
				} catch (NullPointerException e) {
					StoredFoodA = 0F;
				}
				int StoredFood = (int) Math.floor(StoredFoodA);
				int FoodToGive = 20 - PlayerFood;
				if (FoodToGive > StoredFood) {
					FoodToGive = StoredFood;
				}
				player.getFoodStats().addStats(FoodToGive, FoodToGive > 0 ? 20F : 0F);
				stack.getTagCompound().setFloat("Food Stored", StoredFood - FoodToGive);
			}
		}
	}
	
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
		int PlayerFood = player.getFoodStats().getFoodLevel();
		if (PlayerFood < 20) {
			float StoredFoodA = 0F;
			if (stack.getTagCompound() == null) {
				stack.setTagCompound(new NBTTagCompound());
			}
			try {
				StoredFoodA = stack.getTagCompound().getFloat("Food Stored");
			} catch (NullPointerException e) {}
			int StoredFood = (int) Math.floor(StoredFoodA);
			int FoodToGive = 20 - PlayerFood;
			if (FoodToGive > StoredFood) {
				FoodToGive = StoredFood;
			}
			player.getFoodStats().addStats(FoodToGive, FoodToGive > 0 ? 20F : 0F);
			stack.getTagCompound().setFloat("Food Stored", StoredFood - FoodToGive);
		}
		return stack;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.AQUA + Utilities.Translate("tooltip.lunchbox.stores"));
		float StoredFood = 0F;
		float Maximum = 200F;
		try {
			StoredFood = stack.getTagCompound().getFloat("Food Stored");
			Maximum = stack.getTagCompound().getFloat("Maximum Food Stored");
		} catch (NullPointerException e) {}
		if (stack.getCurrentDurability() == 1) {
			list.add(EnumChatFormatting.AQUA + Utilities.Translate("tooltip.lunchbox.auto"));
		}
		list.add(EnumChatFormatting.GREEN + Utilities.Translate("tooltip.lunchbox.fill"));
		String StoredAsString = String.valueOf(StoredFood / 2);
		String MaximumStoredAsString = String.valueOf(Maximum / 2);
		if (StoredAsString.contains(".")) {
			StoredAsString = StoredAsString.substring(0, StoredAsString.indexOf(".") + 2);
		}
		if (StoredAsString.endsWith(".0")) {
			StoredAsString = StoredAsString.replace(".0", "");
		}
		if (MaximumStoredAsString.endsWith(".0")) {
			MaximumStoredAsString = MaximumStoredAsString.replace(".0", "");
		}
		list.add(Utilities.TranslateFormatted("tooltip.lunchbox.stored", StoredAsString, MaximumStoredAsString));
	}
	
	public float getMaxStorage(ItemStack stack) {
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		return stack.getTagCompound().hasKey("Maximum Food Stored") ? stack.getTagCompound().getFloat("Maximum Food Stored") : 200F;
	}
	
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
	
	public EnumAction getItemUseAction(ItemStack stack) {
		return stack.getCurrentDurability() == 0 ? EnumAction.eat : EnumAction.none;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.canEat(false)) {
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
		}
		return stack;
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
			int Color = 16;
			if (stack.getTagCompound() != null) {
				if (stack.getTagCompound().hasKey("Color")) {
					Color = stack.getTagCompound().getInteger("Color");
				} else {
					Color = 16;
				}
			}
			return ColorLogic.getColorFromMeta(Color);
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
	
	@Override public IIcon[] getIcons() {
		return itemIcons;
	}
}