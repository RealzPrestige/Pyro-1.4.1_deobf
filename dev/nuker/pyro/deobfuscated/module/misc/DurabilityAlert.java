//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.inventory.EntityEquipmentSlot;
import java.util.Iterator;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.nuker.pyro.deobfuscated.managers.NotificationManager;
import dev.nuker.pyro.deobfuscated.gui.hud.items.ArmorComponent;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class DurabilityAlert extends Module
{
    public final Value<Integer> Durability;
    public final Value<Boolean> ChatMessages;
    public final Value<Boolean> Notifications;
    private boolean[] stillLow;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public DurabilityAlert() {
        super("DurabilityAlert", new String[] { "DurabilityAlert", "DurabilityNotifier", "DurabilityNotifications" }, "Sends a notification when your armor Durability is low.", "NONE", -1, ModuleType.MISC);
        this.Durability = new Value<Integer>("Durability", new String[] { "Durability", "Dura" }, "Durability at which you will receive a notification.", 20, 0, 100, 1);
        this.ChatMessages = new Value<Boolean>("ChatMessages", new String[] { "ChatMessages" }, "Send Chat Messages.", true);
        this.Notifications = new Value<Boolean>("Notifications", new String[] { "Notifs", "Notifications" }, "Displays a notification.", true);
        this.stillLow = new boolean[] { false, false, false, false };
        final Iterator<ItemStack> armor;
        ItemStack armorItem;
        ItemArmor itemArmor;
        int index;
        float currDurability;
        boolean alreadyNotified;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            armor = this.mc.player.getArmorInventoryList().iterator();
            while (armor.hasNext()) {
                armorItem = armor.next();
                if (!armorItem.isEmpty()) {
                    if (!(armorItem.getItem() instanceof ItemArmor)) {
                        continue;
                    }
                    else {
                        itemArmor = (ItemArmor)armorItem.getItem();
                        index = this.getIndexFromArmorSlot(itemArmor.getEquipmentSlot());
                        if (index == -1) {
                            continue;
                        }
                        else {
                            currDurability = ArmorComponent.GetPctFromStack(armorItem);
                            alreadyNotified = this.stillLow[index];
                            if (currDurability < this.Durability.getValue()) {
                                if (!alreadyNotified) {
                                    this.stillLow[index] = true;
                                    if (this.Notifications.getValue()) {
                                        NotificationManager.Get().AddNotification("Alert!", "Your " + this.slotToName(itemArmor.getEquipmentSlot()) + " is below " + this.Durability.getValue() + "% durability!");
                                    }
                                    if (this.ChatMessages.getValue()) {
                                        this.SendMessage(ChatFormatting.RED + "Your " + this.slotToName(itemArmor.getEquipmentSlot()) + " is below " + this.Durability.getValue() + "% durability!");
                                    }
                                    else {
                                        continue;
                                    }
                                }
                                else {
                                    continue;
                                }
                            }
                            else {
                                this.stillLow[index] = false;
                            }
                        }
                    }
                }
            }
        });
    }
    
    private int getIndexFromArmorSlot(final EntityEquipmentSlot slot) {
        switch (slot) {
            case HEAD: {
                return 0;
            }
            case CHEST: {
                return 1;
            }
            case LEGS: {
                return 2;
            }
            case FEET: {
                return 3;
            }
            default: {
                return -1;
            }
        }
    }
    
    private final String slotToName(final EntityEquipmentSlot slot) {
        switch (slot) {
            case HEAD: {
                return "helmet";
            }
            case CHEST: {
                return "chestplate";
            }
            case LEGS: {
                return "leggings";
            }
            case FEET: {
                return "boots";
            }
            default: {
                return "";
            }
        }
    }
}
