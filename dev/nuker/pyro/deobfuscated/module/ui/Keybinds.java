// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.ui;

import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Keybinds extends Module
{
    public final Value<Boolean> Shift;
    public final Value<Boolean> Ctrl;
    public final Value<Boolean> Alt;
    
    public Keybinds() {
        super("Keybinds", new String[] { "Keys" }, "Allows you to modify the behavior of keybinds", "NONE", -1, ModuleType.UI);
        this.Shift = new Value<Boolean>("StrictShift", new String[] { "Shift" }, "Activates strict keybinds when shift key is down", false);
        this.Ctrl = new Value<Boolean>("StrictCtrl", new String[] { "Ctrl" }, "Activates strict keybinds when ctrl key is down", false);
        this.Alt = new Value<Boolean>("StrictAlt", new String[] { "Alt" }, "Activates strict keybinds when alt key is down", false);
    }
}
