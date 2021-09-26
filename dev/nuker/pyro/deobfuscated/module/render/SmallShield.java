//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.util.EnumHand;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderUpdateEquippedItem;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class SmallShield extends Module
{
    public final Value<Float> MainProgress;
    public final Value<Float> OffProgress;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventRenderUpdateEquippedItem> OnUpdateEquippedItem;
    
    public SmallShield() {
        super("SmallShield", new String[] { "SmallShield", "SS", "HandProgress" }, "Smaller view of mainhand/offhand, smallshield", "NONE", 9032627, ModuleType.RENDER);
        this.MainProgress = new Value<Float>("MainProgress", new String[] { "" }, "Mainhand progress", 0.5f, 0.0f, 1.0f, 0.1f);
        this.OffProgress = new Value<Float>("OffProgress", new String[] { "" }, "Offhand progress", 0.5f, 0.0f, 1.0f, 0.1f);
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            this.mc.entityRenderer.itemRenderer.equippedProgressMainHand = this.MainProgress.getValue();
            this.mc.entityRenderer.itemRenderer.equippedProgressOffHand = this.OffProgress.getValue();
            return;
        });
        this.OnUpdateEquippedItem = new Listener<EventRenderUpdateEquippedItem>(event -> {
            this.mc.entityRenderer.itemRenderer.itemStackMainHand = this.mc.player.getHeldItem(EnumHand.MAIN_HAND);
            this.mc.entityRenderer.itemRenderer.itemStackOffHand = this.mc.player.getHeldItem(EnumHand.OFF_HAND);
        });
    }
}
