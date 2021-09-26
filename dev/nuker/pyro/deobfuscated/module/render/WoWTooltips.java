//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemArmor;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderTooltip;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import java.text.DecimalFormat;
import dev.nuker.pyro.deobfuscated.module.Module;

public class WoWTooltips extends Module
{
    private DecimalFormat _formatter;
    private float _lastWidth;
    private float _lastHeight;
    @EventHandler
    private Listener<EventRenderTooltip> OnRenderTooltip;
    
    public WoWTooltips() {
        super("WoWTooltips", new String[] { "" }, "Displays items like in WoW", "NONE", 3381177, ModuleType.RENDER);
        this._formatter = new DecimalFormat("#");
        this._lastWidth = 0.0f;
        this._lastHeight = 0.0f;
        int x;
        int y;
        String title;
        float prevWidth;
        int newY;
        String itemNameDesc;
        String typeString;
        String rightTypeString;
        ItemArmor armor;
        int prevY;
        ItemSword sword;
        int prevY2;
        String speedString;
        final Iterator<Enchantment> iterator;
        Enchantment enchant;
        String name;
        int color;
        float armorPct;
        String durability;
        this.OnRenderTooltip = new Listener<EventRenderTooltip>(event -> {
            if (!event.isCancelled()) {
                if (!event.getItemStack().isEmpty()) {
                    event.cancel();
                    x = event.getX();
                    y = event.getY();
                    GlStateManager.translate((float)(x + 10), (float)(y - 5), 0.0f);
                    GlStateManager.disableLighting();
                    GlStateManager.disableDepth();
                    title = event.getItemStack().getDisplayName();
                    RenderUtil.drawRect(0.0f, -2.0f, this._lastWidth, this._lastHeight, -519565792);
                    prevWidth = this._lastWidth;
                    this._lastWidth = 0.0f;
                    newY = this.renderString(title, 3, 3, this.getColorFromItem(event.getItemStack()));
                    itemNameDesc = this.getItemNameDescriptionString(event.getItemStack());
                    if (itemNameDesc != null) {
                        newY = this.renderString(itemNameDesc, 3, newY, 16711680);
                    }
                    typeString = null;
                    rightTypeString = null;
                    if (event.getItemStack().getItem() instanceof ItemArmor) {
                        armor = (ItemArmor)event.getItemStack().getItem();
                        switch (armor.getEquipmentSlot()) {
                            case CHEST: {
                                typeString = "Chest";
                                break;
                            }
                            case FEET: {
                                typeString = "Feet";
                                break;
                            }
                            case HEAD: {
                                typeString = "Head";
                                break;
                            }
                            case LEGS: {
                                typeString = "Leggings";
                                break;
                            }
                        }
                        switch (armor.getArmorMaterial()) {
                            case CHAIN: {
                                rightTypeString = "Chain";
                                break;
                            }
                            case DIAMOND: {
                                rightTypeString = "Diamond";
                                break;
                            }
                            case GOLD: {
                                rightTypeString = "Gold";
                                break;
                            }
                            case IRON: {
                                rightTypeString = "Iron";
                                break;
                            }
                            case LEATHER: {
                                rightTypeString = "Leather";
                                break;
                            }
                        }
                    }
                    if (event.getItemStack().getItem() instanceof ItemElytra) {
                        typeString = "Chest";
                    }
                    if (event.getItemStack().getItem() instanceof ItemSword) {
                        typeString = "Mainhand";
                        rightTypeString = "Sword";
                    }
                    if (typeString != null) {
                        prevY = newY;
                        newY = this.renderString(typeString, 3, newY, -1);
                        if (rightTypeString != null) {
                            this.renderString(rightTypeString, (int)(prevWidth - RenderUtil.getStringWidth(rightTypeString) - 3.0f), prevY, -1);
                            this._lastWidth = Math.max(48.0f, prevWidth);
                        }
                    }
                    if (event.getItemStack().getItem() instanceof ItemSword) {
                        sword = (ItemSword)event.getItemStack().getItem();
                        prevY2 = newY;
                        newY = this.renderString(sword.getAttackDamage() + " - " + sword.getAttackDamage() + " Damage", 3, newY, -1);
                        speedString = "Speed 0.625";
                        this.renderString(speedString, (int)(this._lastWidth - RenderUtil.getStringWidth(speedString) - 3.0f), prevY2, -1);
                    }
                    EnchantmentHelper.getEnchantments(event.getItemStack()).keySet().iterator();
                    while (iterator.hasNext()) {
                        enchant = iterator.next();
                        name = "+" + EnchantmentHelper.getEnchantmentLevel(enchant, event.getItemStack()) + " " + I18n.translateToLocal(enchant.getName());
                        if (!name.contains("Vanish")) {
                            if (name.contains("Binding")) {
                                continue;
                            }
                            else {
                                color = -1;
                                if (name.contains("Mending") || name.contains("Unbreaking")) {
                                    color = 65280;
                                }
                                newY = this.renderString(name, 3, newY, color);
                            }
                        }
                    }
                    if (event.getItemStack().getMaxDamage() > 1) {
                        armorPct = (event.getItemStack().getMaxDamage() - event.getItemStack().getItemDamage()) / (float)event.getItemStack().getMaxDamage() * 100.0f;
                        durability = String.format("Durability %s %s / %s", this._formatter.format(armorPct) + "%", event.getItemStack().getMaxDamage() - event.getItemStack().getItemDamage(), event.getItemStack().getMaxDamage());
                        newY = this.renderString(durability, 3, newY, -1);
                    }
                    GlStateManager.enableDepth();
                    this.mc.getRenderItem().zLevel = 150.0f;
                    RenderHelper.enableGUIStandardItemLighting();
                    RenderHelper.disableStandardItemLighting();
                    this.mc.getRenderItem().zLevel = 0.0f;
                    GlStateManager.enableLighting();
                    GlStateManager.translate((float)(-(x + 10)), (float)(-(y - 5)), 0.0f);
                    this._lastHeight = (float)(newY + 1);
                }
            }
        });
    }
    
