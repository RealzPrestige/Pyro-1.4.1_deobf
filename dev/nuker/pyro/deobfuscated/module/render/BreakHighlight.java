//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.client.renderer.RenderGlobal;
import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class BreakHighlight extends Module
{
    public final Value<String> HighlightMode;
    public final Value<Float> ObsidianRed;
    public final Value<Float> ObsidianGreen;
    public final Value<Float> ObsidianBlue;
    public final Value<Float> ObsidianAlpha;
    public final Value<Boolean> DebugMsgs;
    private ArrayList<BlockPos> BlocksBeingBroken;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public BreakHighlight() {
        super("BreakHighlight", new String[] { "BreakHighlights" }, "Highlights the blocks being broken around you", "NONE", -1, ModuleType.RENDER);
        this.HighlightMode = new Value<String>("HighlightModes", new String[] { "HM" }, "Mode for highlighting blocks", "Full");
        this.ObsidianRed = new Value<Float>("Red", new String[] { "oRed" }, "Red for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianGreen = new Value<Float>("Green", new String[] { "oGreen" }, "Green for rendering", 1.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianBlue = new Value<Float>("Blue", new String[] { "oBlue" }, "Blue for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianAlpha = new Value<Float>("Alpha", new String[] { "oAlpha" }, "Alpha for rendering", 0.5f, 0.0f, 1.0f, 0.1f);
        this.DebugMsgs = new Value<Boolean>("Debug", new String[] { "Debug" }, "Allows for debugging this module", false);
        this.BlocksBeingBroken = new ArrayList<BlockPos>();
        SPacketBlockBreakAnim l_Packet;
        this.onServerPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                if (event.getPacket() instanceof SPacketBlockBreakAnim) {
                    l_Packet = (SPacketBlockBreakAnim)event.getPacket();
                    if (!this.BlocksBeingBroken.contains(l_Packet.getPosition()) && l_Packet.getProgress() > 0 && l_Packet.getProgress() <= 10) {
                        this.SendMessage(String.format("added: SPacketBlockBreakAnim %s %s %s", l_Packet.getBreakerId(), l_Packet.getPosition().toString(), l_Packet.getProgress()));
                        this.BlocksBeingBroken.add(l_Packet.getPosition());
                    }
                    else if (l_Packet.getProgress() <= 0 || l_Packet.getProgress() > 10) {
                        this.SendMessage(String.format("removed: SPacketBlockBreakAnim %s %s %s", l_Packet.getBreakerId(), l_Packet.getPosition().toString(), l_Packet.getProgress()));
                        this.BlocksBeingBroken.remove(l_Packet.getPosition());
                    }
                }
                return;
            }
        });
        final Iterator<BlockPos> iterator;
        BlockPos l_Pos;
        AxisAlignedBB bb;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() == null) {
                return;
            }
            else {
                new ArrayList<BlockPos>(this.BlocksBeingBroken).iterator();
                while (iterator.hasNext()) {
                    l_Pos = iterator.next();
                    if (l_Pos == null) {
                        continue;
                    }
                    else if (this.mc.world.getBlockState(l_Pos).getBlock() == Blocks.AIR) {
                        this.BlocksBeingBroken.remove(l_Pos);
                    }
                    else {
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
                            GL11.glLineWidth(3.0f);
                            this.Render(bb, this.ObsidianRed.getValue(), this.ObsidianGreen.getValue(), this.ObsidianBlue.getValue(), this.ObsidianAlpha.getValue());
                            GL11.glDisable(2848);
                            GlStateManager.depthMask(true);
                            GlStateManager.enableDepth();
                            GlStateManager.enableTexture2D();
                            GlStateManager.disableBlend();
                            GlStateManager.popMatrix();
                        }
                        else {
                            continue;
                        }
                    }
                }
                return;
            }
        });
        this.HighlightMode.addString("None");
        this.HighlightMode.addString("FlatOutline");
        this.HighlightMode.addString("Flat");
        this.HighlightMode.addString("Outline");
        this.HighlightMode.addString("Full");
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.BlocksBeingBroken.clear();
    }
    
    public void SendMessage(final String p_Msg) {
        if (this.DebugMsgs.getValue()) {
            super.SendMessage(p_Msg);
        }
    }
    
    private void Render(final AxisAlignedBB bb, final float p_Red, final float p_Green, final float p_Blue, final float p_Alpha) {
        final String s = this.HighlightMode.getValue();
        switch (s) {
            case "Flag": {
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
