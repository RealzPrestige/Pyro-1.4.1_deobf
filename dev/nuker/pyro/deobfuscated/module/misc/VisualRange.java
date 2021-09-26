//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import dev.nuker.pyro.deobfuscated.managers.NotificationManager;
import dev.nuker.pyro.deobfuscated.managers.FriendManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import dev.nuker.pyro.deobfuscated.events.entity.EventEntityRemoved;
import dev.nuker.pyro.deobfuscated.events.entity.EventEntityAdded;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import java.util.List;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class VisualRange extends Module
{
    public final Value<String> Mode;
    public final Value<Boolean> Friends;
    public final Value<Boolean> Enter;
    public final Value<Boolean> Leave;
    private List<String> Entities;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventEntityAdded> OnEntityAdded;
    @EventHandler
    private Listener<EventEntityRemoved> OnEntityRemove;
    
    public VisualRange() {
        super("VisualRange", new String[] { "VR" }, "Notifies you when one enters or leaves your visual range.", "NONE", -1, ModuleType.MISC);
        this.Mode = new Value<String>("Mode", new String[] { "M" }, "Mode of notifying to use", "Both");
        this.Friends = new Value<Boolean>("Friends", new String[] { "Friend" }, "Notifies if a friend comes in range", true);
        this.Enter = new Value<Boolean>("Enter", new String[] { "Enters" }, "Notifies when the entity enters range", true);
        this.Leave = new Value<Boolean>("Leave", new String[] { "Leaves" }, "Notifies when the entity leaves range", true);
        this.Entities = new ArrayList<String>();
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> this.setMetaData(this.getMetaData()));
        this.OnEntityAdded = new Listener<EventEntityAdded>(p_Event -> {
            if (!this.Enter.getValue()) {
                return;
            }
            else if (!this.VerifyEntity(p_Event.GetEntity())) {
                return;
            }
            else {
                if (!this.Entities.contains(p_Event.GetEntity().getName())) {
                    this.Entities.add(p_Event.GetEntity().getName());
                    this.Notify(String.format("%s has entered your visual range.", p_Event.GetEntity().getName()));
                }
                return;
            }
        });
        this.OnEntityRemove = new Listener<EventEntityRemoved>(p_Event -> {
            if (!this.Leave.getValue()) {
                return;
            }
            else if (!this.VerifyEntity(p_Event.GetEntity())) {
                return;
            }
            else {
                if (this.Entities.contains(p_Event.GetEntity().getName())) {
                    this.Entities.remove(p_Event.GetEntity().getName());
                    this.Notify(String.format("%s has left your visual range.", p_Event.GetEntity().getName()));
                }
                return;
            }
        });
        this.setMetaData(this.getMetaData());
        this.Mode.addString("Chat");
        this.Mode.addString("Notification");
        this.Mode.addString("Both");
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.Entities.clear();
    }
    
    private boolean VerifyEntity(final Entity p_Entity) {
        return p_Entity instanceof EntityPlayer && p_Entity != this.mc.player && (this.Friends.getValue() || !FriendManager.Get().IsFriend(p_Entity));
    }
    
    private void Notify(final String p_Msg) {
        final String s = this.Mode.getValue();
        switch (s) {
            case "Chat": {
                this.SendMessage(p_Msg);
                break;
            }
            case "Notification": {
                NotificationManager.Get().AddNotification("VisualRange", p_Msg);
                break;
            }
            case "Both": {
                this.SendMessage(p_Msg);
                NotificationManager.Get().AddNotification("VisualRange", p_Msg);
                break;
            }
        }
    }
}
