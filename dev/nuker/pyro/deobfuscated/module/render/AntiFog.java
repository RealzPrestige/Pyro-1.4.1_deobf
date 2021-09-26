//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderSetupFog;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AntiFog extends Module
{
    @EventHandler
    private Listener<EventRenderSetupFog> SetupFog;
    
    public AntiFog() {
        super("AntiFog", new String[] { "NoFog" }, "Prevents fog from being rendered", "NONE", 14361771, ModuleType.RENDER);
        this.SetupFog = new Listener<EventRenderSetupFog>(p_Event -> {
            p_Event.cancel();
            GlStateManager.pushMatrix();
            GlStateManager.setFogDensity(0.0f);
            GlStateManager.popMatrix();
        });
    }
}
