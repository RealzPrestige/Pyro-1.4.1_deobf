// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import io.netty.channel.ChannelHandlerContext;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ NetworkManager.class })
public class MixinNetworkManager
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void sendPacketPre(final Packet<?> packet, final CallbackInfo info) {
        final EventClientPacket event = new EventClientPacket(packet, MinecraftEvent.Stage.Pre);
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("RETURN") })
    private void sendPacketPost(final Packet<?> packet, final CallbackInfo callbackInfo) {
        PyroMod.EVENT_BUS.post(new EventClientPacket(packet, MinecraftEvent.Stage.Post));
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void channelReadPre(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo info) {
        final EventServerPacket event = new EventServerPacket(packet, MinecraftEvent.Stage.Pre);
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("RETURN") })
    private void channelReadPost(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo info) {
        final EventServerPacket event = new EventServerPacket(packet, MinecraftEvent.Stage.Post);
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}
