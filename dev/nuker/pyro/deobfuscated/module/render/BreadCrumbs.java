//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import java.util.Iterator;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import org.lwjgl.opengl.GL11;
import java.awt.Color;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import java.util.LinkedList;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class BreadCrumbs extends Module
{
    private Value<Boolean> Render;
    private Value<Float> Delay;
    private Value<Float> Width;
    private final LinkedList<double[]> positions;
    private Timer timer;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    
    public BreadCrumbs() {
        super("BreadCrumbs", new String[] { "BreadCrumbs", "BreadMan", "BreadManCrumbs", "Breads", "BreadyCrumbs" }, "Draws a path from the places you have gone through.", "NONE", -1, ModuleType.RENDER);
        this.Render = new Value<Boolean>("Render", new String[] { "Draw", "r" }, "Should this render or be silent", true);
        this.Delay = new Value<Float>("Delay", new String[] { "Delay", "Del", "d" }, "Delay in point generation", 0.0f, 0.0f, 10.0f, 1.0f);
        this.Width = new Value<Float>("Width", new String[] { "Width", "With", "Radius", "raidus" }, "Width of lines drawn", 1.6f, 0.1f, 10.0f, 1.0f);
        this.positions = new LinkedList<double[]>();
        this.timer = new Timer();
        Color color;
        double renderPosX;
        double renderPosY;
        double renderPosZ;
        final Iterator<double[]> iterator;
        double[] pos;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() == null || !this.Render.getValue()) {
                return;
            }
            else {
                color = new Color(255, 0, 72);
                synchronized (this.positions) {
                    GL11.glPushMatrix();
                    GL11.glDisable(3553);
                    GL11.glBlendFunc(770, 771);
                    GL11.glLineWidth((float)this.Width.getValue());
                    GL11.glEnable(2848);
                    GL11.glEnable(3042);
                    GL11.glDisable(2929);
                    this.mc.entityRenderer.disableLightmap();
                    GL11.glBegin(3);
                    RenderUtil.setColor(color);
                    renderPosX = this.mc.getRenderManager().viewerPosX;
                    renderPosY = this.mc.getRenderManager().viewerPosY;
                    renderPosZ = this.mc.getRenderManager().viewerPosZ;
                    this.positions.iterator();
                    while (iterator.hasNext()) {
                        pos = iterator.next();
                        GL11.glVertex3d(pos[0] - renderPosX, pos[1] - renderPosY, pos[2] - renderPosZ);
                    }
                    GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
                    GL11.glEnd();
                    GL11.glEnable(2929);
                    GL11.glDisable(2848);
                    GL11.glDisable(3042);
                    GL11.glEnable(3553);
                    GL11.glPopMatrix();
                }
                return;
            }
        });
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (!(!this.timer.passed(this.Delay.getValue() * 1000.0f))) {
                this.timer.reset();
                synchronized (this.positions) {
                    this.positions.add(new double[] { this.mc.player.posX, this.mc.player.getEntityBoundingBox().minY, this.mc.player.posZ });
                }
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.player == null) {
            return;
        }
        synchronized (this.positions) {
            this.positions.add(new double[] { this.mc.player.posX, this.mc.player.getEntityBoundingBox().minY + this.mc.player.getEyeHeight() * 0.5f, this.mc.player.posZ });
            this.positions.add(new double[] { this.mc.player.posX, this.mc.player.getEntityBoundingBox().minY, this.mc.player.posZ });
        }
    }
    
    @Override
    public void onDisable() {
        synchronized (this.positions) {
            this.positions.clear();
        }
        super.onDisable();
    }
}
