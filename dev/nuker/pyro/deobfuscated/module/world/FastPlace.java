//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import net.minecraft.item.Item;
import java.util.Iterator;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import dev.nuker.pyro.deobfuscated.gui.hud.items.ArmorComponent;
import net.minecraft.item.ItemStack;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class FastPlace extends Module
{
    public final Value<Boolean> xp;
    public final Value<Boolean> Crystals;
    public final Value<Boolean> AutoXP;
    public final Value<Boolean> ArmorCheck;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdate;
    
    public FastPlace() {
        super("FastPlace", new String[] { "Fp" }, "Removes place delay", "NONE", 6740772, ModuleType.WORLD);
        this.xp = new Value<Boolean>("XP", new String[] { "EXP" }, "Only activate while holding XP bottles.", false);
        this.Crystals = new Value<Boolean>("Crystals", new String[] { "Cry" }, "Active only when using crystals", false);
        this.AutoXP = new Value<Boolean>("AutoXP", new String[] { "AutoEXP" }, "Automatically uses XP at your feet when hovered over", false);
        this.ArmorCheck = new Value<Boolean>("ArmorCheck", new String[] { "ArmorCheck" }, "Checks if your armor is already at full durability before processing AutoXP", true);
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            this.setMetaData(this.getMetaData());
            if (this.xp.getValue()) {
                if (this.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle || this.mc.player.getHeldItemOffhand().getItem() instanceof ItemExpBottle) {
                    this.mc.rightClickDelayTimer = 0;
                }
            }
            else if (this.Crystals.getValue()) {
                if (this.mc.player.inventory.getCurrentItem().getItem() == Items.END_CRYSTAL || this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                    this.mc.rightClickDelayTimer = 0;
                }
            }
            else {
                this.mc.rightClickDelayTimer = 0;
            }
            return;
        });
        boolean skip;
        final Iterator<ItemStack> iterator;
        ItemStack stack;
        this.onMotionUpdate = new Listener<EventPlayerMotionUpdate>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre || event.isCancelled()) {
                return;
            }
            else {
                if (this.ArmorCheck.getValue()) {
                    skip = false;
                    this.mc.player.getArmorInventoryList().iterator();
                    while (iterator.hasNext()) {
                        stack = iterator.next();
                        if (ArmorComponent.GetPctFromStack(stack) < 100.0f) {
                            skip = true;
                            break;
                        }
                    }
                    if (!skip) {
                        return;
                    }
                }
                if (this.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE && this.AutoXP.getValue()) {
                    event.cancel();
                    event.setPitch(90.0f);
                    event.setYaw(this.mc.player.rotationYaw);
                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                }
                return;
            }
        });
        this.setMetaData("EXP:0");
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.rightClickDelayTimer = 6;
    }
    
    @Override
    public String getMetaData() {
        if (this.xp.getValue()) {
            return "EXP:" + this.getItemCount(Items.EXPERIENCE_BOTTLE);
        }
        return null;
    }
    
    private int getItemCount(final Item input) {
        if (this.mc.player == null) {
            return 0;
        }
        int items = 0;
        for (int i = 0; i < 45; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == input) {
                items += stack.getCount();
            }
        }
        return items;
    }
}
