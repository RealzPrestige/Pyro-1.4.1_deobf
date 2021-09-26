// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.ui;

import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Commands extends Module
{
    public static final Value<String> Prefix;
    
    public Commands() {
        super("Commands", new String[] { "Commands", "ChatCommands" }, "Chat commands", "NONE", -1, ModuleType.UI);
    }
    
    static {
        Prefix = new Value<String>("Prefix", new String[] { "P" }, "Prefix for the command system", ".");
    }
}