    private int renderString(final String string, final int x, final int y, final int color) {
        RenderUtil.drawStringWithShadow(string, (float)x, (float)y, color);
        this._lastWidth = Math.max(this._lastWidth, RenderUtil.getStringWidth(string) + x + 3.0f);
        return y + 9;
    }
    
    private int getColorFromItem(final ItemStack stack) {
        if (stack.getItem() instanceof ItemArmor) {
            final ItemArmor armor = (ItemArmor)stack.getItem();
            switch (armor.getArmorMaterial()) {
                case CHAIN: {
                    return 28893;
                }
                case DIAMOND: {
                    return EnchantmentHelper.getEnchantments(stack).keySet().isEmpty() ? 2031360 : 10696174;
                }
                case GOLD:
                case IRON: {
                    return 2031360;
                }
                case LEATHER: {
                    return 10329501;
                }
            }
        }
        else if (stack.getItem().equals(Items.GOLDEN_APPLE)) {
            if (stack.hasEffect()) {
                return 10696174;
            }
            return 52735;
        }
        else if (stack.getItem() instanceof ItemSword) {
            final ItemSword sword = (ItemSword)stack.getItem();
            final String material = sword.getToolMaterialName();
            if (material.equals("DIAMOND")) {
                return 10696174;
            }
            if (material.equals("CHAIN")) {
                return 28893;
            }
            if (material.equals("GOLD")) {
                return 2031360;
            }
            if (material.equals("IRON")) {
                return 2031360;
            }
            if (material.equals("LEATHER")) {
                return 10329501;
            }
            return -1;
        }
        else {
            if (stack.getItem().equals(Items.TOTEM_OF_UNDYING)) {
                return 16744448;
            }
            if (stack.getItem().equals(Items.CHORUS_FRUIT)) {
                return 28893;
            }
            if (stack.getItem().equals(Items.ENDER_PEARL)) {
                return 28893;
            }
            if (stack.getItem().equals(Items.END_CRYSTAL)) {
                return 10696174;
            }
            if (stack.getItem().equals(Items.EXPERIENCE_BOTTLE)) {
                return 2031360;
            }
            if (stack.getItem().equals(Items.POTIONITEM)) {
                return 2031360;
            }
            if (Item.getIdFromItem(stack.getItem()) == 130) {
                return 10696174;
            }
            if (stack.getItem() instanceof ItemShulkerBox) {
                return 10696174;
            }
        }
        return -1;
    }
    
    private final String getItemNameDescriptionString(final ItemStack stack) {
        String result = "";
        for (final Enchantment enchant : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchant == null) {
                continue;
            }
            final String name = enchant.getTranslatedName(EnchantmentHelper.getEnchantmentLevel(enchant, stack));
            if (name.contains("Vanish")) {
                result += "Vanishing ";
            }
            else {
                if (!name.contains("Binding")) {
                    continue;
                }
                result += "Binding ";
            }
        }
        if (stack.getItem().equals(Items.GOLDEN_APPLE) && stack.hasEffect()) {
            return "God";
        }
        return result.isEmpty() ? null : result;
    }
}
