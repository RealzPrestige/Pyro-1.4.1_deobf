//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.events.world.EventChunkLoad;
import dev.nuker.pyro.deobfuscated.PyroMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.play.server.SPacketChunkData;
import org.spongepowered.asm.mixin.Final;
import net.minecraft.network.NetworkManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ NetHandlerPlayClient.class })
public abstract class MixinNetHandlerPlayClient
{
    @Shadow
    private WorldClient world;
    @Shadow
    private Minecraft client;
    @Shadow
    private boolean doneLoadingTerrain;
    @Shadow
    @Final
    private NetworkManager netManager;
    @Shadow
    public int currentServerMaxPlayers;
    
    @Inject(method = { "handleChunkData" }, at = { @At("RETURN") })
    public void handleChunkData(final SPacketChunkData packet, final CallbackInfo info) {
        PyroMod.EVENT_BUS.post(new EventChunkLoad(EventChunkLoad.Type.LOAD, this.world.getChunk(packet.getChunkX(), packet.getChunkZ())));
    }
}
