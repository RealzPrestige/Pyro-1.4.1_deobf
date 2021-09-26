// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.player;

import net.minecraft.util.EnumHand;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventPlayerSwingArm extends MinecraftEvent
{
    public EnumHand Hand;
    
    public EventPlayerSwingArm(final EnumHand p_Hand) {
        this.Hand = p_Hand;
    }
}
