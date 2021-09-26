//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.util.text.ITextComponent;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoSign extends Module
{
    public static final Value<String> Line1;
    public static final Value<String> Line2;
    public static final Value<String> Line3;
    public static final Value<String> Line4;
    public static final Value<Boolean> AutoComplete;
    private Timer _completeTimer;
    private boolean _wasInSign;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public AutoSign() {
        super("AutoSign", new String[] { "AutoSignz" }, "Automatically writes texts on signs for you", "NONE", -1, ModuleType.MISC);
        this._completeTimer = new Timer();
        this._wasInSign = false;
        GuiEditSign sign;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.mc.currentScreen != null) {
                if (!(this.mc.currentScreen instanceof GuiEditSign)) {
                    this._wasInSign = false;
                }
                else if (!this._wasInSign) {
                    this._completeTimer.reset();
                    this._wasInSign = true;
                }
                else {
                    sign = (GuiEditSign)this.mc.currentScreen;
                    if (this._completeTimer.passed(3000.0)) {
                        this._completeTimer.reset();
                        sign.tileSign.markDirty();
                        this.mc.displayGuiScreen((GuiScreen)null);
                    }
                    else {
                        sign.tileSign.signText[0] = (ITextComponent)new TextComponentString(this.genLine(AutoSign.Line1.getValue()));
                        sign.tileSign.signText[1] = (ITextComponent)new TextComponentString(this.genLine(AutoSign.Line2.getValue()));
                        sign.tileSign.signText[2] = (ITextComponent)new TextComponentString(this.genLine(AutoSign.Line3.getValue()));
                        sign.tileSign.signText[3] = (ITextComponent)new TextComponentString(this.genLine(AutoSign.Line4.getValue()));
                    }
                }
            }
        });
    }
    
    private String genLine(String val) {
        if (val.equals("~currentdate~")) {
            final LocalDate date = LocalDate.now();
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return date.format(formatter);
        }
        if (val.contains("~playername~")) {
            val = val.replace("~playername~", this.mc.player.getName());
        }
        return val;
    }
    
    static {
        Line1 = new Value<String>("Line1", new String[] { "" }, "The first sign line", "Pyro");
        Line2 = new Value<String>("Line2", new String[] { "" }, "The second sign line", "on");
        Line3 = new Value<String>("Line3", new String[] { "" }, "The third sign line", "top!");
        Line4 = new Value<String>("Line4", new String[] { "" }, "The fourth sign line", "~currentdate~");
        AutoComplete = new Value<Boolean>("AutoComplete", new String[] { "AC" }, "Automatially completes the sign for you", true);
    }
}
