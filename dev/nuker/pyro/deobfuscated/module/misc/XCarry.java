//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketCloseWindow;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class XCarry extends Module
{
    public final Value<Boolean> ForceCancel;
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    
    public XCarry() {
        super("XCarry", new String[] { "XCarry", "MoreInventory" }, "Allows you to carry items in your crafting and dragging slot", "NONE", 11734064, ModuleType.MISC);
        this.ForceCancel = new Value<Boolean>("ForceCancel", new String[] { "" }, "Forces canceling of all CPacketCloseWindow packets", false);
        CPacketCloseWindow packet;
        this.onClientPacket = new Listener<EventClientPacket>(event -> {
            if (event.getStage() == MinecraftEvent.Stage.Pre) {
                if (event.getPacket() instanceof CPacketCloseWindow) {
                    packet = (CPacketCloseWindow)event.getPacket();
                    if (packet.windowId == this.mc.player.inventoryContainer.windowId || this.ForceCancel.getValue()) {
                        event.cancel();
                    }
                }
            }
        });
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mc.world != null) {
            this.mc.player.connection.sendPacket((Packet)new CPacketCloseWindow(this.mc.player.inventoryContainer.windowId));
        }
    }
}
