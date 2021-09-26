// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoReconnect extends Module
{
    public final Value<Float> Delay;
    
    public AutoReconnect() {
        super("AutoReconnect", new String[] { "Reconnect" }, "Automatically reconnects you to your last server", "NONE", 5066121, ModuleType.MISC);
        this.Delay = new Value<Float>("Delay", new String[] { "Delay" }, "Delay to use between attempts", 5.0f, 0.0f, 20.0f, 1.0f);
    }
}
