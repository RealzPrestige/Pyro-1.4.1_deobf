//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemElytra;
import dev.nuker.pyro.deobfuscated.gui.hud.items.ArmorComponent;
import net.minecraft.inventory.EntityEquipmentSlot;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import net.minecraft.client.gui.inventory.GuiInventory;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class AutoArmor extends Module
{
    public final Value<Float> delay;
    public final Value<Boolean> curse;
    public final Value<Boolean> PreferElytra;
    public final Value<Boolean> ElytraReplace;
    private Timer timer;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public AutoArmor() {
        super("AutoArmor", new String[] { "AutoArm", "AutoArmour" }, "Automatically equips armor", "NONE", 2400219, ModuleType.COMBAT);
        this.delay = new Value<Float>("Delay", new String[] { "Del" }, "The amount of delay in milliseconds.", 50.0f, 0.0f, 1000.0f, 1.0f);
        this.curse = new Value<Boolean>("Curse", new String[] { "Curses" }, "Prevents you from equipping armor with cursed enchantments.", false);
        this.PreferElytra = new Value<Boolean>("Elytra", new String[] { "Wings" }, "Prefers elytra over chestplate if available", false);
        this.ElytraReplace = new Value<Boolean>("ElytraReplace", new String[] { "ElytraReplace" }, "Attempts to replace your broken elytra", false);
        this.timer = new Timer();
        ItemStack stack;
        int i;
        ItemStack s;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (!(this.mc.currentScreen instanceof GuiInventory)) {
                if (!PyroStatic.AUTOMEND.isEnabled()) {
                    this.SwitchItemIfNeed(this.mc.player.inventoryContainer.getSlot(5).getStack(), EntityEquipmentSlot.HEAD, 5);
                    this.SwitchItemIfNeed(this.mc.player.inventoryContainer.getSlot(6).getStack(), EntityEquipmentSlot.CHEST, 6);
                    this.SwitchItemIfNeed(this.mc.player.inventoryContainer.getSlot(7).getStack(), EntityEquipmentSlot.LEGS, 7);
                    this.SwitchItemIfNeed(this.mc.player.inventoryContainer.getSlot(8).getStack(), EntityEquipmentSlot.FEET, 8);
                    if (this.ElytraReplace.getValue() && !this.mc.player.inventoryContainer.getSlot(6).getStack().isEmpty()) {
                        stack = this.mc.player.inventoryContainer.getSlot(6).getStack();
                        if (ArmorComponent.GetPctFromStack(stack) < 3.0f && PyroStatic.AUTOWALK.isEnabled() && PyroStatic.STASHFINDER.automaticWalk.getValue()) {
                            this.SendMessage("Turned Off Autowalk");
                            PyroStatic.AUTOWALK.toggle();
                        }
                        if (ArmorComponent.GetPctFromStack(stack) > 10.0f && !PyroStatic.AUTOWALK.isEnabled() && PyroStatic.STASHFINDER.automaticWalk.getValue() && this.mc.player.ticksExisted % 25 == 0) {
                            PyroStatic.AUTOWALK.toggle();
                            this.SendMessage("Began AutoWalking Swap Complete");
                        }
                        if (stack.getItem() instanceof ItemElytra && ArmorComponent.GetPctFromStack(stack) < 2.0f) {
                            for (i = 0; i < this.mc.player.inventoryContainer.getInventory().size(); ++i) {
                                if (i != 0 && i != 5 && i != 6 && i != 7) {
                                    if (i != 8) {
                                        s = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i);
                                        if (s != null && s.getItem() != Items.AIR && s.getItem() instanceof ItemElytra && ItemElytra.isUsable(s) && ArmorComponent.GetPctFromStack(s) > 10.0f) {
                                            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, i, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, i, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                            this.SendMessage("Switched to better elytra");
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }
    
    private void SwitchItemIfNeed(final ItemStack p_Stack, final EntityEquipmentSlot p_Slot, final int p_ArmorSlot) {
        if (p_Stack.getItem() == Items.AIR) {
            if (!this.timer.passed(this.delay.getValue())) {
                return;
            }
            final int l_FoundSlot = this.findArmorSlot(p_Slot);
            if (l_FoundSlot != -1) {
                this.timer.reset();
                if (l_FoundSlot <= 4) {
                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_FoundSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, p_ArmorSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                }
                else {
                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_FoundSlot, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                }
            }
        }
    }
    
    private int findArmorSlot(final EntityEquipmentSlot type) {
        int slot = -1;
        float damage = 0.0f;
        for (int i = 0; i < this.mc.player.inventoryContainer.getInventory().size(); ++i) {
            if (i != 0 && i != 5 && i != 6 && i != 7) {
                if (i != 8) {
                    final ItemStack s = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i);
                    if (s != null && s.getItem() != Items.AIR) {
                        if (s.getItem() instanceof ItemArmor) {
                            final ItemArmor armor = (ItemArmor)s.getItem();
                            if (armor.armorType == type) {
                                final float currentDamage = (float)(armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, s));
                                final boolean cursed = this.curse.getValue() && EnchantmentHelper.hasBindingCurse(s);
                                if (currentDamage > damage && !cursed) {
                                    damage = currentDamage;
                                    slot = i;
                                }
                            }
                        }
                        else if (type == EntityEquipmentSlot.CHEST && this.PreferElytra.getValue() && s.getItem() instanceof ItemElytra && ArmorComponent.GetPctFromStack(s) > 3.0f) {
                            return i;
                        }
                    }
                }
            }
        }
        return slot;
    }
}
