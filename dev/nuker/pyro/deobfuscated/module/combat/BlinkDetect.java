//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import net.minecraft.world.World;
import net.minecraft.network.play.server.SPacketEntity;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.managers.FontManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import java.util.concurrent.CopyOnWriteArrayList;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import java.util.HashMap;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class BlinkDetect extends Module
{
    public final Value<Float> Scaling;
    private HashMap<EntityPlayer, Long> lastMovePacketTimes;
    private List<EntityPlayer> blinkers;
    @EventHandler
    private Listener<EventPlayerUpdate> onUpdate;
    @EventHandler
    private Listener<RenderEvent> OnRenderGameOverlay;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    
    public BlinkDetect() {
        super("BlinkDetect", new String[] { "BlinkDetector" }, "Highlights players that are blinking", "NONE", -1, ModuleType.COMBAT);
        this.Scaling = new Value<Float>("Scaling", new String[] { "" }, "Scaling", 3.0f, 1.0f, 10.0f, 1.0f);
        this.lastMovePacketTimes = new HashMap<EntityPlayer, Long>();
        this.blinkers = new CopyOnWriteArrayList<EntityPlayer>();
        final long now;
        long lastTime;
        final long l;
        long diff;
        this.onUpdate = new Listener<EventPlayerUpdate>(event -> {
            now = System.currentTimeMillis();
            this.blinkers.removeIf(p -> p == null || p.getDistance((Entity)this.mc.player) > 50.0f);
            this.lastMovePacketTimes.keySet().removeIf(p -> p == null);
            this.mc.world.playerEntities.forEach(p -> {
                if (!(p instanceof EntityPlayerSP)) {
                    if (this.lastMovePacketTimes.containsKey(p)) {
                        lastTime = this.lastMovePacketTimes.get(p);
                        diff = l - lastTime;
                        if (diff >= 1000L) {
                            if (!this.blinkers.contains(p)) {
                                this.blinkers.add(p);
                            }
                        }
                        else {
                            this.blinkers.remove(p);
                        }
                    }
                    else {
                        this.lastMovePacketTimes.put(p, l);
                    }
                }
            });
            return;
        });
        final Iterator<EntityPlayer> iterator;
        EntityPlayer player;
        Entity entity2;
        Vec3d pos;
        double n;
        double distance;
        double n2;
        double n3;
        Vec3d pos2;
        double posX;
        double posY;
        double posZ;
        double distance2;
        double scale;
        float n4;
        float n5;
        float n6;
        String nameTag;
        float width;
        float height;
        this.OnRenderGameOverlay = new Listener<RenderEvent>(event -> {
            if (this.mc.world == null || this.mc.renderEngine == null || this.mc.getRenderManager() == null || this.mc.getRenderManager().options == null) {
                return;
            }
            else {
                this.blinkers.iterator();
                while (iterator.hasNext()) {
                    player = iterator.next();
                    entity2 = this.mc.getRenderViewEntity();
                    pos = MathUtil.interpolateEntityClose((Entity)player, event.getPartialTicks());
                    n = pos.x;
                    distance = pos.y + 0.65;
                    n2 = pos.z;
                    n3 = distance + (player.isSneaking() ? 0.0 : 0.07999999821186066) - 0.3;
                    pos2 = MathUtil.interpolateEntityClose(entity2, event.getPartialTicks());
                    posX = entity2.posX;
                    posY = entity2.posY;
                    posZ = entity2.posZ;
                    entity2.posX = pos2.x;
                    entity2.posY = pos2.y;
                    entity2.posZ = pos2.z;
                    distance2 = entity2.getDistance(n, distance, n2);
                    scale = 0.04;
                    if (distance2 > 0.0) {
                        scale = 0.02 + this.Scaling.getValue() / 1000.0f * distance2;
                    }
                    GlStateManager.pushMatrix();
                    RenderHelper.enableStandardItemLighting();
                    GlStateManager.enablePolygonOffset();
                    GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
                    GlStateManager.disableLighting();
                    GlStateManager.translate((float)n, (float)n3 + 1.4f, (float)n2);
                    n4 = -this.mc.getRenderManager().playerViewY;
                    n5 = 1.0f;
                    n6 = 0.0f;
                    GlStateManager.rotate(n4, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(this.mc.getRenderManager().playerViewX, (this.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
                    GlStateManager.scale(-scale, -scale, scale);
                    GlStateManager.disableDepth();
                    GlStateManager.enableBlend();
                    nameTag = "possibly blinking!";
                    width = (float)(FontManager.Get().getGameFont().getStringWidth(nameTag) / 2);
                    height = (float)FontManager.Get().getGameFont().getHeight();
                    GlStateManager.enableBlend();
                    GlStateManager.disableBlend();
                    FontManager.Get().getGameFont().drawString(nameTag, -width + 1.0f, -height + 3.0f, 16711680);
                    GlStateManager.pushMatrix();
                    GlStateManager.popMatrix();
                    GlStateManager.enableDepth();
                    GlStateManager.disableBlend();
                    GlStateManager.disablePolygonOffset();
                    GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
                    GlStateManager.popMatrix();
                    entity2.posX = posX;
                    entity2.posY = posY;
                    entity2.posZ = posZ;
                }
                return;
            }
        });
        SPacketEntity.S15PacketEntityRelMove packet;
        Entity en;
        SPacketEntityHeadLook packet2;
        Entity en2;
        SPacketAnimation packet3;
        Entity en3;
        SPacketBlockBreakAnim packet4;
        Entity en4;
        this.onServerPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() == MinecraftEvent.Stage.Pre && this.mc.world != null) {
                if (event.getPacket() instanceof SPacketEntity.S15PacketEntityRelMove) {
                    packet = (SPacketEntity.S15PacketEntityRelMove)event.getPacket();
                    en = packet.getEntity((World)this.mc.world);
                    if (en != null && en instanceof EntityPlayer) {
                        if (packet.getX() != 0 || packet.getY() != 0 || packet.getZ() != 0 || packet.getPitch() != 0 || packet.getPitch() != 0) {
                            this.lastMovePacketTimes.put((EntityPlayer)en, System.currentTimeMillis());
                        }
                    }
                }
                else if (event.getPacket() instanceof SPacketEntityHeadLook) {
                    packet2 = (SPacketEntityHeadLook)event.getPacket();
                    en2 = packet2.getEntity((World)this.mc.world);
                    if (en2 != null && en2 instanceof EntityPlayer) {
                        this.lastMovePacketTimes.put((EntityPlayer)en2, System.currentTimeMillis());
                    }
                }
                else if (event.getPacket() instanceof SPacketAnimation) {
                    packet3 = (SPacketAnimation)event.getPacket();
                    en3 = this.mc.world.getEntityByID(packet3.getEntityID());
                    if (en3 != null && en3 instanceof EntityPlayer) {
                        this.lastMovePacketTimes.put((EntityPlayer)en3, System.currentTimeMillis());
                    }
                }
                else if (event.getPacket() instanceof SPacketBlockBreakAnim) {
                    packet4 = (SPacketBlockBreakAnim)event.getPacket();
                    en4 = this.mc.world.getEntityByID(packet4.getBreakerId());
                    if (en4 != null && en4 instanceof EntityPlayer) {
                        this.lastMovePacketTimes.put((EntityPlayer)en4, System.currentTimeMillis());
                    }
                }
            }
        });
    }
}
