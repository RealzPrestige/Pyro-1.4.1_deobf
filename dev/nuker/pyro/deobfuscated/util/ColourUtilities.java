// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import java.awt.Color;
import java.util.Random;

public class ColourUtilities
{
    public static final int[] RAINBOW_COLORS;
    private static Random random;
    
    public static double[] toRGBA(final int hex) {
        return new double[] { (hex >> 16 & 0xFF) / 255.0, (hex >> 8 & 0xFF) / 255.0, (hex & 0xFF) / 255.0, (hex >> 24 & 0xFF) / 255.0, (hex >> 24 & 0xFF) / 255.0 };
    }
    
    public static int generateColor() {
        final float hue = ColourUtilities.random.nextFloat();
        final float saturation = ColourUtilities.random.nextInt(5000) / 10000.0f + 0.5f;
        final float brightness = ColourUtilities.random.nextInt(5000) / 10000.0f + 0.5f;
        return Color.HSBtoRGB(hue, saturation, brightness);
    }
    
    public static int generateWaypointColour() {
        return Color.HSBtoRGB((float)Math.random(), (float)Math.random() / 4.0f + 0.75f, (float)Math.random() / 2.0f + 0.25f);
    }
    
    public static Color blend(final Color color1, final Color color2, final float ratio) {
        if (ratio < 0.0f) {
            return color2;
        }
        if (ratio > 1.0f) {
            return color1;
        }
        final float ratio2 = 1.0f - ratio;
        final float[] rgb1 = new float[3];
        final float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        return new Color(rgb1[0] * ratio + rgb2[0] * ratio2, rgb1[1] * ratio + rgb2[1] * ratio2, rgb1[2] * ratio + rgb2[2] * ratio2);
    }
    
    public static int parseColor(String color) {
        if (color.startsWith("#")) {
            color = color.substring(1);
        }
        if (color.toLowerCase().startsWith("0x")) {
            color = color.substring(2);
        }
        try {
            return (int)Long.parseLong(color, 16);
        }
        catch (Exception ex) {
            return -1;
        }
    }
    
    static {
        RAINBOW_COLORS = new int[] { 16711680, 16728064, 16744192, 16776960, 8453888, 65280, 65535, 33023, 255 };
        ColourUtilities.random = new Random();
    }
}
