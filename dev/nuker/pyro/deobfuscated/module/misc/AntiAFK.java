//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.client.Minecraft;
import java.util.TimerTask;
import java.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class AntiAFK extends Module
{
    private Timer timer;
    
    public AntiAFK() {
        super("AntiAFK", new String[] { "BuildH", "BHeight" }, "Makes sure you dont get kicked for afking", "NONE", 14361796, ModuleType.MISC);
        this.timer = new Timer();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.player == null) {
            this.toggle();
            return;
        }
        (this.timer = new Timer()).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                AntiAFK.this.mc.player.sendChatMessage("/stats");
            }
        }, 0L, 270000L);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.timer != null) {
            this.timer.cancel();
        }
    }
}
