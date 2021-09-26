//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.util.math.MathHelper;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.item.ItemChorusFruit;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.entity.EventItemUseFinish;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Module;

public class ChorusFruitBypass extends Module
{
    private boolean needChorusFruitBypass;
    @EventHandler
    private Listener<EventItemUseFinish> onItemUseFinish;
    
    public ChorusFruitBypass() {
        super("ChorusBypass", new String[] { "ChorusFruit" }, "Bypass chorus fruit", "NONE", -1, ModuleType.MISC);
        this.needChorusFruitBypass = false;
        this.onItemUseFinish = new Listener<EventItemUseFinish>(event -> {
            if (!event.getActive().isEmpty() && this.mc.player.isHandActive() && event.getEntity() == this.mc.player && event.getActive().getItem() instanceof ItemChorusFruit) {
                this.needChorusFruitBypass = true;
                this.SendMessage("Ate a chorus fruit");
                this.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, this.mc.player.onGround));
            }
        });
    }
    
    public boolean handlePlayerPosLook(final SPacketPlayerPosLook packet) {
        if (!this.needChorusFruitBypass) {
            return true;
        }
        this.needChorusFruitBypass = false;
        this.SendMessage("SPacketPlayerPosLook!");
        this.SendMessage("X: " + MathHelper.floor(packet.getX()) + " Y: " + MathHelper.floor(packet.getY()) + " Z: " + MathHelper.floor(packet.getZ()));
        this.SendMessage("X: " + MathHelper.floor(this.mc.player.posX) + " Y: " + MathHelper.floor(this.mc.player.posY) + " Z: " + MathHelper.floor(this.mc.player.posZ));
        final int x = MathHelper.floor(packet.getX());
        final int y = MathHelper.floor(packet.getY());
        final int z = MathHelper.floor(packet.getZ());
        final int playerX = MathHelper.floor(this.mc.player.posX);
        final int playerY = MathHelper.floor(this.mc.player.posY);
        final int playerZ = MathHelper.floor(this.mc.player.posZ);
        this.mc.player.setPosition(packet.getX(), packet.getY(), packet.getZ());
        return true;
    }
}
