//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.util.StringUtils;
import java.util.Iterator;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import java.util.UUID;
import java.util.Map;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AntiBots extends Module
{
    private Map<Integer, UUID> _playersMap;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    @EventHandler
    private Listener<EntityJoinWorldEvent> OnWorldEvent;
    
    public AntiBots() {
        super("AntiBots", new String[] { "AB" }, "Removes bots from the entitylist if detected, not useful for 2b.", "NONE", 7682624, ModuleType.COMBAT);
        this._playersMap = new HashMap<Integer, UUID>();
        final Iterator<EntityPlayer> iterator;
        EntityPlayer player;
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (this.mc.getCurrentServerData() == null) {
                return;
            }
            else {
                new ArrayList<EntityPlayer>(this.mc.world.playerEntities).iterator();
                while (iterator.hasNext()) {
                    player = iterator.next();
                    if (this.isBot(player)) {
                        this.mc.world.playerEntities.remove(player);
                    }
                }
                return;
            }
        });
        SPacketSpawnPlayer packet;
        SPacketDestroyEntities packet2;
        final int[] array;
        int length;
        int i = 0;
        int e;
        this.onServerPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else if (this.mc.world == null || this.mc.player == null) {
                return;
            }
            else {
                if (event.getPacket() instanceof SPacketSpawnPlayer) {
                    packet = (SPacketSpawnPlayer)event.getPacket();
                    if (Math.sqrt((this.mc.player.posX - packet.getX() / 0.0) * (this.mc.player.posX - packet.getX() / 0.0) + (this.mc.player.posY - packet.getY() / 0.0) * (this.mc.player.posY - packet.getY() / 0.0) + (this.mc.player.posZ - packet.getZ() / 0.0) * (this.mc.player.posZ - packet.getZ() / 0.0)) <= 0.0 && packet.getX() / 0.0 != this.mc.player.posX && packet.getY() / 0.0 != this.mc.player.posY && packet.getZ() / 0.0 != this.mc.player.posZ) {
                        this._playersMap.put(packet.getEntityID(), packet.getUniqueId());
                    }
                }
                else if (event.getPacket() instanceof SPacketDestroyEntities) {
                    packet2 = (SPacketDestroyEntities)event.getPacket();
                    packet2.getEntityIDs();
                    for (length = array.length; i < length; ++i) {
                        e = array[i];
                        if (this._playersMap.containsKey(e)) {
                            this._playersMap.remove(e);
                        }
                    }
                }
                return;
            }
        });
        this.OnWorldEvent = new Listener<EntityJoinWorldEvent>(p_Event -> {
            if (p_Event.getEntity() == this.mc.player) {
                this._playersMap.clear();
            }
        });
    }
    
    public boolean isBot(final EntityPlayer entity) {
        return entity.getUniqueID().toString().startsWith(entity.getName()) || !StringUtils.stripControlCodes(entity.getGameProfile().getName()).equals(entity.getName()) || entity.getGameProfile().getId() != entity.getUniqueID() || this._playersMap.containsKey(entity.getEntityId());
    }
}
