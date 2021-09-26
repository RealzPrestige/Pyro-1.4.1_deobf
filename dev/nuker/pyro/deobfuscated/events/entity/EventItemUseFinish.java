// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.entity;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventItemUseFinish extends MinecraftEvent
{
    private EntityLivingBase entity;
    private ItemStack active;
    
    public EventItemUseFinish(final EntityLivingBase e, final ItemStack activeItemStack) {
        this.entity = e;
        this.active = activeItemStack;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
    
    public ItemStack getActive() {
        return this.active;
    }
}
