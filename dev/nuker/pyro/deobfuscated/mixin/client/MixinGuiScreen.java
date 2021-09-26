//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderTooltip;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.gui.GuiButton;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiScreen.class })
public abstract class MixinGuiScreen
{
    @Shadow
    protected List<GuiButton> buttonList;
    @Shadow
    public int width;
    @Shadow
    public int height;
    @Shadow
    public Minecraft mc;
    @Shadow
    protected FontRenderer fontRenderer;
    
    @Inject(method = { "renderToolTip" }, at = { @At("HEAD") }, cancellable = true)
    public void renderToolTip(final ItemStack stack, final int x, final int y, final CallbackInfo p_Info) {
        final EventRenderTooltip l_Event = new EventRenderTooltip(stack, x, y);
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.cancel();
        }
    }
}
