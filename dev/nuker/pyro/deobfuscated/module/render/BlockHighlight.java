//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class BlockHighlight extends Module
{
    public final Value<String> HighlightMode;
    public final Value<Float> ObsidianRed;
    public final Value<Float> ObsidianGreen;
    public final Value<Float> ObsidianBlue;
    public final Value<Float> ObsidianAlpha;
    public final Value<Integer> Width;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public BlockHighlight() {
        super("BlockHighlight", new String[] { "BlockHighlights" }, "Highlights the block you are looking at", "NONE", -1, ModuleType.RENDER);
        this.HighlightMode = new Value<String>("HighlightModes", new String[] { "HM" }, "Mode for highlighting blocks", "Full");
        this.ObsidianRed = new Value<Float>("Red", new String[] { "oRed" }, "Red for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianGreen = new Value<Float>("Green", new String[] { "oGreen" }, "Green for rendering", 1.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianBlue = new Value<Float>("Blue", new String[] { "oBlue" }, "Blue for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianAlpha = new Value<Float>("Alpha", new String[] { "oAlpha" }, "Alpha for rendering", 0.5f, 0.0f, 1.0f, 0.1f);
        this.Width = new Value<Integer>("Width", new String[] { "Width" }, "Width", 3, 1, 15, 1);
        RayTraceResult ray;
        BlockPos l_Pos;
        AxisAlignedBB bb;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() == null) {
                return;
            }
            else {
                ray = this.mc.objectMouseOver;
                if (ray == null) {
                    return;
                }
                else if (ray.typeOfHit != RayTraceResult.Type.BLOCK) {
                    return;
                }
                else {
                    l_Pos = ray.getBlockPos();
                    bb = new AxisAlignedBB(l_Pos.getX() - this.mc.getRenderManager().viewerPosX, l_Pos.getY() - this.mc.getRenderManager().viewerPosY, l_Pos.getZ() - this.mc.getRenderManager().viewerPosZ, l_Pos.getX() + 1 - this.mc.getRenderManager().viewerPosX, l_Pos.getY() + 1 - this.mc.getRenderManager().viewerPosY, l_Pos.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
                    RenderUtil.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
                    if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + this.mc.getRenderManager().viewerPosX, bb.minY + this.mc.getRenderManager().viewerPosY, bb.minZ + this.mc.getRenderManager().viewerPosZ, bb.maxX + this.mc.getRenderManager().viewerPosX, bb.maxY + this.mc.getRenderManager().viewerPosY, bb.maxZ + this.mc.getRenderManager().viewerPosZ))) {
                        GlStateManager.pushMatrix();
                        GlStateManager.enableBlend();
                        GlStateManager.disableDepth();
                        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                        GlStateManager.disableTexture2D();
                        GlStateManager.depthMask(false);
                        GL11.glEnable(2848);
                        GL11.glHint(3154, 4354);
                        GL11.glLineWidth((float)this.Width.getValue());
                        this.Render(bb, this.ObsidianRed.getValue(), this.ObsidianGreen.getValue(), this.ObsidianBlue.getValue(), this.ObsidianAlpha.getValue());
                        GL11.glDisable(2848);
                        GlStateManager.depthMask(true);
                        GlStateManager.enableDepth();
                        GlStateManager.enableTexture2D();
                        GlStateManager.disableBlend();
                        GlStateManager.popMatrix();
                    }
                    return;
                }
            }
        });
        this.HighlightMode.addString("FlatOutline");
        this.HighlightMode.addString("Flat");
        this.HighlightMode.addString("Outline");
        this.HighlightMode.addString("Full");
    }
    
    private void Render(final AxisAlignedBB bb, final float p_Red, final float p_Green, final float p_Blue, final float p_Alpha) {
        final String s = this.HighlightMode.getValue();
        switch (s) {
            case "Flat": {
                RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case "FlatOutline": {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case "Full": {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case "Outline": {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
        }
    }
}
