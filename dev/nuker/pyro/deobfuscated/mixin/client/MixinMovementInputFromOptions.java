//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import org.spongepowered.asm.mixin.injection.Redirect;
import dev.nuker.pyro.deobfuscated.util.CameraUtils;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdateMoveState;
import dev.nuker.pyro.deobfuscated.PyroMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.util.MovementInput;

@Mixin(value = { MovementInputFromOptions.class }, priority = 10000)
public abstract class MixinMovementInputFromOptions extends MovementInput
{
    @Inject(method = { "updatePlayerMoveState" }, at = { @At("RETURN") })
    public void updatePlayerMoveStateReturn(final CallbackInfo callback) {
        PyroMod.EVENT_BUS.post(new EventPlayerUpdateMoveState());
    }
    
    @Redirect(method = { "updatePlayerMoveState" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    public boolean isKeyPressed(final KeyBinding keyBinding) {
        return !CameraUtils.freecamEnabled() && keyBinding.isKeyDown();
    }
}
