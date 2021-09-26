//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntityChest;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import dev.nuker.pyro.deobfuscated.module.Value;
import java.awt.Color;
import dev.nuker.pyro.deobfuscated.module.Module;

public class StorageESP extends Module
{
    private Color chestColor;
    private Color enderChestColor;
    private Color dispenserColor;
    private Color furnaceColor;
    private Color hopperColor;
    private Color shulkerColor;
    private Color genericColor;
    public final Value<Boolean> chest;
    public final Value<Boolean> trappedChest;
    public final Value<Boolean> enderChest;
    public final Value<Boolean> dispenser;
    public final Value<Boolean> dropper;
    public final Value<Boolean> hopper;
    public final Value<Boolean> furnace;
    public final Value<Boolean> Shulkers;
    public final Value<Float> Width;
    
    public StorageESP() {
        super("StorageESP", new String[] { "" }, "Highlights different kind of storages", "NONE", -1, ModuleType.RENDER);
        this.chestColor = new Color(255, 252, 99);
        this.enderChestColor = new Color(166, 0, 238);
        this.dispenserColor = new Color(192, 192, 192);
        this.furnaceColor = new Color(192, 192, 192);
        this.hopperColor = new Color(167, 167, 167);
        this.shulkerColor = new Color(255, 0, 151);
        this.genericColor = new Color(30, 255, 40);
        this.chest = new Value<Boolean>("Chests", new String[] { "S" }, "Highlights Chests", true);
        this.trappedChest = new Value<Boolean>("TrappedChests", new String[] { "S" }, "Highlights Chests", true);
        this.enderChest = new Value<Boolean>("EnderChests", new String[] { "S" }, "Highlights EnderChests", true);
        this.dispenser = new Value<Boolean>("Dispenser", new String[] { "S" }, "Highlights Chests", true);
        this.dropper = new Value<Boolean>("Dropper", new String[] { "S" }, "Highlights Chests", true);
        this.hopper = new Value<Boolean>("Hopper", new String[] { "S" }, "Highlights Chests", true);
        this.furnace = new Value<Boolean>("Furnace", new String[] { "S" }, "Highlights Chests", true);
        this.Shulkers = new Value<Boolean>("Shulkers", new String[] { "S" }, "Highlights Shulkers", true);
        this.Width = new Value<Float>("Width", new String[] { "Width" }, "Highlights Width", 3.0f, 0.0f, 10.0f, 1.0f);
    }
    
    public void render(final TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer, final TileEntity tileEntityIn, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float p_192854_10_) {
        final AxisAlignedBB bb = new AxisAlignedBB(tileEntityIn.getPos().getX() - this.mc.getRenderManager().viewerPosX, tileEntityIn.getPos().getY() - this.mc.getRenderManager().viewerPosY, tileEntityIn.getPos().getZ() - this.mc.getRenderManager().viewerPosZ, tileEntityIn.getPos().getX() + 1 - this.mc.getRenderManager().viewerPosX, tileEntityIn.getPos().getY() + 1 - this.mc.getRenderManager().viewerPosY, tileEntityIn.getPos().getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
        RenderUtil.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
        if (!RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + this.mc.getRenderManager().viewerPosX, bb.minY + this.mc.getRenderManager().viewerPosY, bb.minZ + this.mc.getRenderManager().viewerPosZ, bb.maxX + this.mc.getRenderManager().viewerPosX, bb.maxY + this.mc.getRenderManager().viewerPosY, bb.maxZ + this.mc.getRenderManager().viewerPosZ))) {
            return;
        }
        if ((tileEntityIn instanceof TileEntityChest && this.chest.getValue()) || (tileEntityIn instanceof TileEntityShulkerBox && this.Shulkers.getValue()) || (tileEntityIn instanceof TileEntityEnderChest && this.enderChest.getValue()) || (tileEntityIn instanceof TileEntityChest && this.chest.getValue()) || (tileEntityIn instanceof TileEntityDispenser && this.dispenser.getValue()) || (tileEntityIn instanceof TileEntityDropper && this.dropper.getValue()) || (tileEntityIn instanceof TileEntityHopper && this.hopper.getValue()) || (tileEntityIn instanceof TileEntityFurnace && this.furnace.getValue())) {
            final Color n = this.getColor(tileEntityIn);
            GlStateManager.pushMatrix();
            RenderUtil.setColor(n);
            tileentityspecialrenderer.render(tileEntityIn, x, y, z, partialTicks, destroyStage, p_192854_10_);
            RenderUtil.renderOne(this.Width.getValue());
            tileentityspecialrenderer.render(tileEntityIn, x, y, z, partialTicks, destroyStage, p_192854_10_);
            RenderUtil.renderTwo();
            tileentityspecialrenderer.render(tileEntityIn, x, y, z, partialTicks, destroyStage, p_192854_10_);
            RenderUtil.renderThree();
            RenderUtil.renderFour();
            RenderUtil.setColor(n);
            tileentityspecialrenderer.render(tileEntityIn, x, y, z, partialTicks, destroyStage, p_192854_10_);
            RenderUtil.renderFive();
            RenderUtil.setColor(Color.WHITE);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }
    
    private Color getColor(final TileEntity en) {
        if (en instanceof TileEntityShulkerBox) {
            return this.shulkerColor;
        }
        if (en instanceof TileEntityChest) {
            return this.chestColor;
        }
        if (en instanceof TileEntityEnderChest) {
            return this.enderChestColor;
        }
        if (en instanceof TileEntityFurnace) {
            return this.furnaceColor;
        }
        if (en instanceof TileEntityHopper) {
            return this.hopperColor;
        }
        if (en instanceof TileEntityDispenser) {
            return this.dispenserColor;
        }
        if (en instanceof TileEntityDropper) {
            return this.dispenserColor;
        }
        return this.genericColor;
    }
}
