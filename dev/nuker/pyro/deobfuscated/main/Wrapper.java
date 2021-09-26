//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.main;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;

public class Wrapper
{
    static final Minecraft mc;
    public static FileManager fileManager;
    
    public static Minecraft GetMC() {
        return Wrapper.mc;
    }
    
    public static EntityPlayerSP GetPlayer() {
        return Wrapper.mc.player;
    }
    
    public static FileManager getFileManager() {
        if (Wrapper.fileManager == null) {
            Wrapper.fileManager = new FileManager();
        }
        return Wrapper.fileManager;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
