package com.sixonethree.randomutilities.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import com.sixonethree.randomutilities.common.container.ContainerDisplayTable;
import com.sixonethree.randomutilities.common.container.ContainerMagicChest;

public class GuiManager extends GuiContainer {
	public enum ResourceList {
		MAGIC(new ResourceLocation("randomutilities", "textures/gui/magicchest.png")),
		DISPLAYTABLE(new ResourceLocation("randomutilities", "textures/gui/displaytable.png"));
		public final ResourceLocation location;
		
		private ResourceList(ResourceLocation loc) {
			this.location = loc;
		}
	}
	
	public enum GUI {
		MAGIC(176, 132, ResourceList.MAGIC), DISPLAYTABLE(212, 199, ResourceList.DISPLAYTABLE);
		
		private int xSize;
		private int ySize;
		private ResourceList guiResourceList;
		
		private GUI(int xSize, int ySize, ResourceList guiResourceList) {
			this.xSize = xSize;
			this.ySize = ySize;
			this.guiResourceList = guiResourceList;
		}
		
		protected Container makeContainer(GUI type, IInventory player, IInventory chest) {
			if (type == MAGIC) return new ContainerMagicChest(player, chest, xSize, ySize);
			if (type == DISPLAYTABLE) return new ContainerDisplayTable(player, chest, xSize, ySize);
			return null;
		}
		
		public static GuiManager buildGUI(GUI type, IInventory playerInventory, IInventory source) {
			return new GuiManager(type, playerInventory, source);
		}
	}
	
	private IInventory chest;
	private GUI type;
	
	private GuiManager(GUI type, IInventory player, IInventory chest) {
		super(type.makeContainer(type, player, chest));
		this.chest = chest;
		this.type = type;
		this.xSize = type.xSize;
		this.ySize = type.ySize;
		this.allowUserInput = false;
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		if (this.type == GUI.MAGIC) this.fontRendererObj.drawString(I18n.format(this.chest.getDisplayName().getFormattedText(), new Object[0]), 8, 6, 4210752);
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GlStateManager.color(1, 1, 1, 1);
		this.mc.getTextureManager().bindTexture(type.guiResourceList.location);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}