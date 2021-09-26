//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.MobEffects;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import java.util.concurrent.CopyOnWriteArrayList;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import dev.nuker.pyro.deobfuscated.module.Module;

public class StrengthESP extends Module
{
    public List<EntityPlayer> strengthedPlayers;
    private Timer timer;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public StrengthESP() {
        super("StrengthESP", new String[] { "StrengthESP" }, "Renders an ESP around strengthed players", "NONE", -1, ModuleType.RENDER);
        this.strengthedPlayers = new CopyOnWriteArrayList<EntityPlayer>();
        this.timer = new Timer();
        final Iterator<EntityPlayer> iterator;
        EntityPlayer ent;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (!this.timer.passed(1000.0)) {
                return;
            }
            else {
                this.timer.reset();
                this.mc.world.playerEntities.iterator();
                while (iterator.hasNext()) {
                    ent = iterator.next();
                    if (EntityUtil.isLiving((Entity)ent) && ent.getHealth() > 0.0f) {
                        if (ent == this.mc.player) {
                            continue;
                        }
                        else if (this.strengthedPlayers.contains(ent) && !ent.isPotionActive(MobEffects.STRENGTH)) {
                            this.SendMessage("" + ent.getDisplayNameString() + "] no longer has strength.");
                            this.strengthedPlayers.remove(ent);
                        }
                        else {
                            if (!ent.isPotionActive(MobEffects.STRENGTH)) {
                                this.SendMessage(ent.getName() + " doesn't have strength. " + ent.getActivePotionMap().size());
                            }
                            if (ent.isPotionActive(MobEffects.STRENGTH) && !this.strengthedPlayers.contains(ent)) {
                                this.SendMessage("" + ent.getDisplayNameString() + "] now has strength");
                                this.strengthedPlayers.add(ent);
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
                return;
            }
        });
        boolean isThirdPersonFrontal;
        float viewerYaw;
        Vec3d pos;
        final float n;
        final boolean b;
        this.OnRenderEvent = new Listener<RenderEvent>(event -> {
            if (this.mc.world != null && this.mc.renderEngine != null && this.mc.getRenderManager() != null && this.mc.getRenderManager().options != null) {
                isThirdPersonFrontal = (this.mc.getRenderManager().options.thirdPersonView == 2);
                viewerYaw = this.mc.getRenderManager().playerViewY;
                this.strengthedPlayers.forEach(e -> {
                    GlStateManager.pushMatrix();
                    pos = EntityUtil.getInterpolatedPos(e, event.getPartialTicks());
                    GlStateManager.translate(pos.x - this.mc.getRenderManager().renderPosX, pos.y - this.mc.getRenderManager().renderPosY, pos.z - this.mc.getRenderManager().renderPosZ);
                    GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-n, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(b ? -1.0f : 1.0f, 1.0f, 0.0f, 0.0f);
                    GlStateManager.disableLighting();
                    GlStateManager.depthMask(false);
                    GlStateManager.disableDepth();
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    GL11.glColor3f(1.0f, 0.2f, 0.2f);
                    GlStateManager.disableTexture2D();
                    GL11.glLineWidth(4.0f);
                    GL11.glEnable(2848);
                    GL11.glBegin(2);
                    GL11.glVertex2d((double)(-((EntityPlayer)e).width / 2.0f), 0.0);
                    GL11.glVertex2d((double)(-((EntityPlayer)e).width / 2.0f), (double)((EntityPlayer)e).height);
                    GL11.glVertex2d((double)(((EntityPlayer)e).width / 2.0f), (double)((EntityPlayer)e).height);
                    GL11.glVertex2d((double)(((EntityPlayer)e).width / 2.0f), 0.0);
                    GL11.glEnd();
                    GlStateManager.popMatrix();
                    return;
                });
                GlStateManager.enableDepth();
                GlStateManager.depthMask(true);
                GlStateManager.disableTexture2D();
                GlStateManager.enableBlend();
                GlStateManager.disableAlpha();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.shadeModel(7425);
                GlStateManager.disableDepth();
                GlStateManager.enableCull();
                GlStateManager.glLineWidth(1.0f);
                GL11.glColor3f(1.0f, 1.0f, 1.0f);
            }
        });
    }
}
