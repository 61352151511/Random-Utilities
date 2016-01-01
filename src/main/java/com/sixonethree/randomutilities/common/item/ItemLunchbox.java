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
	String[] nameSuffixes = new String[] {"", "_auto"};
	
	public ItemLunchbox() {
		super();
		this.setUnlocalizedName("lunchbox");
		this.setFull3D();
		this.setHasSubtypes(true);
	}
	
	@Override @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
		list.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("tooltip.lunchbox.stores"));
		float storedFood = this.getCurrentFoodStorage(stack);
		float maximum = this.getMaxFoodStorage(stack);
		if (stack.getItemDamage() == 1) list.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("tooltip.lunchbox.auto"));
		list.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("tooltip.lunchbox.fill"));
		String storedAsString = String.valueOf(storedFood / 2);
		String maximumStoredAsString = String.valueOf(maximum / 2);
		if (storedAsString.contains(".")) storedAsString = storedAsString.substring(0, storedAsString.indexOf(".") + 2);
		if (storedAsString.endsWith(".0")) storedAsString = storedAsString.replace(".0", "");
		if (maximumStoredAsString.endsWith(".0")) maximumStoredAsString = maximumStoredAsString.replace(".0", "");
		list.add(Utilities.translateFormatted("tooltip.lunchbox.stored", storedAsString, maximumStoredAsString));
	}
	
	@Override @SideOnly(Side.CLIENT) public int getColorFromItemStack(ItemStack stack, int pass) { return pass == 0 ? 0xFFFFFF : ColorLogic.getColorFromMeta(getColor(stack)); }
	@Override public EnumAction getItemUseAction(ItemStack stack) { return stack.getItemDamage() == 0 ? EnumAction.EAT : EnumAction.NONE; }
	
	@Override @SideOnly(Side.CLIENT) public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}
	
	@Override public String getUnlocalizedName(ItemStack stack) { return super.getUnlocalizedName() + this.nameSuffixes[stack.getItemDamage()]; }
	@Override public int getMaxItemUseDuration(ItemStack stack) { return 32; }
	@Override public boolean hasEffect(ItemStack stack) { return stack.getItemDamage() == 1; }
	
	@Override public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.canEat(false)) player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}
	
	@Override public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
		int playerFood = player.getFoodStats().getFoodLevel();
		if (playerFood < 20) {
			int storedFood = (int) this.getCurrentFoodStorage(stack);
			int foodToGive = 20 - playerFood;
			if (foodToGive > storedFood) {
				foodToGive = storedFood;
			}
			player.getFoodStats().addStats(foodToGive, foodToGive > 0 ? 20F : 0F);
			stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_FOOD_STORED, storedFood - foodToGive);
		}
		return stack;
	}
	
	@Override public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (stack.getItemDamage() == 1 && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			int playerFood = player.getFoodStats().getFoodLevel();
			if (playerFood < 20) {
				int storedFood = (int) this.getCurrentFoodStorage(stack);
				int foodToGive = 20 - playerFood;
				if (foodToGive > storedFood) {
					foodToGive = storedFood;
				}
				player.getFoodStats().addStats(foodToGive, foodToGive > 0 ? 20F : 0F);
				stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_FOOD_STORED, storedFood - foodToGive);
			}
		}
	}
	
	/* ILunchbox */
	
	@Override public int getColor(ItemStack stack) { return this.tagOrDefault(stack, NBTTagKeys.COLOR, 16); }
	@Override public float getCurrentFoodStorage(ItemStack stack) { return this.tagOrDefault(stack, NBTTagKeys.CURRENT_FOOD_STORED, 0F); }
	@Override public float getMaxFoodStorage(ItemStack stack) { return this.tagOrDefault(stack, NBTTagKeys.MAX_FOOD_STORED, 200F); }
	@Override public boolean hasColor(ItemStack stack) {
		tagCompoundVerification(stack);
		return stack.getTagCompound().hasKey(NBTTagKeys.COLOR);
	}
}