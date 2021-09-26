//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import dev.nuker.pyro.deobfuscated.util.render.GLUProjection;
import dev.nuker.pyro.deobfuscated.waypoints.Waypoint;
import dev.nuker.pyro.deobfuscated.waypoints.WaypointManager;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderGameOverlay;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import java.text.DecimalFormat;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Waypoints extends Module
{
    public final Value<Boolean> Normal;
    public final Value<Boolean> LogoutSpots;
    public final Value<Boolean> DeathPoints;
    public final Value<Boolean> CoordTPExploit;
    public final Value<Boolean> Tracers;
    public static final Value<Integer> RemoveDistance;
    private DecimalFormat _formatter;
    @EventHandler
    private Listener<EventRenderGameOverlay> OnRenderGameOverlay;
    @EventHandler
    private Listener<RenderEvent> OnRender;
    
    public Waypoints() {
        super("Waypoints", new String[] { "Waypoint" }, "Displays waypoints in your render", "NONE", -1, ModuleType.RENDER);
        this.Normal = new Value<Boolean>("Normal", new String[] { "Normal" }, "Displays normal waypoints", true);
        this.LogoutSpots = new Value<Boolean>("LogoutSpots", new String[] { "LogoutSpots" }, "Displays players LogoutSpots", true);
        this.DeathPoints = new Value<Boolean>("DeathPoints", new String[] { "DeathPoints" }, "Displays players DeathPoints", true);
        this.CoordTPExploit = new Value<Boolean>("CoordTPExploit", new String[] { "CoordTPExploit" }, "Displays waypoints created by CoordTPExploit", true);
        this.Tracers = new Value<Boolean>("Tracers", new String[] { "Tracers" }, "Points tracers to each waypoint", false);
        this._formatter = new DecimalFormat("#.#");
        final String currentAddress;
        final Iterator<Waypoint> iterator;
        Waypoint point;
        double x;
        double y;
        double z;
        GLUProjection.Projection projection;
        double distance;
        String displayName;
        GLUProjection.Projection projection2;
        this.OnRenderGameOverlay = new Listener<EventRenderGameOverlay>(event -> {
            currentAddress = ((Wrapper.GetMC().getCurrentServerData() != null) ? Wrapper.GetMC().getCurrentServerData().serverIP : "singleplayer");
            WaypointManager.Get().GetWaypoints().iterator();
            while (iterator.hasNext()) {
                point = iterator.next();
                if (!point.getAddress().equals(currentAddress)) {
                    continue;
                }
                else if (point.getType() == Waypoint.Type.Normal && !this.Normal.getValue()) {
                    continue;
                }
                else if (point.getType() == Waypoint.Type.Logout && !this.LogoutSpots.getValue()) {
                    continue;
                }
                else if (point.getType() == Waypoint.Type.Death && !this.DeathPoints.getValue()) {
                    continue;
                }
                else if (point.getType() == Waypoint.Type.CoordTPExploit && !this.CoordTPExploit.getValue()) {
                    continue;
                }
                else if (point.getDimension() == 1 && this.mc.player.dimension != 1) {
                    continue;
                }
                else if (this.mc.player.dimension == 1 && point.getDimension() != 1) {
                    continue;
                }
                else {
                    x = point.getX();
                    y = point.getY();
                    z = point.getZ();
                    if (point.getDimension() == -1 && this.mc.player.dimension == 0) {
                        x *= 8.0;
                        z *= 8.0;
                    }
                    else if (point.getDimension() == 0 && this.mc.player.dimension == -1) {
                        x /= 8.0;
                        z /= 8.0;
                    }
                    projection = GLUProjection.getInstance().project(x - this.mc.getRenderManager().viewerPosX, y - this.mc.getRenderManager().viewerPosY, z - this.mc.getRenderManager().viewerPosZ, GLUProjection.ClampMode.NONE, false);
                    if (projection != null && projection.getType() == GLUProjection.Projection.Type.INSIDE) {
                        distance = this.mc.player.getDistance(x, y, z);
                        displayName = point.getDisplayName() + " " + this._formatter.format(distance) + "m";
                        RenderUtil.drawStringWithShadow(displayName, (float)projection.getX() - RenderUtil.getStringWidth(displayName), (float)projection.getY() - RenderUtil.getStringHeight(displayName), -1);
                    }
                    if (this.Tracers.getValue()) {
                        projection2 = GLUProjection.getInstance().project(x - this.mc.getRenderManager().viewerPosX, y - this.mc.getRenderManager().viewerPosY, z - this.mc.getRenderManager().viewerPosZ, GLUProjection.ClampMode.NONE, true);
                        if (projection2 != null) {
                            RenderUtil.drawLine((float)projection2.getX(), (float)projection2.getY(), (float)(event.getScaledResolution().getScaledWidth() / 2), (float)(event.getScaledResolution().getScaledHeight() / 2), 0.5f, -1);
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                }
            }
            return;
        });
        final Iterator<String> iterator2;
        String uuid;
        WaypointManager.PlayerData data;
        this.OnRender = new Listener<RenderEvent>(event -> {
            if (this.mc.player != null && this.mc.getRenderManager() != null && this.mc.getRenderManager().renderViewEntity != null) {
                if (this.LogoutSpots.getValue()) {
                    WaypointManager.Get().getLogoutCache().keySet().iterator();
                    while (iterator2.hasNext()) {
                        uuid = iterator2.next();
                        data = WaypointManager.Get().getLogoutCache().get(uuid);
                        if (WaypointManager.Get().isOutOfRange(data)) {
                            WaypointManager.Get().RemoveLogoutCache(uuid);
                        }
                        else {
                            data.ghost.prevLimbSwingAmount = 0.0f;
                            data.ghost.limbSwing = 0.0f;
                            data.ghost.limbSwingAmount = 0.0f;
                            data.ghost.hurtTime = 0;
                            GlStateManager.pushMatrix();
                            GlStateManager.enableLighting();
                            GlStateManager.enableBlend();
                            GlStateManager.enableDepth();
                            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                            this.mc.getRenderManager().renderEntity((Entity)data.ghost, data.position.x - this.mc.getRenderManager().renderPosX, data.position.y - this.mc.getRenderManager().renderPosY, data.position.z - this.mc.getRenderManager().renderPosZ, data.ghost.rotationYaw, this.mc.getRenderPartialTicks(), false);
                            GlStateManager.disableLighting();
                            GlStateManager.disableBlend();
                            GlStateManager.popMatrix();
                        }
                    }
                }
            }
        });
    }
    
    static {
        RemoveDistance = new Value<Integer>("RemoveDistance", new String[] { "RD", "RemoveRange" }, "Minimum distance in blocks the player must be away from the spot for it to be removed.", 200, 1, 2000, 1);
    }
}
