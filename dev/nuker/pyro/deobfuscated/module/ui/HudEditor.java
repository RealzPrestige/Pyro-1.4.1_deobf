//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.ui;

import net.minecraft.client.gui.GuiScreen;
import dev.nuker.pyro.deobfuscated.gui.hud.GuiHudEditor;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class HudEditor extends Module
{
    public HudEditor() {
        super("HudEditor", new String[] { "HudEditor" }, "Displays the HudEditor", "GRAVE", 14403620, ModuleType.UI);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onToggle() {
        super.onToggle();
        if (this.mc.world != null) {
            this.mc.displayGuiScreen((GuiScreen)new GuiHudEditor(this));
        }
    }
}
