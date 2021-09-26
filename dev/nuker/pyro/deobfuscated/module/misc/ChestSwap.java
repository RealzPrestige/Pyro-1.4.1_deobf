//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.item.ItemElytra;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class ChestSwap extends Module
{
    public final Value<Boolean> PreferElytra;
    public final Value<Boolean> Curse;
    
    public ChestSwap() {
        super("ChestSwap", new String[] { "ElytraOneButton" }, "Will attempt to instantly swap your chestplate with an elytra or vice versa, depending on what is already equipped", "NONE", 2415572, ModuleType.MISC);
        this.PreferElytra = new Value<Boolean>("PreferElytra", new String[] { "Elytra" }, "Prefers elytra when this is toggled with no equipped item", true);
        this.Curse = new Value<Boolean>("Curse", new String[] { "Curses" }, "Prevents you from equipping armor with cursed enchantments.", false);
    }
    
    @Override
    public void onEnable() {
        if (this.mc.player == null) {
            return;
        }
        final ItemStack l_ChestSlot = this.mc.player.inventoryContainer.getSlot(6).getStack();
        if (l_ChestSlot.isEmpty()) {
            int l_Slot = this.FindChestItem(this.PreferElytra.getValue());
            if (!this.PreferElytra.getValue() && l_Slot == -1) {
                l_Slot = this.FindChestItem(true);
            }
            if (l_Slot != -1) {
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            }
            this.toggle();
            return;
        }
        int l_Slot = this.FindChestItem(l_ChestSlot.getItem() instanceof ItemArmor);
        if (l_Slot != -1) {
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        }
        this.toggle();
    }
    
    private int FindChestItem(final boolean p_Elytra) {
        int slot = -1;
        float damage = 0.0f;
        for (int i = 0; i < this.mc.player.inventoryContainer.getInventory().size(); ++i) {
            if (i != 0 && i != 5 && i != 6 && i != 7) {
                if (i != 8) {
                    final ItemStack s = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i);
                    if (s != null && s.getItem() != Items.AIR) {
                        if (s.getItem() instanceof ItemArmor) {
                            final ItemArmor armor = (ItemArmor)s.getItem();
                            if (armor.armorType == EntityEquipmentSlot.CHEST) {
                                final float currentDamage = (float)(armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, s));
                                final boolean cursed = this.Curse.getValue() && EnchantmentHelper.hasBindingCurse(s);
                                if (currentDamage > damage && !cursed) {
                                    damage = currentDamage;
                                    slot = i;
                                }
                            }
                        }
                        else if (p_Elytra && s.getItem() instanceof ItemElytra) {
                            return i;
                        }
                    }
                }
            }
        }
        return slot;
    }
}
