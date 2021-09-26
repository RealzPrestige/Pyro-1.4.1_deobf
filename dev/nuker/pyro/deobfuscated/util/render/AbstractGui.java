//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util.render;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractGui
{
    public static final ResourceLocation BACKGROUND_LOCATION;
    public static final ResourceLocation STATS_ICON_LOCATION;
    public static final ResourceLocation GUI_ICONS_LOCATION;
    protected int blitOffset;
    
    public static void fill(int x0, int y1, int x1, int y0, final int color) {
        if (x0 < x1) {
            final int i = x0;
            x0 = x1;
            x1 = i;
        }
        if (y1 < y0) {
            final int j = y1;
            y1 = y0;
            y0 = j;
        }
        final float a = (color >> 24 & 0xFF) / 255.0f;
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(r, g, b, a);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)x0, (double)y0, 0.0).endVertex();
        bufferbuilder.pos((double)x1, (double)y0, 0.0).endVertex();
        bufferbuilder.pos((double)x1, (double)y1, 0.0).endVertex();
        bufferbuilder.pos((double)x0, (double)y1, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void blit(final int x, final int y, final int z, final int width, final int height, final TextureAtlasSprite sprite) {
        innerBlit(x, x + width, y, y + height, z, sprite.getMinU(), sprite.getMaxU(), sprite.getMinV(), sprite.getMaxV());
    }
    
    public static void blit(final int x, final int y, final int z, final float u, final float v, final int width, final int height, final int vScale, final int uScale) {
        innerBlit(x, x + width, y, y + height, z, width, height, u, v, uScale, vScale);
    }
    
    public static void blit(final int x, final int y, final int width, final int height, final float minU, final float minV, final int maxU, final int maxV, final int uScale, final int vScale) {
        innerBlit(x, x + width, y, y + height, 0, maxU, maxV, minU, minV, uScale, vScale);
    }
    
    public static void blit(final int x, final int y, final float minU, final float minV, final int width, final int height, final int uScale, final int vScale) {
        blit(x, y, width, height, minU, minV, width, height, uScale, vScale);
    }
    
    private static void innerBlit(final int x0, final int x1, final int y0, final int y1, final int z, final int maxU, final int maxV, final float minU, final float minV, final int uScale, final int vScale) {
        innerBlit(x0, x1, y0, y1, z, (minU + 0.0f) / uScale, (minU + maxU) / uScale, (minV + 0.0f) / vScale, (minV + maxV) / vScale);
    }
    
    protected static void innerBlit(final int x0, final int x1, final int y1, final int y0, final int z, final float minU, final float maxU, final float minV, final float maxV) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x0, (double)y0, (double)z).tex((double)minU, (double)maxV).endVertex();
        bufferbuilder.pos((double)x1, (double)y0, (double)z).tex((double)maxU, (double)maxV).endVertex();
        bufferbuilder.pos((double)x1, (double)y1, (double)z).tex((double)maxU, (double)minV).endVertex();
        bufferbuilder.pos((double)x0, (double)y1, (double)z).tex((double)minU, (double)minV).endVertex();
        tessellator.draw();
    }
    
    protected void hLine(int x, int width, final int y, final int color) {
        if (width < x) {
            final int i = x;
            x = width;
            width = i;
        }
        fill(x, y, width + 1, y + 1, color);
    }
    
    protected void vLine(final int x, int y, int height, final int color) {
        if (height < y) {
            final int i = y;
            y = height;
            height = i;
        }
        fill(x, y + 1, x + 1, height, color);
    }
    
    protected void fillGradient(final int x1, final int y0, final int x0, final int y1, final int color0, final int color1) {
        final float a0 = (color0 >> 24 & 0xFF) / 255.0f;
        final float r0 = (color0 >> 16 & 0xFF) / 255.0f;
        final float g0 = (color0 >> 8 & 0xFF) / 255.0f;
        final float b0 = (color0 & 0xFF) / 255.0f;
        final float a2 = (color1 >> 24 & 0xFF) / 255.0f;
        final float r2 = (color1 >> 16 & 0xFF) / 255.0f;
        final float g2 = (color1 >> 8 & 0xFF) / 255.0f;
        final float b2 = (color1 & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x0, (double)y0, (double)this.blitOffset).color(r0, g0, b0, a0).endVertex();
        bufferbuilder.pos((double)x1, (double)y0, (double)this.blitOffset).color(r0, g0, b0, a0).endVertex();
        bufferbuilder.pos((double)x1, (double)y1, (double)this.blitOffset).color(r2, g2, b2, a2).endVertex();
        bufferbuilder.pos((double)x0, (double)y1, (double)this.blitOffset).color(r2, g2, b2, a2).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public void blit(final int x, final int y, final int u, final int v, final int width, final int height) {
        blit(x, y, this.blitOffset, (float)u, (float)v, width, height, 256, 256);
    }
    
    static {
        BACKGROUND_LOCATION = new ResourceLocation("textures/gui/options_background.png");
        STATS_ICON_LOCATION = new ResourceLocation("textures/gui/container/stats_icons.png");
        GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");
    }
}
