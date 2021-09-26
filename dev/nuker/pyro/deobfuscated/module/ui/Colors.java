// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.ui;

import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class Colors extends Module
{
    public final Value<Boolean> RainbowGUI;
    public final Value<Integer> Red;
    public final Value<Integer> Green;
    public final Value<Integer> Blue;
    public final Value<Integer> Alpha;
    public final Value<Float> ImageRed;
    public final Value<Float> ImageGreen;
    public final Value<Float> ImageBlue;
    public final Value<Float> ImageAlpha;
    
    public Colors() {
        super("Colors", new String[] { "Colors", "Colors" }, "Allows you to modify the GUI Colors", "NONE", -1, ModuleType.UI);
        this.RainbowGUI = new Value<Boolean>("RainbowGUI", new String[] { "RainbowGUI" }, "Renders the GUI in a rainbow", false);
        this.Red = new Value<Integer>("Red", new String[] { "bRed" }, "Red for rendering", 51, 0, 255, 11);
        this.Green = new Value<Integer>("Green", new String[] { "bGreen" }, "Green for rendering", 182, 0, 255, 11);
        this.Blue = new Value<Integer>("Blue", new String[] { "bBlue" }, "Blue for rendering", 215, 0, 255, 11);
        this.Alpha = new Value<Integer>("Alpha", new String[] { "bAlpha" }, "Alpha for rendering", 153, 0, 255, 11);
        this.ImageRed = new Value<Float>("ImageRed", new String[] { "iRed" }, "Red for rendering the icons", 1.0f, 0.0f, 1.0f, 0.1f);
        this.ImageGreen = new Value<Float>("ImageGreen", new String[] { "iGreen" }, "Green for rendering the icons", 1.0f, 0.0f, 1.0f, 0.1f);
        this.ImageBlue = new Value<Float>("ImageBlue", new String[] { "iBlue" }, "Blue for rendering the icons", 1.0f, 0.0f, 1.0f, 0.1f);
        this.ImageAlpha = new Value<Float>("ImageAlpha", new String[] { "iAlpha" }, "Alpha for rendering the icons", 1.0f, 0.0f, 1.0f, 0.1f);
    }
}
