// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.blocks.EventPlaceBlockAt;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class NoGlitchBlocks extends Module
{
    public final Value<Boolean> Destroy;
    public final Value<Boolean> Place;
    @EventHandler
    private Listener<EventPlaceBlockAt> OnSetBlockState;
    
    public NoGlitchBlocks() {
        super("NoGlitchBlocks", new String[] { "AntiGhostBlocks" }, "Synchronizes client and server communication by canceling clientside destroy/place for blocks", "NONE", 12544803, ModuleType.WORLD);
        this.Destroy = new Value<Boolean>("Destroy", new String[] { "destroy" }, "Syncs Destroying", true);
        this.Place = new Value<Boolean>("Place", new String[] { "placement" }, "Syncs placement.", true);
        this.OnSetBlockState = new Listener<EventPlaceBlockAt>(event -> {
            if (!(!this.Place.getValue())) {
                event.cancel();
            }
        });
    }
}
