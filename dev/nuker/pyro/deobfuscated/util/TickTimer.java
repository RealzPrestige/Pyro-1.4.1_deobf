// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

public final class TickTimer
{
    private int tick;
    
    public void update() {
        ++this.tick;
    }
    
    public void reset() {
        this.tick = 0;
    }
    
    public boolean hasTimePassed(final int ticks) {
        return this.tick >= ticks;
    }
}
