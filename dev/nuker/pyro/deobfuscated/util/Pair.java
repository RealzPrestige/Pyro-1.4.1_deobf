// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

public class Pair<F, S>
{
    private F first;
    private S second;
    
    public Pair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }
    
    public void setFirst(final F first) {
        this.first = first;
    }
    
    public void setSecond(final S second) {
        this.second = second;
    }
    
    public F getFirst() {
        return this.first;
    }
    
    public S getSecond() {
        return this.second;
    }
}
