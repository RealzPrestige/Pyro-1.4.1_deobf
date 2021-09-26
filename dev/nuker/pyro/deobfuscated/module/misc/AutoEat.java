//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import net.minecraft.init.Items;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerIsHandActive;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerOnStoppedUsingItem;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoEat extends Module
{
    private Value<String> mode;
    private Value<Boolean> autoSwitch;
    private Value<Boolean> pauseOnCrystal;
    private Value<Float> hunger;
    private Value<Float> health;
    boolean playerIsEating;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerOnStoppedUsingItem> OnStopUsingItem;
    @EventHandler
    private Listener<EventPlayerIsHandActive> onIsHandActive;
    
    public AutoEat() {
        super("AutoEat", new String[] { "Eat" }, "Automatically eats food, depending on hunger, or health", "NONE", 16379422, ModuleType.MISC);
        this.mode = new Value<String>("Mode", new String[] { "Mode", "M" }, "Check for Hunger or Health?", "Hunger");
        this.autoSwitch = new Value<Boolean>("AutoSwitch", new String[] { "AutoSwitch" }, "Automatically switches to Gaps.", true);
        this.pauseOnCrystal = new Value<Boolean>("PauseOnCrystal", new String[] { "PauseOnCrystal" }, "Pauses while CrystalAura is enabled.", false);
        this.hunger = new Value<Float>("Hunger", new String[] { "Hunger" }, "Only eats if hunger is below this amount. (Mode Hunger)", 16.0f, 0.0f, 20.0f, 1.0f);
        this.health = new Value<Float>("Health", new String[] { "Health" }, "Only eats if health is below this amount. (Mode Heatlh)", 16.0f, 0.0f, 36.0f, 1.0f);
        this.playerIsEating = false;
        boolean healthCheck;
        boolean hungerCheck;
        boolean mainhandHasGap;
        boolean offhandHasGap;
        int i;
        ItemStack stack;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (this.mode.getValue().equals("Finish")) {
                return;
            }
            else {
                healthCheck = (PlayerUtil.GetHealthWithAbsorption() <= this.health.getValue() && this.mode.getValue().equals("Health"));
                hungerCheck = (this.mc.player.getFoodStats().getSaturationLevel() <= this.hunger.getValue() && this.mode.getValue().equals("Hunger"));
                mainhandHasGap = (this.mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE);
                offhandHasGap = (this.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE);
                if (this.pauseOnCrystal.getValue() && PyroStatic.AUTOCRYSTAL.isEnabled()) {
                    this.playerIsEating = false;
                    return;
                }
                else {
                    if (mainhandHasGap && !offhandHasGap) {
                        if (healthCheck || hungerCheck) {
                            this.playerIsEating = true;
                            if (this.mc.rightClickDelayTimer != 0) {
                                return;
                            }
                            else {
                                this.mc.rightClickDelayTimer = 4;
                                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                            }
                        }
                    }
                    else if (this.autoSwitch.getValue() && !offhandHasGap && ((hungerCheck && !mainhandHasGap) || (healthCheck && !mainhandHasGap))) {
                        i = 0;
                        while (i < 9) {
                            stack = this.mc.player.inventory.getStackInSlot(i);
                            if (!stack.isEmpty() && stack.getItem() == Items.GOLDEN_APPLE) {
                                this.mc.player.inventory.currentItem = i;
                                this.mc.playerController.updateController();
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    if (this.mode.getValue().equals("Offhand")) {
                        if (offhandHasGap && !mainhandHasGap) {
                            if (hungerCheck || healthCheck) {
                                this.playerIsEating = true;
                            }
                            if (this.mc.rightClickDelayTimer != 0) {
                                return;
                            }
                            else {
                                this.mc.rightClickDelayTimer = 4;
                                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                            }
                        }
                        if ((this.mode.getValue().equals("Hunger") && this.mc.player.getFoodStats().getSaturationLevel() > this.hunger.getValue()) || (this.mode.getValue().equals("Health") && PlayerUtil.GetHealthWithAbsorption() > this.health.getValue())) {
                            this.playerIsEating = false;
                        }
                    }
                    return;
                }
            }
        });
        this.OnStopUsingItem = new Listener<EventPlayerOnStoppedUsingItem>(event -> {
            if (this.playerIsEating || this.mode.getValue().equals("Finish")) {
                event.cancel();
            }
            return;
        });
        this.onIsHandActive = new Listener<EventPlayerIsHandActive>(event -> {
            if (this.mode.getValue().equals("Offhand")) {
                event.cancel();
            }
            return;
        });
        this.mode.addString("Health");
        this.mode.addString("Hunger");
        this.mode.addString("Finish");
        this.mode.addString("Offhand");
    }
}
