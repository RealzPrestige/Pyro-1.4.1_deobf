// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.player;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventPlayerGetLocationCape extends MinecraftEvent
{
    private ResourceLocation m_Location;
    public AbstractClientPlayer Player;
    
    public EventPlayerGetLocationCape(final AbstractClientPlayer abstractClientPlayer) {
        this.m_Location = null;
        this.Player = abstractClientPlayer;
    }
    
    public void SetResourceLocation(final ResourceLocation p_Location) {
        this.m_Location = p_Location;
    }
    
    public ResourceLocation GetResourceLocation() {
        return this.m_Location;
    }
}
