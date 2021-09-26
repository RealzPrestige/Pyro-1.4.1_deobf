//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.entity.item.EntityItem;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.model.ModelBase;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;
import org.lwjgl.opengl.GL11;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import java.util.UUID;
import java.util.HashMap;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class EntityESP extends Module
{
    public final Value<ESPMode> Mode;
    public final Value<Boolean> Players;
    public final Value<Boolean> Monsters;
    public final Value<Boolean> Animals;
    public final Value<Boolean> Vehicles;
    public final Value<Boolean> Others;
    public final Value<Boolean> Items;
    public final Value<Boolean> Tamed;
    public final Value<Float> Width;
    private final HashMap<UUID, String> _uuidToName;
    @EventHandler
    private Listener<RenderEvent> OnRenderGameOverlay;
    
    public EntityESP() {
        super("EntityESP", new String[] { "" }, "Highlights different kind of storages", "NONE", 15997844, ModuleType.RENDER);
        this.Mode = new Value<ESPMode>("Mode", new String[] { "ESPMode" }, "Mode of rendering to use for ESP", ESPMode.Outline);
        this.Players = new Value<Boolean>("Players", new String[] { "Players" }, "Highlights players", true);
        this.Monsters = new Value<Boolean>("Monsters", new String[] { "Monsters" }, "Highlights Monsters", false);
        this.Animals = new Value<Boolean>("Animals", new String[] { "Animals" }, "Highlights Animals", false);
        this.Vehicles = new Value<Boolean>("Vehicles", new String[] { "Vehicles" }, "Highlights Vehicles", false);
        this.Others = new Value<Boolean>("Others", new String[] { "Others" }, "Highlights Others", false);
        this.Items = new Value<Boolean>("Items", new String[] { "Items" }, "Highlights Items", false);
        this.Tamed = new Value<Boolean>("Tamed", new String[] { "Tamed" }, "Highlights Tamed", false);
        this.Width = new Value<Float>("Width", new String[] { "Width" }, "Highlights Width", 3.0f, 0.0f, 10.0f, 1.0f);
        this._uuidToName = new HashMap<UUID, String>();
        UUID owner;
        String name;
        String name2;
        double distance;
        double scale;
        double posX;
        double posY;
        double posZ;
        float width;
        this.OnRenderGameOverlay = new Listener<RenderEvent>(event -> {
            if (this.mc.world != null && this.mc.renderEngine != null && this.mc.getRenderManager() != null && this.mc.getRenderManager().options != null && this.Tamed.getValue()) {
                GL11.glPushMatrix();
                GL11.glTranslated(-this.mc.getRenderManager().viewerPosX, -this.mc.getRenderManager().viewerPosY, -this.mc.getRenderManager().viewerPosZ);
                this.mc.world.loadedEntityList.forEach(e -> {
                    if (e instanceof EntityTameable || e instanceof AbstractHorse) {
                        owner = null;
                        if (e instanceof EntityTameable) {
                            owner = ((EntityTameable)e).getOwnerId();
                        }
                        if (e instanceof AbstractHorse) {
                            owner = e.getOwnerUniqueId();
                        }
                        if (owner != null) {
                            name = this.getUserFromUUID(owner);
                            if (name != null) {
                                name2 = name + " [" + ((Entity)e).getEntityId() + "]";
                                distance = this.mc.getRenderViewEntity().getDistance((Entity)e);
                                scale = 0.04;
                                if (distance > 0.0) {
                                    scale = 0.02 + 0.003000000026077032 * distance;
                                }
                                posX = ((Entity)e).lastTickPosX + (((Entity)e).posX - ((Entity)e).lastTickPosX) * event.getPartialTicks();
                                posY = ((Entity)e).lastTickPosY + (((Entity)e).posY - ((Entity)e).lastTickPosY) * event.getPartialTicks();
                                posZ = ((Entity)e).lastTickPosZ + (((Entity)e).posZ - ((Entity)e).lastTickPosZ) * event.getPartialTicks();
                                GL11.glPushMatrix();
                                GL11.glTranslated(posX, posY + 1.4, posZ);
                                GL11.glNormal3i(0, 1, 0);
                                GL11.glRotatef(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
                                GL11.glRotatef(this.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
                                GL11.glScaled(-scale, -scale, scale);
                                GL11.glDisable(2896);
                                GL11.glDisable(2929);
                                width = RenderUtil.getStringWidth(name2) / 2.0f;
                                GL11.glEnable(3042);
                                GL11.glBlendFunc(770, 771);
                                RenderUtil.drawStringWithShadow(name2, -width, -11.0f, -1);
                                GL11.glEnable(2929);
                                GL11.glDisable(3042);
                                GL11.glEnable(2896);
                                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                GL11.glPopMatrix();
                            }
                        }
                    }
                    return;
                });
                GL11.glPopMatrix();
            }
        });
    }
    
    public void doRenderOutlines(final ModelBase mainModel, final Entity entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor) {
        if (entitylivingbaseIn == this.mc.player.getRidingEntity()) {
            return;
        }
        RenderUtil.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
        if (!RenderUtil.camera.isBoundingBoxInFrustum(entitylivingbaseIn.getEntityBoundingBox())) {
            return;
        }
        if (entitylivingbaseIn instanceof EntityPlayer && !(entitylivingbaseIn instanceof EntityPlayerSP) && this.Players.getValue()) {
            GlStateManager.pushMatrix();
            final Color n = this.generateColor(entitylivingbaseIn);
            RenderUtil.setColor(n);
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            RenderUtil.renderOne(this.Width.getValue());
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            RenderUtil.renderTwo();
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            RenderUtil.renderThree();
            RenderUtil.renderFour();
            RenderUtil.setColor(n);
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            RenderUtil.renderFive();
            RenderUtil.setColor(Color.WHITE);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
        else if ((EntityUtil.isPassive(entitylivingbaseIn) && this.Animals.getValue()) || (EntityUtil.isHostileMob(entitylivingbaseIn) && this.Monsters.getValue()) || (EntityUtil.IsVehicle(entitylivingbaseIn) && this.Vehicles.getValue()) || (entitylivingbaseIn instanceof EntityItem && this.Items.getValue())) {
            GlStateManager.pushMatrix();
            final Color n = this.generateColor(entitylivingbaseIn);
            RenderUtil.setColor(n);
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            RenderUtil.renderOne(this.Width.getValue());
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            RenderUtil.renderTwo();
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            RenderUtil.renderThree();
            RenderUtil.renderFour();
            RenderUtil.setColor(n);
            mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            RenderUtil.renderFive();
            RenderUtil.setColor(Color.WHITE);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }
    
    private String getUserFromUUID(final UUID id) {
        return this._uuidToName.computeIfAbsent(id, e -> EntityUtil.getNameFromUUID(id));
    }
    
    private Color generateColor(final Entity e) {
        if (EntityUtil.isPassive(e)) {
            return new Color(0, 200, 0);
        }
        if (EntityUtil.isHostileMob(e)) {
            return Color.RED;
        }
        if (EntityUtil.IsVehicle(e)) {
            return Color.WHITE;
        }
        return new Color(5, 255, 240);
    }
    
    private enum ESPMode
    {
        Outline;
    }
}
