//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.ListValue;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class Offhand extends Module
{
    private Value<Float> Delay;
    private ListValue Mode;
    private Value<Float> MinHealth;
    private Timer timer;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    
    public Offhand() {
        super("Offhand", new String[] { "Offhand" }, "Automatically places something in your offhand", "NONE", 14342948, ModuleType.COMBAT);
        this.Delay = new Value<Float>("Delay", new String[] { "D" }, "Delay", 0.1f, 0.0f, 1.0f, 0.1f);
        this.Mode = new ListValue("Mode", "Will switch offhand if it meets the required health", new String[] { "Crystal", "Gap" });
        this.MinHealth = new Value<Float>("MinHealth", new String[] { "MH" }, "MinHealth to pause this mod", 5.0f, 0.0f, 36.0f, 1.0f);
        this.timer = new Timer();
        int i;
        ItemStack s;
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            this.setMetaData(String.valueOf(PlayerUtil.GetItemCount(this.getCurrentItem())));
            if (this.mc.currentScreen instanceof GuiContainer && !(this.mc.currentScreen instanceof GuiInventory)) {
                return;
            }
            else if (this.mc.player.getHeldItemOffhand().getItem() == this.getCurrentItem()) {
                return;
            }
            else if (this.mc.player.isCreative()) {
                return;
            }
            else if (!this.offhandEnabled()) {
                return;
            }
            else {
                for (i = 0; i < this.mc.player.inventoryContainer.getInventory().size(); ++i) {
                    if (i != 0 && i != 5 && i != 6 && i != 7) {
                        if (i != 8) {
                            s = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i);
                            if (!s.isEmpty()) {
                                if (s.getItem().equals(this.getCurrentItem())) {
                                    if (this.mc.player.getHeldItemOffhand().isEmpty()) {
                                        if (this.timer.passed(this.Delay.getValue() * 1000.0f)) {
                                            this.mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                        }
                                        if (this.timer.passed(this.Delay.getValue() * 2000.0f)) {
                                            this.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                            this.timer.reset();
                                        }
                                    }
                                    else {
                                        if (this.timer.passed(this.Delay.getValue() * 1000.0f)) {
                                            this.mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                        }
                                        if (this.timer.passed(this.Delay.getValue() * 2000.0f)) {
                                            this.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                        }
                                        if (this.timer.passed(this.Delay.getValue() * 3000.0f)) {
                                            this.timer.reset();
                                            this.mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return;
            }
        });
        this.setMetaData("0");
    }
    
    private Item getCurrentItem() {
        final String s = this.Mode.getValue();
        switch (s) {
            case "Crystal": {
                return Items.END_CRYSTAL;
            }
            case "Gap": {
                return Items.GOLDEN_APPLE;
            }
            default: {
                return Items.GOLDEN_APPLE;
            }
        }
    }
    
    public boolean offhandEnabled() {
        return PlayerUtil.GetHealthWithAbsorption() > this.MinHealth.getValue();
    }
}
