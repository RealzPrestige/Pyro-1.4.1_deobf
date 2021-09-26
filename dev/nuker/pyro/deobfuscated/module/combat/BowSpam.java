//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.item.ItemBow;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.client.EventClientTick;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class BowSpam extends Module
{
    public final Value<Integer> Ticks;
    private int _ticks;
    @EventHandler
    private Listener<EventClientTick> OnTick;
    
    public BowSpam() {
        super("BowSpam", new String[] { "BS" }, "Releases the bow as fast as possible", "NONE", 14361636, ModuleType.COMBAT);
        this.Ticks = new Value<Integer>("Ticks", new String[] { "Delay" }, "Number of ticks required between next bow release", 3, 0, 40, 1);
        this._ticks = 0;
        this.OnTick = new Listener<EventClientTick>(event -> {
            if (this.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && this.mc.player.isHandActive() && this.mc.player.getItemInUseMaxCount() >= 3 && ++this._ticks >= this.Ticks.getValue()) {
                this._ticks = 0;
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, this.mc.player.getHorizontalFacing()));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(this.mc.player.getActiveHand()));
                this.mc.player.stopActiveHand();
            }
        });
    }
}
