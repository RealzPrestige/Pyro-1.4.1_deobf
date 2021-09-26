// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.player;

import dev.nuker.pyro.deobfuscated.mixin.client.MixinAbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventPlayerGetLocationSkin extends MinecraftEvent
{
    private ResourceLocation m_Location;
    public MixinAbstractClientPlayer Player;
    
    public EventPlayerGetLocationSkin(final MixinAbstractClientPlayer p_Player) {
        this.m_Location = null;
        this.Player = p_Player;
    }
    
    public void SetResourceLocation(final ResourceLocation p_Location) {
        this.m_Location = p_Location;
    }
    
    public ResourceLocation GetResourceLocation() {
        return this.m_Location;
    }
}
