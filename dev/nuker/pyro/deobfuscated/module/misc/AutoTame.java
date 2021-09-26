//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.network.Packet;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.network.play.client.CPacketUseEntity;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import net.minecraft.entity.passive.AbstractHorse;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoTame extends Module
{
    public final Value<Float> Delay;
    private AbstractHorse EntityToTame;
    private Timer timer;
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public AutoTame() {
        super("AutoTame", new String[] { "" }, "Automatically tames the animal you click", "NONE", 14361796, ModuleType.MISC);
        this.Delay = new Value<Float>("Delay", new String[] { "D" }, "Delay to remount", 0.1f, 0.0f, 1.0f, 0.1f);
        this.EntityToTame = null;
        this.timer = new Timer();
        CPacketUseEntity l_Packet;
        Entity l_Entity;
        this.onClientPacket = new Listener<EventClientPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                if (event.getPacket() instanceof CPacketUseEntity) {
                    if (this.EntityToTame == null) {
                        l_Packet = (CPacketUseEntity)event.getPacket();
                        l_Entity = l_Packet.getEntityFromWorld((World)this.mc.world);
                        if (l_Entity instanceof AbstractHorse && !((AbstractHorse)l_Entity).isTame()) {
                            this.EntityToTame = (AbstractHorse)l_Entity;
                            this.SendMessage("Will try to tame " + l_Entity.getName());
                            this.setMetaData(l_Entity.getName());
                        }
                    }
                }
                return;
            }
        });
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.EntityToTame != null) {
                if (this.EntityToTame.isTame()) {
                    this.SendMessage("Successfully tamed " + this.EntityToTame.getName() + ", disabling.");
                    this.toggle();
                }
                else if (!this.mc.player.isRiding()) {
                    if (this.mc.player.getDistance((Entity)this.EntityToTame) <= 5.0f) {
                        if (!(!this.timer.passed(this.Delay.getValue() * 1000.0f))) {
                            this.timer.reset();
                            this.mc.getConnection().sendPacket((Packet)new CPacketUseEntity((Entity)this.EntityToTame, EnumHand.MAIN_HAND));
                        }
                    }
                }
            }
        });
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.SendMessage("Right click an animal you want to tame");
        this.EntityToTame = null;
    }
}
