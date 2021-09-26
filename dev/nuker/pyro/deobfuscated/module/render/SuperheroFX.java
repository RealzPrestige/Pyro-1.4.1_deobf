//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import dev.nuker.pyro.deobfuscated.managers.HudManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.world.World;
import net.minecraft.network.play.server.SPacketEntityStatus;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.play.server.SPacketExplosion;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.managers.FontManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import java.util.concurrent.CopyOnWriteArrayList;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import java.util.Random;
import java.util.List;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class SuperheroFX extends Module
{
    public final Value<Float> ExplosionDelay;
    public final Value<Float> HitDelay;
    public final Value<Float> Scaling;
    private List<PopupText> popTexts;
    private Random rand;
    private Timer hitTimer;
    private Timer explosionTimer;
    private static String[] superHeroTextsBlowup;
    private static String[] superHeroTextsDamageTaken;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    @EventHandler
    private Listener<EventServerPacket> onPacket;
    
    public SuperheroFX() {
        super("SuperHeroFX", new String[] { "FX" }, "Displays cool things when events happen", "NONE", -1, ModuleType.RENDER);
        this.ExplosionDelay = new Value<Float>("ExplosionDelay", new String[] { "ED" }, "Measured in seconds", 1.0f, 0.0f, 10.0f, 1.0f);
        this.HitDelay = new Value<Float>("HitDelay", new String[] { "HD" }, "Measured in seconds", 1.0f, 1.0f, 10.0f, 1.0f);
        this.Scaling = new Value<Float>("Scaling", new String[] { "" }, "Scaling", 3.0f, 1.0f, 10.0f, 1.0f);
        this.popTexts = new CopyOnWriteArrayList<PopupText>();
        this.rand = new Random();
        this.hitTimer = new Timer();
        this.explosionTimer = new Timer();
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            this.popTexts.removeIf(pop -> pop.isMarked());
            this.popTexts.forEach(pop -> pop.Update());
            return;
        });
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
        this.OnRenderEvent = new Listener<RenderEvent>(event -> {
            if (this.mc.getRenderManager() == null || this.mc.getRenderManager().options == null) {
                return;
            }
            else {
                this.popTexts.forEach(pop -> {
                    entity2 = this.mc.getRenderViewEntity();
                    pos = MathUtil.interpolateVec3dPos(pop.getPos(), event.getPartialTicks());
                    n = pos.x;
                    distance = pos.y + 0.65;
                    n2 = pos.z;
                    n3 = distance;
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
                    nameTag = pop.getDisplayName();
                    width = (float)(FontManager.Get().badaboom.getStringWidth(nameTag) / 2);
                    height = (float)FontManager.Get().badaboom.getHeight();
                    FontManager.Get().badaboom.drawStringWithShadow(nameTag, -width + 1.0f, -height + 3.0f, pop.getColor());
                    GlStateManager.enableDepth();
                    GlStateManager.disableBlend();
                    GlStateManager.disablePolygonOffset();
                    GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
                    GlStateManager.popMatrix();
                    entity2.posX = posX;
                    entity2.posY = posY;
                    entity2.posZ = posZ;
                });
                return;
            }
        });
        SPacketExplosion packet;
        Vec3d pos3;
        List<PopupText> popTexts;
        final PopupText popupText;
        SPacketEntityStatus packet2;
        Entity e;
        List<PopupText> popTexts2;
        final PopupText popupText2;
        Vec3d pos4;
        List<PopupText> popTexts3;
        final PopupText popupText3;
        SPacketDestroyEntities packet3;
        final int[] array;
        int length;
        int i = 0;
        int id;
        Entity e2;
        Vec3d pos5;
        List<PopupText> popTexts4;
        final PopupText popupText4;
        this.onPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() == MinecraftEvent.Stage.Pre) {
                if (event.getPacket() instanceof SPacketExplosion) {
                    packet = (SPacketExplosion)event.getPacket();
                    pos3 = new Vec3d(packet.getX() + Math.random(), packet.getY() + Math.random() - 2.0, packet.getZ() + Math.random());
                    if (this.mc.player.getDistance(pos3.x, pos3.y, pos3.z) < 10.0 && this.explosionTimer.passed(this.ExplosionDelay.getValue() * 1000.0f)) {
                        this.explosionTimer.reset();
                        popTexts = this.popTexts;
                        new PopupText(ChatFormatting.ITALIC + SuperheroFX.superHeroTextsBlowup[this.rand.nextInt(SuperheroFX.superHeroTextsBlowup.length)], pos3);
                        popTexts.add(popupText);
                    }
                }
                else if (event.getPacket() instanceof SPacketEntityStatus) {
                    packet2 = (SPacketEntityStatus)event.getPacket();
                    if (this.mc.world != null) {
                        e = packet2.getEntity((World)this.mc.world);
                        if (packet2.getOpCode() == 35) {
                            if (e != null && this.mc.player.getDistance(e) < 20.0f) {
                                popTexts2 = this.popTexts;
                                new PopupText(ChatFormatting.ITALIC + "POP", e.getPositionVector().add((double)(this.rand.nextInt(2) / 2), 1.0, (double)(this.rand.nextInt(2) / 2)));
                                popTexts2.add(popupText2);
                            }
                        }
                        else if (packet2.getOpCode() == 2 && e != null) {
                            if (this.mc.player.getDistance(e) < 20.0f & e != this.mc.player) {
                                pos4 = new Vec3d(e.posX + Math.random(), e.posY + Math.random() - 2.0, e.posZ + Math.random());
                                if (this.hitTimer.passed(this.HitDelay.getValue() * 1000.0f)) {
                                    this.hitTimer.reset();
                                    popTexts3 = this.popTexts;
                                    new PopupText(ChatFormatting.ITALIC + SuperheroFX.superHeroTextsDamageTaken[this.rand.nextInt(SuperheroFX.superHeroTextsBlowup.length)], pos4);
                                    popTexts3.add(popupText3);
                                }
                            }
                        }
                    }
                }
                else if (event.getPacket() instanceof SPacketDestroyEntities) {
                    packet3 = (SPacketDestroyEntities)event.getPacket();
                    packet3.getEntityIDs();
                    for (length = array.length; i < length; ++i) {
                        id = array[i];
                        e2 = this.mc.world.getEntityByID(id);
                        if (e2 != null && e2.isDead) {
                            if ((this.mc.player.getDistance(e2) < 20.0f & e2 != this.mc.player) && e2 instanceof EntityPlayer) {
                                pos5 = new Vec3d(e2.posX + Math.random(), e2.posY + Math.random() - 2.0, e2.posZ + Math.random());
                                popTexts4 = this.popTexts;
                                new PopupText(ChatFormatting.ITALIC + "" + ChatFormatting.BOLD + "EZ", pos5);
                                popTexts4.add(popupText4);
                            }
                        }
                    }
                }
            }
        });
    }
    
    static {
        SuperheroFX.superHeroTextsBlowup = new String[] { "KABOOM", "BOOM", "POW", "KAPOW" };
        SuperheroFX.superHeroTextsDamageTaken = new String[] { "OUCH", "ZAP", "BAM", "WOW", "POW", "SLAP" };
    }
    
    class PopupText
    {
        private String displayName;
        private Vec3d position;
        private boolean markedToRemove;
        private int color;
        private Timer timer;
        private double yIncrease;
        
        public PopupText(final String displayName, final Vec3d pos) {
            this.timer = new Timer();
            this.yIncrease = 0.0;
            this.yIncrease = Math.random();
            while (this.yIncrease > 0.025 || this.yIncrease < 0.011) {
                this.yIncrease = Math.random();
            }
            this.timer.reset();
            this.setDisplayName(displayName);
            this.position = pos;
            this.markedToRemove = false;
            this.color = HudManager.Get().rainbow.GetRainbowColorAt(0);
        }
        
        public void Update() {
            this.position = this.position.add(0.0, this.yIncrease, 0.0);
            if (this.timer.passed(1000.0)) {
                this.markedToRemove = true;
            }
        }
        
        public Vec3d getPos() {
            return this.position;
        }
        
        public boolean isMarked() {
            return this.markedToRemove;
        }
        
        public String getDisplayName() {
            return this.displayName;
        }
        
        public void setDisplayName(final String displayName) {
            this.displayName = displayName;
        }
        
        public int getColor() {
            return this.color;
        }
    }
}
