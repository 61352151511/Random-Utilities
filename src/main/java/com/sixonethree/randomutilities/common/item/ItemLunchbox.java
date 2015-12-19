package com.sixonethree.randomutilities.common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.client.ColorLogic;
import com.sixonethree.randomutilities.reference.NBTTagKeys;
import com.sixonethree.randomutilities.utility.Utilities;

public class ItemLunchbox extends ItemBase implements ILunchbox {
	public ItemLunchbox() {
		super();
		setMaxStackSize(1);
		setUnlocalizedName("lunchbox");
		setFull3D();
		setHasSubtypes(true);
	}
	
	@Override public boolean hasEffect(ItemStack stack) {
		return stack.getItemDamage() == 1;
	}
	
	@Override @SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}
	
	@Override public String getUnlocalizedName(ItemStack stack) {
		String[] NameSuffixes = new String[] {"", "_auto"};
		return super.getUnlocalizedName() + NameSuffixes[stack.getItemDamage()];
	}
	
	@Override public void onUpdate(ItemStack stack, World world, Entity entity, int param4, boolean param5) {
		if (stack.getItemDamage() == 1 && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			int PlayerFood = player.getFoodStats().getFoodLevel();
			if (PlayerFood < 20) {
				int StoredFood = (int) getCurrentFoodStorage(stack);
				int FoodToGive = 20 - PlayerFood;
				if (FoodToGive > StoredFood) {
					FoodToGive = StoredFood;
				}
				player.getFoodStats().addStats(FoodToGive, FoodToGive > 0 ? 20F : 0F);
				stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_FOOD_STORED, StoredFood - FoodToGive);
			}
		}
	}
	
	@Override public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
		int PlayerFood = player.getFoodStats().getFoodLevel();
		if (PlayerFood < 20) {
			int StoredFood = (int) getCurrentFoodStorage(stack);
			int FoodToGive = 20 - PlayerFood;
			if (FoodToGive > StoredFood) {
				FoodToGive = StoredFood;
			}
			player.getFoodStats().addStats(FoodToGive, FoodToGive > 0 ? 20F : 0F);
			stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_FOOD_STORED, StoredFood - FoodToGive);
		}
		return stack;
	}
	
	@Override @SuppressWarnings({"rawtypes", "unchecked"}) @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("tooltip.lunchbox.stores"));
		float StoredFood = getCurrentFoodStorage(stack);
		float Maximum = getMaxFoodStorage(stack);
		if (stack.getItemDamage() == 1) list.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("tooltip.lunchbox.auto"));
		list.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("tooltip.lunchbox.fill"));
		String StoredAsString = String.valueOf(StoredFood / 2);
		String MaximumStoredAsString = String.valueOf(Maximum / 2);
		if (StoredAsString.contains(".")) StoredAsString = StoredAsString.substring(0, StoredAsString.indexOf(".") + 2);
		if (StoredAsString.endsWith(".0")) StoredAsString = StoredAsString.replace(".0", "");
		if (MaximumStoredAsString.endsWith(".0")) MaximumStoredAsString = MaximumStoredAsString.replace(".0", "");
		list.add(Utilities.translateFormatted("tooltip.lunchbox.stored", StoredAsString, MaximumStoredAsString));
	}
	
	@Override @SideOnly(Side.CLIENT) public int getColorFromItemStack(ItemStack stack, int pass) {
		return pass == 0 ? 0xFFFFFF : ColorLogic.getColorFromMeta(getColor(stack));
	}
	
	@Override public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
	
	@Override public EnumAction getItemUseAction(ItemStack stack) {
		return stack.getItemDamage() == 0 ? EnumAction.EAT : EnumAction.NONE;
	}
	
	@Override public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.canEat(false)) player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
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