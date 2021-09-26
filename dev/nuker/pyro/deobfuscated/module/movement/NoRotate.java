//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import net.minecraft.network.play.server.SPacketPlayerPosLook;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class NoRotate extends Module
{
    @EventHandler
    private Listener<EventServerPacket> onPlayerPosLook;
    
    public NoRotate() {
        super("NoRotate", new String[] { "NoRot", "AntiRotate" }, "Prevents you from processing server rotations", "NONE", 2405083, ModuleType.MOVEMENT);
        SPacketPlayerPosLook packet;
        this.onPlayerPosLook = new Listener<EventServerPacket>(event -> {
            if (event.getStage() == MinecraftEvent.Stage.Pre && event.getPacket() instanceof SPacketPlayerPosLook) {
                packet = (SPacketPlayerPosLook)event.getPacket();
                packet.pitch = this.mc.player.rotationPitch;
                packet.yaw = this.mc.player.rotationYaw;
            }
        });
    }
}
