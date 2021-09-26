//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import java.util.Iterator;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import net.minecraft.init.Items;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.client.EventClientTick;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AntiBowkick extends Module
{
    public Value<Integer> delay;
    private Value<Integer> validDistance;
    private Value<Integer> minHealth;
    private int delayCounter;
    @EventHandler
    private Listener<EventClientTick> OnClientTick;
    
    public AntiBowkick() {
        super("AntiBowkick", new String[] { "" }, "If player within set range holds bow, toggle AutoTotem", "NONE", 10167515, ModuleType.COMBAT);
        this.delay = new Value<Integer>("Delay", new String[] { "Delay" }, "Amount of time before Re-enabling AutoTotem.", 60, 50, 200, 1);
        this.validDistance = new Value<Integer>("ValidDistance", new String[] { "ValidDistance" }, "The range players holding bows will be detected at.", 3, 0, 10, 1);
        this.minHealth = new Value<Integer>("MinHealth", new String[] { "MinHealth" }, "If health is lower than this amount module begins to work.", 8, 2, 20, 1);
        this.delayCounter = 0;
        final Iterator<EntityPlayer> iterator;
        EntityPlayer l_Player;
        float distance;
        float health;
        this.OnClientTick = new Listener<EventClientTick>(p_Event -> {
            this.mc.world.playerEntities.iterator();
            while (iterator.hasNext()) {
                l_Player = iterator.next();
                distance = this.mc.player.getDistance((Entity)l_Player);
                health = PlayerUtil.GetHealthWithAbsorption();
                if (l_Player != this.mc.player && this.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING && l_Player.getHeldItemMainhand().getItem() == Items.BOW && distance <= this.validDistance.getValue() && health <= this.minHealth.getValue() && PyroStatic.AUTOTOTEM.isEnabled()) {
                    PyroStatic.AUTOTOTEM.toggle();
                    this.delayCounter = 0;
                }
                if (!PyroStatic.AUTOTOTEM.isEnabled() && this.mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING && ++this.delayCounter > this.delay.getValue()) {
                    PyroStatic.AUTOTOTEM.toggle();
                    this.delayCounter = 0;
                }
            }
        });
    }
}
