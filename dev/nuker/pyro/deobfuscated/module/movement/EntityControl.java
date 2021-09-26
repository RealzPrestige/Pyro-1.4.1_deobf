// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import dev.nuker.pyro.deobfuscated.events.entity.EventHorseSaddled;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.entity.EventSteerEntity;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Module;

public class EntityControl extends Module
{
    @EventHandler
    private Listener<EventSteerEntity> OnSteerEntity;
    @EventHandler
    private Listener<EventHorseSaddled> OnHorseSaddled;
    
    public EntityControl() {
        super("EntityControl", new String[] { "AntiSaddle", "EntityRide", "NoSaddle" }, "Allows you to control llamas, horses, pigs without a saddle/carrot", "NONE", 1612579, ModuleType.MOVEMENT);
        this.OnSteerEntity = new Listener<EventSteerEntity>(p_Event -> p_Event.cancel());
        this.OnHorseSaddled = new Listener<EventHorseSaddled>(p_Event -> p_Event.cancel());
    }
}
