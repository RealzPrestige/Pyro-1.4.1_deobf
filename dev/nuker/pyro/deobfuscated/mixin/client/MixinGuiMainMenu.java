//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import dev.nuker.pyro.deobfuscated.gui.altmanager.GuiAltList;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.client.gui.GuiButton;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiMainMenu.class })
public class MixinGuiMainMenu extends MixinGuiScreen
{
    @Inject(method = { "initGui" }, at = { @At("HEAD") }, cancellable = true)
    public void initGui(final CallbackInfo info) {
        final int j = this.height / 4 + 70;
        this.buttonList.add(new GuiButton(420, this.width / 2 - 100, j + 72 + 12, 98, 20, "Alt Manager"));
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") }, cancellable = true)
    protected void actionPerformed(final GuiButton button, final CallbackInfo info) throws IOException {
        if (button.id == 420) {
            this.mc.displayGuiScreen((GuiScreen)new GuiAltList());
            info.cancel();
        }
    }
}
