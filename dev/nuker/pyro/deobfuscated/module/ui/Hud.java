//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.ui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.util.glu.Project;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.managers.HudManager;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderHand;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderGetFOVModifier;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderGameOverlay;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class Hud extends Module
{
    public static final Value<Integer> ExtraTab;
    public final Value<Boolean> CustomFOV;
    public final Value<Float> FOV;
    public final Value<Boolean> NoHurtCam;
    public final Value<Boolean> NoBob;
    public final Value<Boolean> CustomFont;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventRenderGameOverlay> OnRenderGameOverlay;
    @EventHandler
    private Listener<EventRenderGetFOVModifier> OnGetFOVModifier;
    @EventHandler
    private Listener<EventRenderHand> OnRenderHand;
    
    public Hud() {
        super("HUD", new String[] { "HUD" }, "Displays the HUD", "NONE", 13753124, ModuleType.UI);
        this.CustomFOV = new Value<Boolean>("CustomFOV", new String[] { "CustomFOV" }, "Enables the option below", false);
        this.FOV = new Value<Float>("FOV", new String[] { "FOV" }, "Override the clientside FOV", this.mc.gameSettings.fovSetting, 0.0f, 170.0f, 10.0f);
        this.NoHurtCam = new Value<Boolean>("NoHurtCam", new String[] { "NoHurtCam" }, "Disables hurt camera effect", true);
        this.NoBob = new Value<Boolean>("NoBob", new String[] { "NoBob" }, "Disables bobbing effect", true);
        this.CustomFont = new Value<Boolean>("CustomFont", new String[] { "CF" }, "Displays the custom font", true);
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> HudManager.Get().Items.forEach(item -> {
            if (item.isEnabled()) {
                item.onUpdate();
            }
        }));
        this.OnRenderGameOverlay = new Listener<EventRenderGameOverlay>(event -> {
            if (!this.mc.gameSettings.showDebugInfo) {
                HudManager.Get().OnRender(event.getPartialTicks());
            }
            return;
        });
        this.OnGetFOVModifier = new Listener<EventRenderGetFOVModifier>(p_Event -> {
            if (!this.CustomFOV.getValue()) {
                return;
            }
            else {
                p_Event.cancel();
                p_Event.SetFOV(this.FOV.getValue());
                return;
            }
        });
        float f;
        boolean flag;
        this.OnRenderHand = new Listener<EventRenderHand>(p_Event -> {
            if (!(!this.CustomFOV.getValue())) {
                p_Event.cancel();
                GlStateManager.matrixMode(5889);
                GlStateManager.loadIdentity();
                f = 0.07f;
                if (this.mc.entityRenderer.mc.gameSettings.anaglyph) {
                    GlStateManager.translate(-(p_Event.Pass * 2 - 1) * 0.07f, 0.0f, 0.0f);
                }
                Project.gluPerspective(70.0f, this.mc.entityRenderer.mc.displayWidth / (float)this.mc.entityRenderer.mc.displayHeight, 0.05f, this.mc.entityRenderer.farPlaneDistance * 2.0f);
                GlStateManager.matrixMode(5888);
                GlStateManager.loadIdentity();
                if (this.mc.entityRenderer.mc.gameSettings.anaglyph) {
                    GlStateManager.translate((p_Event.Pass * 2 - 1) * 0.1f, 0.0f, 0.0f);
                }
                GlStateManager.pushMatrix();
                this.hurtCameraEffect(p_Event.PartialTicks);
                if (this.mc.entityRenderer.mc.gameSettings.viewBobbing) {
                    this.applyBobbing(p_Event.PartialTicks);
                }
                flag = (this.mc.entityRenderer.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.entityRenderer.mc.getRenderViewEntity()).isPlayerSleeping());
                if (!ForgeHooksClient.renderFirstPersonHand(this.mc.renderGlobal, p_Event.PartialTicks, p_Event.Pass) && this.mc.entityRenderer.mc.gameSettings.thirdPersonView == 0 && !flag && !this.mc.entityRenderer.mc.gameSettings.hideGUI && !this.mc.entityRenderer.mc.playerController.isSpectator()) {
                    this.mc.entityRenderer.enableLightmap();
                    this.mc.entityRenderer.itemRenderer.renderItemInFirstPerson(p_Event.PartialTicks);
                    this.mc.entityRenderer.disableLightmap();
                }
                GlStateManager.popMatrix();
                if (this.mc.entityRenderer.mc.gameSettings.thirdPersonView == 0 && !flag) {
                    this.mc.entityRenderer.itemRenderer.renderOverlays(p_Event.PartialTicks);
                    this.hurtCameraEffect(p_Event.PartialTicks);
                }
                if (this.mc.entityRenderer.mc.gameSettings.viewBobbing) {
                    this.applyBobbing(p_Event.PartialTicks);
                }
            }
        });
    }
    
    private void hurtCameraEffect(final float partialTicks) {
        if (this.NoHurtCam.getValue()) {
            return;
        }
        if (this.mc.getRenderViewEntity() instanceof EntityLivingBase) {
            final EntityLivingBase entitylivingbase = (EntityLivingBase)this.mc.getRenderViewEntity();
            float f = entitylivingbase.hurtTime - partialTicks;
            if (entitylivingbase.getHealth() <= 0.0f) {
                final float f2 = entitylivingbase.deathTime + partialTicks;
                GlStateManager.rotate(40.0f - 8000.0f / (f2 + 200.0f), 0.0f, 0.0f, 1.0f);
            }
            if (f < 0.0f) {
                return;
            }
            f /= entitylivingbase.maxHurtTime;
            f = MathHelper.sin(f * f * f * f * 3.1415927f);
            final float f3 = entitylivingbase.attackedAtYaw;
            GlStateManager.rotate(-f3, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-f * 14.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(f3, 0.0f, 1.0f, 0.0f);
        }
    }
    
    private void applyBobbing(final float partialTicks) {
        if (this.NoBob.getValue()) {
            return;
        }
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
            final float f = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
            final float f2 = -(entityplayer.distanceWalkedModified + f * partialTicks);
            final float f3 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * partialTicks;
            final float f4 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * partialTicks;
            GlStateManager.translate(MathHelper.sin(f2 * 3.1415927f) * f3 * 0.5f, -Math.abs(MathHelper.cos(f2 * 3.1415927f) * f3), 0.0f);
            GlStateManager.rotate(MathHelper.sin(f2 * 3.1415927f) * f3 * 3.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(Math.abs(MathHelper.cos(f2 * 3.1415927f - 0.2f) * f3) * 5.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(f4, 1.0f, 0.0f, 0.0f);
        }
    }
    
    static {
        ExtraTab = new Value<Integer>("ExtraTab", new String[] { "ET" }, "Max playerslots to show in the tab list", 80, 80, 1000, 10);
    }
}
