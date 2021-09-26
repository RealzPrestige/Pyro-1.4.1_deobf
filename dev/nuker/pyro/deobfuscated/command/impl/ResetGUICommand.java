// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.command.Command;

public class ResetGUICommand extends Command
{
    public ResetGUICommand() {
        super("ResetGUI", "Reset the ClickGUI positions to default");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        PyroStatic.CLICKGUI.ResetToDefaults();
        this.SendToChat("Reset the ClickGUI");
    }
    
    @Override
    public String getHelp() {
        return "Resets the positions of the ClickGUI";
    }
}
