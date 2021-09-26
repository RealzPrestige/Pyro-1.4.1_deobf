//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class ItemUtil
{
    public static boolean Is32k(final ItemStack p_Stack) {
        if (p_Stack.getEnchantmentTagList() != null) {
            final NBTTagList tags = p_Stack.getEnchantmentTagList();
            for (int i = 0; i < tags.tagCount(); ++i) {
                final NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
                if (tagCompound != null && Enchantment.getEnchantmentByID((int)tagCompound.getByte("id")) != null) {
                    final Enchantment enchantment = Enchantment.getEnchantmentByID((int)tagCompound.getShort("id"));
                    final short lvl = tagCompound.getShort("lvl");
                    if (enchantment != null) {
                        if (!enchantment.isCurse()) {
                            if (lvl >= 1000) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
