//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class BuildHeight extends Module
{
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    
    public BuildHeight() {
        super("BuildHeight", new String[] { "BuildH", "BHeight" }, "Allows you to interact with blocks at build height", "NONE", 14361709, ModuleType.MISC);
        CPacketPlayerTryUseItemOnBlock packet;
        this.onClientPacket = new Listener<EventClientPacket>(event -> {
            if (event.getStage() == MinecraftEvent.Stage.Pre) {
                if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                    packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
                    if (packet.getPos().getY() >= 255 && packet.getDirection() == EnumFacing.UP) {
                        packet.placedBlockDirection = EnumFacing.DOWN;
                    }
                }
            }
        });
    }
}
