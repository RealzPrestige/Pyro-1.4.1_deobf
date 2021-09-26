//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class Yaw extends Module
{
    public final Value<Boolean> Cardinal;
    public final Value<Boolean> yawLock;
    public final Value<Boolean> pitchLock;
    private float Yaw;
    private float Pitch;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public Yaw() {
        super("Yaw", new String[] { "RotLock", "Rotation" }, "Locks you rotation for precision", "NONE", 7087748, ModuleType.MOVEMENT);
        this.Cardinal = new Value<Boolean>("Cardinal", new String[] { "C" }, "Locks the yaw to one of the cardinal directions", true);
        this.yawLock = new Value<Boolean>("Yaw", new String[] { "Y" }, "Lock the player's rotation yaw if enabled.", false);
        this.pitchLock = new Value<Boolean>("Pitch", new String[] { "P" }, "Lock the player's rotation pitch if enabled.", false);
        Entity l_Entity;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                this.setMetaData(((boolean)this.Cardinal.getValue()) ? "Cardinal" : "One");
                l_Entity = (Entity)(this.mc.player.isRiding() ? this.mc.player.getRidingEntity() : this.mc.player);
                if (this.yawLock.getValue()) {
                    l_Entity.rotationYaw = this.Yaw;
                }
                if (this.pitchLock.getValue()) {
                    l_Entity.rotationPitch = this.Pitch;
                }
                if (this.Cardinal.getValue()) {
                    l_Entity.rotationYaw = Math.round((l_Entity.rotationYaw + 1.0f) / 45.0f) * 45.0f;
                }
                return;
            }
        });
        this.setMetaData(((boolean)this.Cardinal.getValue()) ? "Cardinal" : "One");
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.player != null) {
            this.Yaw = this.mc.player.rotationYaw;
            this.Pitch = this.mc.player.rotationPitch;
        }
    }
    
    @Override
    public void toggleNoSave() {
    }
}
