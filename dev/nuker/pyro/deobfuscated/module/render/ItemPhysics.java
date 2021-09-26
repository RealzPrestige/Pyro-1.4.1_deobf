// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class ItemPhysics extends Module
{
    public static final Value<Float> Scaling;
    
    public ItemPhysics() {
        super("ItemPhysics", new String[] { "IP" }, "Allows you to control physics of item entities", "NONE", -1, ModuleType.RENDER);
    }
    
    static {
        Scaling = new Value<Float>("Scaling", new String[] { "" }, "Scaling", 0.5f, 0.0f, 10.0f, 1.0f);
    }
}
