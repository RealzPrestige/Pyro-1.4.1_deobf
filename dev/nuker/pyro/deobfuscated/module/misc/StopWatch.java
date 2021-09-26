// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.client.EventClientTick;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Module;

public class StopWatch extends Module
{
    public long StartMS;
    public long ElapsedMS;
    @EventHandler
    private Listener<EventClientTick> OnTick;
    
    public StopWatch() {
        super("Stopwatch", new String[] { "" }, "Counts a stopwatch starting from 0 when toggled.", "NONE", -1, ModuleType.MISC);
        this.OnTick = new Listener<EventClientTick>(p_Event -> this.ElapsedMS = System.currentTimeMillis());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.StartMS = System.currentTimeMillis();
        this.ElapsedMS = System.currentTimeMillis();
    }
}
