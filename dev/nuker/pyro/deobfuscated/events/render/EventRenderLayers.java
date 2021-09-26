// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.render;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import dev.nuker.pyro.deobfuscated.events.bus.Cancellable;

public class EventRenderLayers extends Cancellable
{
    private final EntityLivingBase entityLivingBase;
    private final LayerRenderer layerRenderer;
    private float HeadPitch;
    
    public EventRenderLayers(final EntityLivingBase entityLivingBase, final LayerRenderer layerRenderer, final float headPitch) {
        this.entityLivingBase = entityLivingBase;
        this.layerRenderer = layerRenderer;
        this.HeadPitch = headPitch;
    }
    
    public EntityLivingBase getEntityLivingBase() {
        return this.entityLivingBase;
    }
    
    public LayerRenderer getLayerRenderer() {
        return this.layerRenderer;
    }
    
    public float GetHeadPitch() {
        return this.HeadPitch;
    }
    
    public void SetHeadPitch(final float p_Pitch) {
        this.HeadPitch = p_Pitch;
    }
}
