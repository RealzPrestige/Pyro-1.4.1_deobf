// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util.imgs;

import net.minecraft.util.ResourceLocation;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class SalDynamicTexture extends DynamicTexture
{
    private int Height;
    private int Width;
    private BufferedImage m_BufferedImage;
    private ResourceLocation m_TexturedLocation;
    private ImageFrame m_Frame;
    
    public SalDynamicTexture(final BufferedImage bufferedImage, final int p_Height, final int p_Width) {
        super(bufferedImage);
        this.m_Frame = null;
        this.m_BufferedImage = bufferedImage;
        this.Height = p_Height;
        this.Width = p_Width;
    }
    
    public int GetHeight() {
        return this.Height;
    }
    
    public int GetWidth() {
        return this.Width;
    }
    
    public final DynamicTexture GetDynamicTexture() {
        return this;
    }
    
    public final BufferedImage GetBufferedImage() {
        return this.m_BufferedImage;
    }
    
    public void SetResourceLocation(final ResourceLocation dynamicTextureLocation) {
        this.m_TexturedLocation = dynamicTextureLocation;
    }
    
    public final ResourceLocation GetResourceLocation() {
        return this.m_TexturedLocation;
    }
    
    public void SetImageFrame(final ImageFrame p_Frame) {
        this.m_Frame = p_Frame;
    }
    
    public final ImageFrame GetFrame() {
        return this.m_Frame;
    }
}
