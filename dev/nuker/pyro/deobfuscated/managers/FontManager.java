//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.managers;

import dev.nuker.pyro.deobfuscated.main.Pyro;
import java.io.InputStream;
import java.awt.Font;
import dev.nuker.pyro.deobfuscated.util.render.GameFontRenderer;

public class FontManager
{
    public GameFontRenderer badaboom;
    public GameFontRenderer lato;
    public GameFontRenderer lato80;
    
    public FontManager() {
        this.badaboom = null;
        this.lato = null;
        this.lato80 = null;
    }
    
    public void Load() {
        try {
            this.badaboom = new GameFontRenderer(getFont("badaboom.ttf", 40.0f));
            this.lato = new GameFontRenderer(getFont("Lato-Medium.ttf", 40.0f));
            this.lato80 = new GameFontRenderer(getFont("Lato-Medium.ttf", 80.0f));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public GameFontRenderer getGameFont() {
        return this.lato;
    }
    
    private static Font getFont(final String fontName, final float size) {
        try {
            final InputStream inputStream = FontManager.class.getResourceAsStream("/assets/Pyro/fonts/" + fontName);
            Font awtClientFont = Font.createFont(0, inputStream);
            awtClientFont = awtClientFont.deriveFont(0, size);
            inputStream.close();
            return awtClientFont;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Font("default", 0, (int)size);
        }
    }
    
    public void LoadCustomFont(final String customFont) {
        this.lato = new GameFontRenderer(new Font(customFont, 0, 19));
    }
    
    public float GetStringHeight(final String p_Name) {
        return (float)this.getGameFont().getHeight();
    }
    
    public float GetStringWidth(final String p_Name) {
        return (float)this.getGameFont().getStringWidth(p_Name);
    }
    
    public static FontManager Get() {
        return Pyro.GetFontManager();
    }
}
