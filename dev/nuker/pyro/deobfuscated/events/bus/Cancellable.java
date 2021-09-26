// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.bus;

public class Cancellable
{
    private boolean cancelled;
    
    public Cancellable() {
        this.cancelled = false;
    }
    
    public void cancel() {
        this.cancelled = true;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean way) {
        this.cancelled = way;
    }
}
