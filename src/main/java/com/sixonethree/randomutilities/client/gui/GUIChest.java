package com.sixonethree.randomutilities.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.sixonethree.randomutilities.common.block.ContainerMagicChest;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;

public class GUIChest extends GuiContainer {
	public enum ResourceList {
		MAGIC(new ResourceLocation("randomutilities", "textures/gui/magicchest.png"));
		public final ResourceLocation location;
		
		private ResourceList(ResourceLocation loc) {
			this.location = loc;
		}
	}
	
	public enum GUI {
		MAGIC(176, 132, ResourceList.MAGIC);
		
		private int xSize;
		private int ySize;
		private ResourceList guiResourceList;
		
		private GUI(int xSize, int ySize, ResourceList guiResourceList) {
			this.xSize = xSize;
			this.ySize = ySize;
			this.guiResourceList = guiResourceList;
		}
		
		protected Container makeContainer(IInventory player, IInventory chest) {
			return new ContainerMagicChest(player, chest, xSize, ySize);
		}
		
		public static GUIChest buildGUI(IInventory playerInventory, TileEntityMagicChest chestInventory) {
			return new GUIChest(MAGIC, playerInventory, chestInventory);
		}
	}
	
	public int getRowLength() {
		return 1;
	}
	
	private GUI type;
	
	private GUIChest(GUI type, IInventory player, IInventory chest) {
		super(type.makeContainer(player, chest));
		this.type = type;
		this.xSize = type.xSize;
		this.ySize = type.ySize;
		this.allowUserInput = false;
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRendererObj.drawString(I18n.format("Magic Chest", new Object[0]), 8, 6, 4210752);
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		// new "bind tex"
		this.mc.getTextureManager().bindTexture(type.guiResourceList.location);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
