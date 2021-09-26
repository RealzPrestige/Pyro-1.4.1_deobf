// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.block.BlockShulkerBox;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.blocks.EventCanPlaceCheck;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AntiShulkerPlace extends Module
{
    @EventHandler
    private Listener<EventCanPlaceCheck> CheckEvent;
    
    public AntiShulkerPlace() {
        super("AntiShulkerPlace", new String[] { "NoShulkerPlace", "CancelShulkerPlace" }, "Prevents you from accidentally placing shulkers", "NONE", 14361796, ModuleType.MISC);
        this.CheckEvent = new Listener<EventCanPlaceCheck>(event -> {
            if (event.Block.isAssignableFrom(BlockShulkerBox.class)) {
                event.cancel();
            }
        });
    }
}
