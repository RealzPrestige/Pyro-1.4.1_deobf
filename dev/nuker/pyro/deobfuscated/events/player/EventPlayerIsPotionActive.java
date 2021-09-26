// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.player;

import net.minecraft.potion.Potion;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventPlayerIsPotionActive extends MinecraftEvent
{
    public Potion potion;
    
    public EventPlayerIsPotionActive(final Potion p_Potion) {
        this.potion = p_Potion;
    }
}
