//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.managers.NotificationManager;
import net.minecraft.world.World;
import net.minecraft.network.play.server.SPacketEntityStatus;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import java.util.HashMap;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class TotemPopNotifier extends Module
{
    public final Value<Boolean> ChatMessages;
    private HashMap<String, Integer> TotemPopContainer;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public TotemPopNotifier() {
        super("TotemPopNotifier", new String[] { "TPN" }, "Notifys when someone pops a totem!", "NONE", 2392795, ModuleType.MISC);
        this.ChatMessages = new Value<Boolean>("ChatMessages", new String[] { "ChatMessages" }, "Send Chat Messages.", true);
        this.TotemPopContainer = new HashMap<String, Integer>();
        SPacketEntityStatus l_Packet;
        Entity l_Entity;
        int l_Count;
        this.onServerPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                if (event.getPacket() instanceof SPacketEntityStatus) {
                    l_Packet = (SPacketEntityStatus)event.getPacket();
                    if (l_Packet.getOpCode() == 35) {
                        l_Entity = l_Packet.getEntity((World)this.mc.world);
                        if (l_Entity != null) {
                            l_Count = 1;
                            if (this.TotemPopContainer.containsKey(l_Entity.getName())) {
                                l_Count = this.TotemPopContainer.get(l_Entity.getName());
                                this.TotemPopContainer.put(l_Entity.getName(), ++l_Count);
                            }
                            else {
                                this.TotemPopContainer.put(l_Entity.getName(), l_Count);
                            }
                            NotificationManager.Get().AddNotification("TotemPop", l_Entity.getName() + " popped " + l_Count + " totem(s)!");
                            if (this.ChatMessages.getValue()) {
                                this.SendMessage(l_Entity.getName() + " popped " + l_Count + " totem(s)!");
                            }
                        }
                    }
                }
                return;
            }
        });
        final Iterator<EntityPlayer> iterator;
        EntityPlayer l_Player;
        int l_Count2;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            this.mc.world.playerEntities.iterator();
            while (iterator.hasNext()) {
                l_Player = iterator.next();
                if (!this.TotemPopContainer.containsKey(l_Player.getName())) {
                    continue;
                }
                else if (l_Player.isDead || l_Player.getHealth() <= 0.0f) {
                    l_Count2 = this.TotemPopContainer.get(l_Player.getName());
                    this.TotemPopContainer.remove(l_Player.getName());
                    NotificationManager.Get().AddNotification("TotemPop", l_Player.getName() + " died after popping " + l_Count2 + " totem(s)!");
                    if (this.ChatMessages.getValue()) {
                        this.SendMessage(l_Player.getName() + " died after popping " + l_Count2 + " totem(s)!");
                    }
                    else {
                        continue;
                    }
                }
                else {
                    continue;
                }
            }
        });
    }
}
