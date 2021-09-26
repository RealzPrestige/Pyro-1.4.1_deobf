//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.network.play.client.CPacketVehicleMove;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerTravel;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class BoatFly extends Module
{
    public final Value<Float> speed;
    public final Value<Float> GlideSpeed;
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    @EventHandler
    private Listener<EventPlayerTravel> OnTravel;
    
    public BoatFly() {
        super("BoatFly", new String[] { "BF" }, "Fly with ridable entities", "NONE", 12723419, ModuleType.MOVEMENT);
        this.speed = new Value<Float>("Speed", new String[] { "Spd" }, "Speed multiplier for flight, higher values equals more speed", 2.02f, 0.0f, 10.0f, 0.1f);
        this.GlideSpeed = new Value<Float>("GlideSpeed", new String[] { "GlideSpeed" }, "Glide value for acceleration, this is divided by 10000.", 1.0f, 0.0f, 10.0f, 1.0f);
        this.onClientPacket = new Listener<EventClientPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                if (event.getPacket() instanceof CPacketVehicleMove && this.mc.player.isRiding() && this.mc.player.ticksExisted % 2 == 0) {
                    this.mc.playerController.interactWithEntity((EntityPlayer)this.mc.player, this.mc.player.getRidingEntity(), EnumHand.MAIN_HAND);
                }
                return;
            }
            else {
                if ((event.getPacket() instanceof CPacketPlayer.Rotation || event.getPacket() instanceof CPacketInput) && this.mc.player.isRiding()) {
                    event.cancel();
                }
                return;
            }
        });
        this.onServerPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                if (event.getPacket() instanceof SPacketMoveVehicle && this.mc.player.isRiding()) {
                    event.cancel();
                }
                return;
            }
        });
        Entity riding;
        double[] dir;
        this.OnTravel = new Listener<EventPlayerTravel>(event -> {
            if (!(!this.mc.player.isRiding())) {
                riding = this.mc.player.getRidingEntity();
                riding.rotationYaw = this.mc.player.rotationYaw;
                riding.motionY = -this.GlideSpeed.getValue() / 10000.0f;
                dir = MathUtil.directionSpeed(this.speed.getValue());
                if (this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f) {
                    riding.motionX = dir[0];
                    riding.motionY = -(this.GlideSpeed.getValue() / 10000.0f);
                    riding.motionZ = dir[1];
                }
                if (this.mc.player.movementInput.jump) {
                    riding.motionY = 1.0;
                }
                else if (this.mc.player.movementInput.sneak) {
                    riding.motionY = -1.0;
                }
                event.cancel();
            }
        });
    }
}
