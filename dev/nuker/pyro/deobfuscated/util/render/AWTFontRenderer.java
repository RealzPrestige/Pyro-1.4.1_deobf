//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util.render;

import java.awt.FontMetrics;
import java.awt.RenderingHints;
import net.minecraft.client.renderer.texture.TextureUtil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import java.util.HashMap;
import java.awt.Font;
import java.util.ArrayList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AWTFontRenderer
{
    private static boolean assumeNonVolatile;
    private static final ArrayList<AWTFontRenderer> activeFontRenderers;
    private static int gcTicks;
    private static final int GC_TICKS = 600;
    private static final int CACHED_FONT_REMOVAL_TIME = 30000;
    private final Font font;
    private int startChar;
    private int stopChar;
    private int fontHeight;
    private CharLocation[] charLocations;
    private final HashMap<String, CachedFont> cachedStrings;
    private int textureID;
    private int textureWidth;
    private int textureHeight;
    
    public static void garbageCollectionTick() {
        if (AWTFontRenderer.gcTicks++ > 600) {
            AWTFontRenderer.activeFontRenderers.forEach(AWTFontRenderer::collectGarbage);
            AWTFontRenderer.gcTicks = 0;
        }
    }
    
    private void collectGarbage() {
        final long currentTime = System.currentTimeMillis();
        this.cachedStrings.entrySet().stream().filter(entry -> currentTime - entry.getValue().getLastUsage() > 30000L).forEach(entry -> {
            GL11.glDeleteLists(entry.getValue().getDisplayList(), 1);
            entry.getValue().setDeleted(true);
            this.cachedStrings.remove(entry.getKey());
        });
    }
    
    public AWTFontRenderer(final Font font, final int startChar, final int stopChar) {
        this.fontHeight = -1;
        this.charLocations = null;
        this.cachedStrings = new HashMap<String, CachedFont>();
        this.textureID = 0;
        this.textureWidth = 0;
        this.textureHeight = 0;
        this.font = font;
        this.startChar = startChar;
        this.stopChar = stopChar;
        this.charLocations = new CharLocation[stopChar];
        this.renderBitmap(startChar, stopChar);
        AWTFontRenderer.activeFontRenderers.add(this);
    }
    
    public AWTFontRenderer(final Font font) {
        this(font, 0, 255);
    }
    
    public int getHeight() {
        return (this.fontHeight - 8) / 2;
    }
    
    public void drawString(final String text, final double x, final double y, final int color) {
        final double scale = 0.25;
        final double reverse = 4.0;
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.25, 0.25, 0.25);
        GL11.glTranslated(x * 2.0, y * 2.0 - 2.0, 0.0);
        GlStateManager.bindTexture(this.textureID);
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
        double currX = 0.0;
        final CachedFont cached = this.cachedStrings.get(text);
        if (cached != null) {
            GL11.glCallList(cached.getDisplayList());
            cached.setLastUsage(System.currentTimeMillis());
            GlStateManager.popMatrix();
            return;
        }
        int list = -1;
        if (AWTFontRenderer.assumeNonVolatile) {
            list = GL11.glGenLists(1);
            GL11.glNewList(list, 4865);
        }
        GL11.glBegin(7);
        for (final char ch : text.toCharArray()) {
            if (Character.getNumericValue(ch) >= this.charLocations.length) {
                GL11.glEnd();
                GlStateManager.scale(4.0, 4.0, 4.0);
                Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(ch), (float)currX * 0.25f + 1.0f, 2.0f, color, false);
                currX += Minecraft.getMinecraft().fontRenderer.getStringWidth(String.valueOf(ch)) * 4.0;
                GlStateManager.scale(0.25, 0.25, 0.25);
                GlStateManager.bindTexture(this.textureID);
                GlStateManager.color(red, green, blue, alpha);
                GL11.glBegin(7);
            }
            else if (this.charLocations.length > ch) {
                final CharLocation fontChar = this.charLocations[ch];
                if (fontChar != null) {
                    this.drawChar(fontChar, (float)currX, 0.0f);
                    currX += fontChar.width - 8.0;
                }
            }
        }
        GL11.glEnd();
        if (AWTFontRenderer.assumeNonVolatile) {
            this.cachedStrings.put(text, new CachedFont(list, System.currentTimeMillis()));
            GL11.glEndList();
        }
        GlStateManager.popMatrix();
    }
    
    private void drawChar(final CharLocation ch, final float x, final float y) {
        final float width = (float)ch.width;
        final float height = (float)ch.height;
        final float srcX = (float)ch.x;
        final float srcY = (float)ch.y;
        final float renderX = srcX / this.textureWidth;
        final float renderY = srcY / this.textureHeight;
        final float renderWidth = width / this.textureWidth;
        final float renderHeight = height / this.textureHeight;
        GL11.glTexCoord2f(renderX, renderY);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(renderX, renderY + renderHeight);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY + renderHeight);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY);
        GL11.glVertex2f(x + width, y);
    }
    
    private void renderBitmap(final int startChar, final int stopChar) {
        final BufferedImage[] fontImages = new BufferedImage[stopChar];
        int rowHeight = 0;
        int charX = 0;
        int charY = 0;
        for (int targetChar = startChar; targetChar < stopChar; ++targetChar) {
            final BufferedImage fontImage = this.drawCharToImage((char)targetChar);
            final CharLocation fontChar = new CharLocation(charX, charY, fontImage.getWidth(), fontImage.getHeight());
            if (fontChar.height > this.fontHeight) {
                this.fontHeight = fontChar.height;
            }
            if (fontChar.height > rowHeight) {
                rowHeight = fontChar.height;
            }
            if (this.charLocations.length > targetChar) {
                this.charLocations[targetChar] = fontChar;
                fontImages[targetChar] = fontImage;
                charX += fontChar.width;
                if (charX > 2048) {
                    if (charX > this.textureWidth) {
                        this.textureWidth = charX;
                    }
                    charX = 0;
                    charY += rowHeight;
                    rowHeight = 0;
                }
            }
        }
        this.textureHeight = charY + rowHeight;
        final BufferedImage bufferedImage = new BufferedImage(this.textureWidth, this.textureHeight, 2);
        final Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setFont(this.font);
        graphics2D.setColor(new Color(255, 255, 255, 0));
        graphics2D.fillRect(0, 0, this.textureWidth, this.textureHeight);
        graphics2D.setColor(Color.WHITE);
        for (int targetChar2 = startChar; targetChar2 < stopChar; ++targetChar2) {
            if (fontImages[targetChar2] != null && this.charLocations[targetChar2] != null) {
                graphics2D.drawImage(fontImages[targetChar2], this.charLocations[targetChar2].x, this.charLocations[targetChar2].y, null);
            }
        }
        this.textureID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), bufferedImage, true, true);
    }
    
    private BufferedImage drawCharToImage(final char ch) {
        final Graphics2D graphics2D = (Graphics2D)new BufferedImage(1, 1, 2).getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setFont(this.font);
        final FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int charWidth = fontMetrics.charWidth(ch) + 8;
        if (charWidth <= 8) {
            charWidth = 7;
        }
        int charHeight = fontMetrics.getHeight() + 3;
        if (charHeight <= 0) {
            charHeight = this.font.getSize();
        }
        final BufferedImage fontImage = new BufferedImage(charWidth, charHeight, 2);
        final Graphics2D graphics = (Graphics2D)fontImage.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setFont(this.font);
        graphics.setColor(Color.WHITE);
        graphics.drawString(String.valueOf(ch), 3, 1 + fontMetrics.getAscent());
        return fontImage;
    }
    
    public int getStringWidth(final String text) {
        int width = 0;
        for (final char ch : text.toCharArray()) {
            int index;
            if (ch < this.charLocations.length) {
                index = ch;
            }
            else {
                index = 3;
            }
            if (this.charLocations.length > index) {
                final CharLocation fontChar = this.charLocations[index];
                if (fontChar != null) {
                    width += fontChar.width - 8;
                }
            }
        }
        return width / 2;
    }
    
    public Font getFont() {
        return this.font;
    }
    
    static {
        AWTFontRenderer.assumeNonVolatile = false;
        activeFontRenderers = new ArrayList<AWTFontRenderer>();
        AWTFontRenderer.gcTicks = 0;
    }
    
    private static class CharLocation
    {
        private final int x;
        private final int y;
        private final int width;
        private final int height;
        
        CharLocation(final int x, final int y, final int width, final int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
