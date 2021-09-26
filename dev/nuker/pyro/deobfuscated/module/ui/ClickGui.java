//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.ui;

import net.minecraft.client.gui.GuiScreen;
import dev.nuker.pyro.deobfuscated.guiclick2.ClickGuiS;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class ClickGui extends Module
{
    public final Value<Boolean> AllowOverflow;
    public final Value<Boolean> Watermark;
    public final Value<Boolean> HoverDescriptions;
    public final Value<Boolean> Snowing;
    
    public ClickGui() {
        super("ClickGui", new String[] { "ClickGui", "ClickGui" }, "Displays the click gui", "RSHIFT", 14390052, ModuleType.UI);
        this.AllowOverflow = new Value<Boolean>("AllowOverflow", new String[] { "AllowOverflow" }, "Allows the GUI to overflow", true);
        this.Watermark = new Value<Boolean>("Watermark", new String[] { "Watermark" }, "Displays the watermark on the GUI", true);
        this.HoverDescriptions = new Value<Boolean>("HoverDescriptions", new String[] { "HD" }, "Displays hover descriptions over values and modules", true);
        this.Snowing = new Value<Boolean>("Snowing", new String[] { "SN" }, "Play a snowing animation in ClickGUI", true);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.world != null) {
            this.mc.displayGuiScreen((GuiScreen)new ClickGuiS());
        }
    }
    
    public void ResetToDefaults() {
    }
}
