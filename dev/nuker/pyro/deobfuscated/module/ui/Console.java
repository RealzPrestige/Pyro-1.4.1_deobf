//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.ui;

import net.minecraft.client.gui.GuiScreen;
import dev.nuker.pyro.deobfuscated.gui.chat.SalGuiConsole;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class Console extends Module
{
    private SalGuiConsole m_Console;
    
    public Console() {
        super("Console", new String[] { "Console" }, "Displays the click gui", "U", 14397476, ModuleType.UI);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onToggle() {
        super.onToggle();
        if (this.mc.world != null) {
            if (this.m_Console == null) {
                this.m_Console = new SalGuiConsole(this);
            }
            this.mc.displayGuiScreen((GuiScreen)this.m_Console);
        }
    }
}
