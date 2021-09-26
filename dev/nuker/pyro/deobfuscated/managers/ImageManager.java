//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.managers;

import dev.nuker.pyro.deobfuscated.main.Pyro;

import java.util.Map;
import java.io.InputStream;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.Minecraft;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.TreeMap;
import dev.nuker.pyro.deobfuscated.util.imgs.SalDynamicTexture;
import java.util.NavigableMap;

public class ImageManager
{
    public NavigableMap<String, SalDynamicTexture> Pictures;
    
    public ImageManager() {
        this.Pictures = new TreeMap<String, SalDynamicTexture>();
    }
    
    public void Load() {
    }
    
    public void LoadImage(final String p_Img) {
        BufferedImage l_Image = null;
        final InputStream l_Stream = ImageManager.class.getResourceAsStream("/assets/Pyro/imgs/" + p_Img + ".png");
        try {
            l_Image = ImageIO.read(l_Stream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (l_Image == null) {
            System.out.println("Couldn't load image: " + p_Img);
            return;
        }
        final int l_Height = l_Image.getHeight();
        final int l_Width = l_Image.getWidth();
        final SalDynamicTexture l_Texture = new SalDynamicTexture(l_Image, l_Height, l_Width);
        if (l_Texture != null) {
            l_Texture.SetResourceLocation(Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("Pyro/textures", (DynamicTexture)l_Texture));
            this.Pictures.put(p_Img, l_Texture);
            System.out.println("Loaded Img: " + p_Img);
        }
    }
    
    public SalDynamicTexture GetDynamicTexture(final String p_Image) {
        if (this.Pictures.containsKey(p_Image)) {
            return this.Pictures.get(p_Image);
        }
        return null;
    }
    
    public String GetNextImage(final String value, final boolean p_Recursive) {
        String l_String = null;
        for (final Map.Entry<String, SalDynamicTexture> l_Itr : this.Pictures.entrySet()) {
            if (!l_Itr.getKey().equalsIgnoreCase(value)) {
                continue;
            }
            if (p_Recursive) {
                l_String = this.Pictures.lowerKey(l_Itr.getKey());
                if (l_String == null) {
                    return this.Pictures.lastKey();
                }
            }
            else {
                l_String = this.Pictures.higherKey(l_Itr.getKey());
                if (l_String == null) {
                    return this.Pictures.firstKey();
                }
            }
            return l_String;
        }
        return l_String;
    }
    
    public static ImageManager Get() {
        return Pyro.GetImageManager();
    }
}
