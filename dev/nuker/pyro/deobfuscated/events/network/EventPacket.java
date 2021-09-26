// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.network;

import net.minecraft.network.Packet;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventPacket extends MinecraftEvent
{
    private Packet<?> _packet;
    
    public EventPacket(final Packet<?> packet, final Stage stage) {
        super(stage);
        this._packet = packet;
    }
    
    public Packet<?> getPacket() {
        return this._packet;
    }
}
