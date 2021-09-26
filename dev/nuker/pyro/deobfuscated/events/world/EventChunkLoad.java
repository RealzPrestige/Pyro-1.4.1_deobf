// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.world;

import net.minecraft.world.chunk.Chunk;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventChunkLoad extends MinecraftEvent
{
    private Chunk _chunk;
    private Type _type;
    
    public EventChunkLoad(final Type type, final Chunk chunk) {
        this._type = type;
        this._chunk = chunk;
    }
    
    public Chunk getChunk() {
        return this._chunk;
    }
    
    public Type getType() {
        return this._type;
    }
    
    public enum Type
    {
        LOAD, 
        UNLOAD;
    }
}
