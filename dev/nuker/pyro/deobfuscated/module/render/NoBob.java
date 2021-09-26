//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Module;

public class NoBob extends Module
{
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public NoBob() {
        super("NoBob", new String[] { "NoBob" }, "Prevents bobbing by setting distance walked modifier to a static number", "NONE", 6180566, ModuleType.RENDER);
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(event -> this.mc.player.distanceWalkedModified = 4.0f);
    }
}
