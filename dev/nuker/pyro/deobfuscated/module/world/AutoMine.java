//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import net.minecraft.client.settings.KeyBinding;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoMine extends Module
{
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    
    public AutoMine() {
        super("AutoMine", new String[] { "AM" }, "Holds down your left click.", "NONE", -1, ModuleType.WORLD);
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindAttack.getKeyCode(), true));
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindAttack.getKeyCode(), false);
    }
}
