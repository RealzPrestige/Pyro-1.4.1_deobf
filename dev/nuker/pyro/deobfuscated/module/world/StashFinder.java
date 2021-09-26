//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import java.util.List;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import dev.nuker.pyro.deobfuscated.util.HilbertCurve;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class StashFinder extends Module
{
    public final Value<String> PathType;
    public final Value<Boolean> Loop;
    public final Value<Integer> Curve;
    public final Value<Integer> Spacer;
    public final Value<Boolean> Render;
    public final Value<Boolean> viewLock;
    public final Value<Boolean> automaticWalk;
    public final Value<Integer> restarterdistance;
    public final Value<Boolean> ToggleLog;
    private ArrayList<BlockPos> WaypointPath;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public StashFinder() {
        super("StashFinder", new String[] { "BaseFinder" }, "Automatically pilots you towards generated waypoints", "NONE", -1, ModuleType.WORLD);
        this.PathType = new Value<String>("Mode", new String[] { "M" }, "Mode of surrounding to use", "Normal");
        this.Loop = new Value<Boolean>("Loop", new String[] { "Loop" }, "Loops after a finish", false);
        this.Curve = new Value<Integer>("Curve", new String[] { "Curves" }, "Curves to use for hilbert curve, more = bigger path", 5, 1, 5, 1);
        this.Spacer = new Value<Integer>("Spacers", new String[] { "Space Between loops" }, "Spacer to use for etbes spiral, more = bigger path", 250, 1, 1000, 10);
        this.Render = new Value<Boolean>("Render Waypoints", new String[] { "Render Waypoints" }, "Renders the path", true);
        this.viewLock = new Value<Boolean>("viewLock", new String[] { "viewLock" }, "viewLock", true);
        this.automaticWalk = new Value<Boolean>("automaticWalk", new String[] { "automaticWalk" }, "automaticWalk", false);
        this.restarterdistance = new Value<Integer>("restarterdistance", new String[] { "restarterdistance" }, "restarterdistance to the south", 0, 1, 10000, 100);
        this.ToggleLog = new Value<Boolean>("ToggleStashLogger", new String[] { "ToggleLog" }, "Automatically toggles on StashLogger if not already enabled", true);
        this.WaypointPath = new ArrayList<BlockPos>();
        BlockPos first;
        double[] rotations;
        int order;
        int n;
        List<HilbertCurve.Point> points;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (!this.automaticWalk.getValue() && !PyroStatic.AUTOARMOR.isEnabled() && PyroStatic.AUTOWALK.isEnabled()) {
                PyroStatic.AUTOWALK.toggle();
            }
            if (this.automaticWalk.getValue() && !PyroStatic.AUTOARMOR.isEnabled()) {
                PyroStatic.AUTOARMOR.toggle();
            }
            if (PyroStatic.AUTOARMOR.isEnabled() && !this.automaticWalk.getValue()) {
                PyroStatic.AUTOARMOR.toggle();
            }
            if (!this.WaypointPath.isEmpty()) {
                first = this.WaypointPath.get(0);
                rotations = EntityUtil.calculateLookAt(first.getX() + 0.5, first.getY() - 0.5, first.getZ() + 0.5, (EntityPlayer)this.mc.player);
                if (this.viewLock.getValue()) {
                    this.mc.player.rotationYaw = (float)rotations[0];
                }
                if (this.getDistance2D(first) < 10.0) {
                    this.WaypointPath.remove(first);
                }
            }
            else if (this.Loop.getValue()) {
                order = this.Curve.getValue();
                n = 1 << order;
                points = HilbertCurve.getPointsForCurve(n);
                this.WaypointPath.clear();
                points.forEach(p -> this.WaypointPath.add(new BlockPos((int)this.mc.player.posX + p.x * 16 * 8, 165, (int)this.mc.player.posZ + p.y * 16 * 8)));
            }
            return;
        });
        AxisAlignedBB bb;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() == null || !this.Render.getValue()) {
                return;
            }
            else {
                new ArrayList(this.WaypointPath).forEach(pos -> {
                    bb = new AxisAlignedBB(pos.getX() - this.mc.getRenderManager().viewerPosX, (double)(pos.getY() - 1000), pos.getZ() - this.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - this.mc.getRenderManager().viewerPosX, (double)(pos.getY() + 1000), pos.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
                    RenderUtil.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
                    if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + this.mc.getRenderManager().viewerPosX, bb.minY + this.mc.getRenderManager().viewerPosY, bb.minZ + this.mc.getRenderManager().viewerPosZ, bb.maxX + this.mc.getRenderManager().viewerPosX, bb.maxY + this.mc.getRenderManager().viewerPosY, bb.maxZ + this.mc.getRenderManager().viewerPosZ)) && this.Render.getValue()) {
                        GlStateManager.pushMatrix();
                        GlStateManager.enableBlend();
                        GlStateManager.disableDepth();
                        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                        GlStateManager.disableTexture2D();
                        GlStateManager.depthMask(false);
                        GL11.glEnable(2848);
                        GL11.glHint(3154, 4354);
                        GL11.glLineWidth(3.0f);
                        RenderUtil.drawBoundingBox(bb, 1.5f, -1717986919);
                        RenderUtil.drawFilledBox(bb, 1361266023);
                        GL11.glDisable(2848);
                        GlStateManager.depthMask(true);
                        GlStateManager.enableDepth();
                        GlStateManager.enableTexture2D();
                        GlStateManager.disableBlend();
                        GlStateManager.popMatrix();
                    }
                });
                return;
            }
        });
        this.PathType.addString("Spiral");
        this.PathType.addString("Spiral Restart");
        this.PathType.addString("HildebrantCurve");
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        if (this.automaticWalk.getValue() && !PyroStatic.AUTOARMOR.isEnabled()) {
            PyroStatic.AUTOARMOR.toggle();
        }
        if (!this.automaticWalk.getValue() && PyroStatic.AUTOARMOR.isEnabled()) {
            PyroStatic.AUTOARMOR.toggle();
        }
        if (!this.automaticWalk.getValue() && PyroStatic.AUTOWALK.isEnabled()) {
            PyroStatic.AUTOWALK.toggle();
        }
        super.onEnable();
        this.WaypointPath.clear();
        int x = 1;
        int counter = 0;
        final int spacerlocal = this.Spacer.getValue();
        final int restarterdistancelocal = this.restarterdistance.getValue();
        if (this.ToggleLog.getValue() && !PyroStatic.STASHLOGGER.isEnabled()) {
            PyroStatic.STASHLOGGER.toggle();
        }
        if (this.PathType.getValue().equals("HildebrantCurve")) {
            final int order = this.Curve.getValue();
            final int n = 1 << order;
            final List<HilbertCurve.Point> points = HilbertCurve.getPointsForCurve(n);
            this.WaypointPath.clear();
            points.forEach(p -> this.WaypointPath.add(new BlockPos((int)this.mc.player.posX + p.x * 16 * 8, 222, (int)this.mc.player.posZ + p.y * 16 * 8)));
            this.SendMessage("Turn on AutoWalk and StashLogger to begin!");
        }
        if (this.PathType.getValue().equals("Spiral")) {
            for (int i = 0; i < 998; ++i) {
                if (counter == 0) {
                    this.WaypointPath.add(new BlockPos(x + this.mc.player.posX, 222.0, x + this.mc.player.posZ));
                    ++counter;
                }
                if (counter == 1) {
                    this.WaypointPath.add(new BlockPos(this.mc.player.posX - x, 222.0, this.mc.player.posZ + x));
                    ++counter;
                }
                if (counter == 2) {
                    this.WaypointPath.add(new BlockPos(this.mc.player.posX - x, 222.0, this.mc.player.posZ - x));
                    ++counter;
                }
                if (counter == 3) {
                    this.WaypointPath.add(new BlockPos(this.mc.player.posX + x, 222.0, this.mc.player.posZ - x));
                    x += spacerlocal;
                    counter = 0;
                }
            }
            ++counter;
        }
        if (this.PathType.getValue().equals("Spiral Restart")) {
            for (int i = 0; i < 999; ++i) {
                if (counter == 3) {
                    this.WaypointPath.add(new BlockPos(x + this.mc.player.posX + restarterdistancelocal, 222.0, x + this.mc.player.posZ + restarterdistancelocal - restarterdistancelocal));
                    counter = 0;
                }
                if (counter == 0) {
                    this.WaypointPath.add(new BlockPos(this.mc.player.posX - x - restarterdistancelocal, 222.0, this.mc.player.posZ + x + restarterdistancelocal - restarterdistancelocal));
                    ++counter;
                }
                if (counter == 1) {
                    this.WaypointPath.add(new BlockPos(this.mc.player.posX - x - restarterdistancelocal, 222.0, this.mc.player.posZ - x - restarterdistancelocal - restarterdistancelocal));
                    ++counter;
                }
                if (counter == 2) {
                    this.WaypointPath.add(new BlockPos(this.mc.player.posX + x + restarterdistancelocal, 222.0, this.mc.player.posZ - x - restarterdistancelocal - restarterdistancelocal));
                    x += spacerlocal;
                    ++counter;
                }
            }
            ++counter;
        }
        x = 1;
    }
    
    private double getDistance2D(final BlockPos pos) {
        final double posX = Math.abs(this.mc.player.posX - pos.getX());
        final double posZ = Math.abs(this.mc.player.posZ - pos.getZ());
        return posX + posZ;
    }
}
