// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import dev.nuker.pyro.deobfuscated.main.Wrapper;
import net.minecraft.client.Minecraft;

public class MinecraftInstance
{
    protected static final Minecraft mc;
    
    static {
        mc = Wrapper.GetMC();
    }
}
