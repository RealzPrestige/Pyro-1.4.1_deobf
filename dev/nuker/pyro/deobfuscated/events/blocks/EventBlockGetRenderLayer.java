// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.BlockRenderLayer;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventBlockGetRenderLayer extends MinecraftEvent
{
    private BlockRenderLayer _layer;
    private Block _block;
    
    public EventBlockGetRenderLayer(final Block block) {
        this._block = block;
    }
    
    public Block getBlock() {
        return this._block;
    }
    
    public void setLayer(final BlockRenderLayer layer) {
        this._layer = layer;
    }
    
    public BlockRenderLayer getBlockRenderLayer() {
        return this._layer;
    }
}
