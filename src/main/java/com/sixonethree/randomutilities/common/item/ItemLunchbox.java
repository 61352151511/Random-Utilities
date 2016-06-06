package com.sixonethree.randomutilities.common.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.sixonethree.randomutilities.reference.NBTTagKeys;

public class ItemLunchbox extends ItemBase implements ILunchbox {
	String[] nameSuffixes = new String[] {"", "_auto"};
	
	/* Constructors */
	
	public ItemLunchbox() {
		super();
		this.setHasSubtypes(true);
		this.setNames("lunchbox");
	}
	
	/* Overridden */
	
	@Override @SideOnly(Side.CLIENT) public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.lunchbox.stores"));
		float storedFood = this.getCurrentFoodStorage(stack);
		float maximum = this.getMaxFoodStorage(stack);
		if (this.isLunchboxAutomatic(stack)) tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.lunchbox.auto"));
		tooltip.add(TextFormatting.GREEN + I18n.format("tooltip.lunchbox.fill"));
		String storedAsString = String.valueOf(storedFood / 2);
		String maximumStoredAsString = String.valueOf(maximum / 2);
		if (storedAsString.contains(".")) storedAsString = storedAsString.substring(0, storedAsString.indexOf(".") + 2);
		if (storedAsString.endsWith(".0")) storedAsString = storedAsString.replace(".0", "");
		if (maximumStoredAsString.endsWith(".0")) maximumStoredAsString = maximumStoredAsString.replace(".0", "");
		tooltip.add(I18n.format("tooltip.lunchbox.stored", storedAsString, maximumStoredAsString));
	}
	
	@Override public EnumAction getItemUseAction(ItemStack stack) {
		return this.isLunchboxAutomatic(stack) ? EnumAction.NONE : EnumAction.EAT;
	}
	
	@Override @SideOnly(Side.CLIENT) public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		subItems.add(new ItemStack(itemIn, 1, 0));
		subItems.add(new ItemStack(itemIn, 1, 1));
	}
	
	@Override public String getUnlocalizedName(ItemStack stack) {
		return stack.getItemDamage() < this.nameSuffixes.length ? super.getUnlocalizedName() + this.nameSuffixes[stack.getItemDamage()] : super.getUnlocalizedName();
	}
	
	@Override public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
	
	@Override public boolean hasEffect(ItemStack stack) {
		return stack.getItemDamage() == 1;
	}
	
	@Override public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.canEat(false)) {
			playerIn.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
	}
	
	@Override public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (!(entityLiving instanceof EntityPlayer)) return stack;
		EntityPlayer player = (EntityPlayer) entityLiving;
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
	
	@Override public boolean isLunchboxAutomatic(ItemStack stack) {
		return stack.getMetadata() == 1;
	}
	
	@Override public void setCurrentFoodStorage(ItemStack stack, float storage) {
		this.tagCompoundVerification(stack);
		stack.getTagCompound().setFloat(NBTTagKeys.CURRENT_FOOD_STORED, storage);
	}
}