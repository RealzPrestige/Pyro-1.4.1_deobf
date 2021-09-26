//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketEntityStatus;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerApplyCollision;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerPushedByWater;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerPushOutOfBlocks;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class Velocity extends Module
{
    public final Value<Integer> horizontal_vel;
    public final Value<Integer> vertical_vel;
    public final Value<Boolean> explosions;
    public final Value<Boolean> bobbers;
    public final Value<Boolean> NoPush;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerPushOutOfBlocks> PushOutOfBlocks;
    @EventHandler
    private Listener<EventPlayerPushedByWater> PushByWater;
    @EventHandler
    private Listener<EventPlayerApplyCollision> ApplyCollision;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    
    public Velocity() {
        super("Velocity", new String[] { "Vel", "AntiVelocity", "Knockback", "AntiKnockback" }, "Modify the velocity you take", "NONE", 7439498, ModuleType.COMBAT);
        this.horizontal_vel = new Value<Integer>("Horizontal", new String[] { "Horizontal_Velocity", "HVel", "HV", "HorizontalVel", "Horizontal", "H" }, "The horizontal velocity you will take.", 0, 0, 100, 1);
        this.vertical_vel = new Value<Integer>("Veritcal", new String[] { "Vertical_Velocity", "VVel", "VV", "VerticalVel", "Vertical", "Vert", "V" }, "The vertical velocity you will take.", 0, 0, 100, 1);
        this.explosions = new Value<Boolean>("Explosions", new String[] { "Explosions", "Explosion", "EXP", "EX", "Expl" }, "Apply velocity modifier on explosion velocity.", true);
        this.bobbers = new Value<Boolean>("Bobbers", new String[] { "Bobb", "Bob", "FishHook", "FishHooks" }, "Apply velocity modifier on fishing bobber velocity.", true);
        this.NoPush = new Value<Boolean>("NoPush", new String[] { "AntiPush" }, "Disable collision with entities, blocks and water", true);
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> this.setMetaData(this.getMetaData()));
        this.PushOutOfBlocks = new Listener<EventPlayerPushOutOfBlocks>(event -> {
            if (!this.NoPush.getValue()) {
                return;
            }
            else {
                event.cancel();
                return;
            }
        });
        this.PushByWater = new Listener<EventPlayerPushedByWater>(event -> {
            if (!this.NoPush.getValue()) {
                return;
            }
            else {
                event.cancel();
                return;
            }
        });
        this.ApplyCollision = new Listener<EventPlayerApplyCollision>(event -> {
            if (!this.NoPush.getValue()) {
                return;
            }
            else {
                event.cancel();
                return;
            }
        });
        SPacketEntityStatus packet;
        Entity entity;
        EntityFishHook fishHook;
        SPacketEntityVelocity packet2;
        SPacketExplosion packet3;
        this.onServerPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else if (this.mc.player == null) {
                return;
            }
            else {
                if (event.getPacket() instanceof SPacketEntityStatus && this.bobbers.getValue()) {
                    packet = (SPacketEntityStatus)event.getPacket();
                    if (packet.getOpCode() == 31) {
                        entity = packet.getEntity((World)Minecraft.getMinecraft().world);
                        if (entity != null && entity instanceof EntityFishHook) {
                            fishHook = (EntityFishHook)entity;
                            if (fishHook.caughtEntity == Minecraft.getMinecraft().player) {
                                event.cancel();
                            }
                        }
                    }
                }
                if (event.getPacket() instanceof SPacketEntityVelocity) {
                    packet2 = (SPacketEntityVelocity)event.getPacket();
                    if (packet2.getEntityID() == this.mc.player.getEntityId()) {
                        if (this.horizontal_vel.getValue() == 0 && this.vertical_vel.getValue() == 0) {
                            event.cancel();
                            return;
                        }
                        else {
                            if (this.horizontal_vel.getValue() != 100) {
                                packet2.motionX = packet2.motionX / 100 * this.horizontal_vel.getValue();
                                packet2.motionZ = packet2.motionZ / 100 * this.horizontal_vel.getValue();
                            }
                            if (this.vertical_vel.getValue() != 100) {
                                packet2.motionY = packet2.motionY / 100 * this.vertical_vel.getValue();
                            }
                        }
                    }
                }
                if (event.getPacket() instanceof SPacketExplosion && this.explosions.getValue()) {
                    packet3 = (SPacketExplosion)event.getPacket();
                    if (this.horizontal_vel.getValue() == 0 && this.vertical_vel.getValue() == 0) {
                        event.cancel();
                    }
                    else {
                        if (this.horizontal_vel.getValue() != 100) {
                            packet3.motionX = packet3.motionX / 100.0f * this.horizontal_vel.getValue();
                            packet3.motionZ = packet3.motionZ / 100.0f * this.horizontal_vel.getValue();
                        }
                        if (this.vertical_vel.getValue() != 100) {
                            packet3.motionY = packet3.motionY / 100.0f * this.vertical_vel.getValue();
                        }
                    }
                }
                return;
            }
        });
        this.setMetaData(this.getMetaData());
    }
    
    @Override
    public String getMetaData() {
        return String.format("H:%s%% V:%s%%", this.horizontal_vel.getValue(), this.vertical_vel.getValue());
    }
}
