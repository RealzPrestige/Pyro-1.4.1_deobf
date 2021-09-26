//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoMount extends Module
{
    public final Value<Boolean> Boats;
    public final Value<Boolean> Horses;
    public final Value<Boolean> SkeletonHorses;
    public final Value<Boolean> Donkeys;
    public final Value<Boolean> Pigs;
    public final Value<Boolean> Llamas;
    public final Value<Integer> Range;
    public final Value<Float> Delay;
    private Timer timer;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public AutoMount() {
        super("AutoMount", new String[] { "" }, "Automatically attempts to mount an entity near you", "NONE", -1, ModuleType.MISC);
        this.Boats = new Value<Boolean>("Boats", new String[] { "Boat" }, "Mounts boats", true);
        this.Horses = new Value<Boolean>("Horses", new String[] { "Horse" }, "Mounts Horses", true);
        this.SkeletonHorses = new Value<Boolean>("SkeletonHorses", new String[] { "SkeletonHorse" }, "Mounts SkeletonHorses", true);
        this.Donkeys = new Value<Boolean>("Donkeys", new String[] { "Donkey" }, "Mounts Donkeys", true);
        this.Pigs = new Value<Boolean>("Pigs", new String[] { "Pig" }, "Mounts Pigs", true);
        this.Llamas = new Value<Boolean>("Llamas", new String[] { "Llama" }, "Mounts Llamas", true);
        this.Range = new Value<Integer>("Range", new String[] { "R" }, "Range to search for mountable entities", 4, 0, 10, 1);
        this.Delay = new Value<Float>("Delay", new String[] { "D" }, "Delay to use", 1.0f, 0.0f, 10.0f, 1.0f);
        this.timer = new Timer();
        Entity l_Entity;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (!this.mc.player.isRiding()) {
                if (!(!this.timer.passed(this.Delay.getValue() * 1000.0f))) {
                    this.timer.reset();
                    l_Entity = (Entity)this.mc.world.loadedEntityList.stream().filter(p_Entity -> this.isValidEntity(p_Entity)).min(Comparator.comparing(p_Entity -> this.mc.player.getDistance(p_Entity))).orElse(null);
                    if (l_Entity != null) {
                        this.mc.playerController.interactWithEntity((EntityPlayer)this.mc.player, l_Entity, EnumHand.MAIN_HAND);
                    }
                }
            }
        });
    }
    
    private boolean isValidEntity(final Entity entity) {
        if (entity.getDistance((Entity)this.mc.player) > this.Range.getValue()) {
            return false;
        }
        if (entity instanceof AbstractHorse) {
            final AbstractHorse horse = (AbstractHorse)entity;
            if (horse.isChild()) {
                return false;
            }
        }
        if (entity instanceof EntityBoat && this.Boats.getValue()) {
            return true;
        }
        if (entity instanceof EntitySkeletonHorse && this.SkeletonHorses.getValue()) {
            return true;
        }
        if (entity instanceof EntityHorse && this.Horses.getValue()) {
            return true;
        }
        if (entity instanceof EntityDonkey && this.Donkeys.getValue()) {
            return true;
        }
        if (entity instanceof EntityPig && this.Pigs.getValue()) {
            final EntityPig pig = (EntityPig)entity;
            return pig.getSaddled();
        }
        if (entity instanceof EntityLlama && this.Llamas.getValue()) {
            final EntityLlama l_Llama = (EntityLlama)entity;
            if (!l_Llama.isChild()) {
                return true;
            }
        }
        return false;
    }
}
