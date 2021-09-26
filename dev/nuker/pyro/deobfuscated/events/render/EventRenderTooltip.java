// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.render;

import net.minecraft.item.ItemStack;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventRenderTooltip extends MinecraftEvent
{
    private ItemStack Item;
    private int X;
    private int Y;
    
    public EventRenderTooltip(final ItemStack p_Stack, final int p_X, final int p_Y) {
        this.Item = p_Stack;
        this.X = p_X;
        this.Y = p_Y;
    }
    
    public ItemStack getItemStack() {
        return this.Item;
    }
    
    public int getX() {
        return this.X;
    }
    
    public int getY() {
        return this.Y;
    }
}
