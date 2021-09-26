//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.world.IBlockAccess;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import java.util.Comparator;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemShears;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoShear extends Module
{
    public final Value<Integer> Radius;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AutoShear() {
        super("AutoShear", new String[] { "" }, "Shears sheep in range", "NONE", -1, ModuleType.MISC);
        this.Radius = new Value<Integer>("Radius", new String[] { "R" }, "Radius to search for sheep", 4, 0, 10, 1);
        EntitySheep l_Sheep;
        double[] l_Pos;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (!(!(this.mc.player.getHeldItemMainhand().getItem() instanceof ItemShears))) {
                l_Sheep = (EntitySheep)this.mc.world.loadedEntityList.stream().filter(p_Entity -> this.IsValidSheep(p_Entity)).map(p_Entity -> (EntitySheep)p_Entity).min(Comparator.comparing(p_Entity -> this.mc.player.getDistance(p_Entity))).orElse(null);
                if (l_Sheep != null) {
                    p_Event.cancel();
                    l_Pos = EntityUtil.calculateLookAt(l_Sheep.posX, l_Sheep.posY, l_Sheep.posZ, (EntityPlayer)this.mc.player);
                    p_Event.setPitch(l_Pos[1]);
                    p_Event.setYaw(l_Pos[0]);
                    this.mc.getConnection().sendPacket((Packet)new CPacketUseEntity((Entity)l_Sheep, EnumHand.MAIN_HAND));
                }
            }
        });
    }
    
    private boolean IsValidSheep(final Entity p_Entity) {
        if (!(p_Entity instanceof EntitySheep)) {
            return false;
        }
        if (p_Entity.getDistance((Entity)this.mc.player) > this.Radius.getValue()) {
            return false;
        }
        final EntitySheep l_Sheep = (EntitySheep)p_Entity;
        return l_Sheep.isShearable(this.mc.player.getHeldItemMainhand(), (IBlockAccess)this.mc.world, p_Entity.getPosition());
    }
}
