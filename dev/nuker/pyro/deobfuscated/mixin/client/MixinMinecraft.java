//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import dev.nuker.pyro.deobfuscated.events.world.EventLoadWorld;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.util.CrystalUtils2;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.gui.ingame.SalGuiIngame;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Minecraft.class })
public class MixinMinecraft
{
    @Shadow
    WorldClient world;
    @Shadow
    EntityPlayerSP player;
    @Shadow
    GuiScreen currentScreen;
    @Shadow
    GameSettings gameSettings;
    @Shadow
    GuiIngame ingameGUI;
    @Shadow
    boolean skipRenderWorld;
    @Shadow
    SoundHandler soundHandler;
    @Shadow
    private int leftClickCounter;
    @Shadow
    public ParticleManager effectRenderer;
    @Shadow
    public RayTraceResult objectMouseOver;
    @Shadow
    public PlayerControllerMP playerController;
    
    @Inject(method = { "init" }, at = { @At("RETURN") })
    private void init(final CallbackInfo callbackInfo) {
        this.ingameGUI = (GuiIngame)new SalGuiIngame(Wrapper.GetMC());
    }
    
    @Inject(method = { "loadWorld" }, at = { @At("HEAD") })
    private void loadWorld(@Nullable final WorldClient worldClientIn, final CallbackInfo info) {
        CrystalUtils2.loadWorld();
        PyroMod.EVENT_BUS.post(new EventLoadWorld());
    }
}
