package com.sixonethree.randomutilities.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.client.ColorLogic;
import com.sixonethree.randomutilities.utility.Utilities;

public class ItemLunchbox extends ItemBase {
	public ItemLunchbox() {
		super();
		setMaxStackSize(1);
		setUnlocalizedName("lunchbox");
		setFull3D();
		setHasSubtypes(true);
	}
	
	public boolean hasEffect(ItemStack stack) {
		return stack.getItemDamage() == 1;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		String[] NameSuffixes = new String[] {"", "_auto"};
		return super.getUnlocalizedName() + NameSuffixes[stack.getItemDamage()];
	}
	
	public void onUpdate(ItemStack stack, World world, Entity entity, int param4, boolean param5) {
		if (stack.getItemDamage() == 1 && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			int PlayerFood = player.getFoodStats().getFoodLevel();
			if (PlayerFood < 20) {
				int StoredFood = (int) Math.floor(stack.hasTagCompound() ? stack.getTagCompound().getFloat("Food Stored") : 0F);
				int FoodToGive = 20 - PlayerFood;
				if (FoodToGive > StoredFood) {
					FoodToGive = StoredFood;
				}
				player.getFoodStats().addStats(FoodToGive, FoodToGive > 0 ? 20F : 0F);
				if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setFloat("Food Stored", StoredFood - FoodToGive);
			}
		}
	}
	
	@Override public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
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
		return stack;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.AQUA + Utilities.Translate("tooltip.lunchbox.stores"));
		float StoredFood = stack.hasTagCompound() ? stack.getTagCompound().hasKey("Food Stored") ? stack.getTagCompound().getFloat("Food Stored") : 0F : 0F;
		float Maximum = getMaxStorage(stack);
		if (stack.getItemDamage() == 1) list.add(EnumChatFormatting.AQUA + Utilities.Translate("tooltip.lunchbox.auto"));
		list.add(EnumChatFormatting.GREEN + Utilities.Translate("tooltip.lunchbox.fill"));
		String StoredAsString = String.valueOf(StoredFood / 2);
		String MaximumStoredAsString = String.valueOf(Maximum / 2);
		if (StoredAsString.contains(".")) StoredAsString = StoredAsString.substring(0, StoredAsString.indexOf(".") + 2);
		if (StoredAsString.endsWith(".0")) StoredAsString = StoredAsString.replace(".0", "");
		if (MaximumStoredAsString.endsWith(".0")) MaximumStoredAsString = MaximumStoredAsString.replace(".0", "");
		list.add(Utilities.TranslateFormatted("tooltip.lunchbox.stored", StoredAsString, MaximumStoredAsString));
	}
	
	@Override @SideOnly(Side.CLIENT) public int getColorFromItemStack(ItemStack stack, int pass) {
		return pass == 0 ? 0xFFFFFF : ColorLogic.getColorFromMeta(stack.hasTagCompound() ? stack.getTagCompound().hasKey("Color") ? stack.getTagCompound().getInteger("Color") : 16 : 16);
	}
	
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
	
	public EnumAction getItemUseAction(ItemStack stack) {
		return stack.getItemDamage() == 0 ? EnumAction.EAT : EnumAction.NONE;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.canEat(false)) {
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
		}
		return stack;
	}
	
	public float getMaxStorage(ItemStack stack) {
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		return stack.getTagCompound().hasKey("Maximum Food Stored") ? stack.getTagCompound().getFloat("Maximum Food Stored") : 200F;
	}
}