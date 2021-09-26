//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import net.minecraft.item.Item;
import java.util.ArrayList;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class HotbarCache extends Module
{
    public final Value<String> Mode;
    public final Value<Float> Delay;
    private ArrayList<Item> Hotbar;
    private Timer timer;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public HotbarCache() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "HotbarCache"
        //     3: iconst_1       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             "HC"
        //    11: aastore        
        //    12: ldc             "Automatically refills your hotbar similar to how autototem works"
        //    14: ldc             "NONE"
        //    16: ldc             11740379
        //    18: getstatic       com/Pyro/pyro/module/Module$ModuleType.MISC:Lcom/Pyro/pyro/module/Module$ModuleType;
        //    21: invokespecial   com/Pyro/pyro/module/Module.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/Pyro/pyro/module/Module$ModuleType;)V
        //    24: aload_0         /* this */
        //    25: new             Lcom/Pyro/pyro/module/Value;
        //    28: dup            
        //    29: ldc             "Mode"
        //    31: iconst_1       
        //    32: anewarray       Ljava/lang/String;
        //    35: dup            
        //    36: iconst_0       
        //    37: ldc             "M"
        //    39: aastore        
        //    40: ldc             "The mode of refilling to use, Refill may cause desync"
        //    42: ldc             "Cache"
        //    44: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    47: putfield        com/Pyro/pyro/module/misc/HotbarCache.Mode:Lcom/Pyro/pyro/module/Value;
        //    50: aload_0         /* this */
        //    51: new             Lcom/Pyro/pyro/module/Value;
        //    54: dup            
        //    55: ldc             "Delay"
        //    57: iconst_1       
        //    58: anewarray       Ljava/lang/String;
        //    61: dup            
        //    62: iconst_0       
        //    63: ldc             "D"
        //    65: aastore        
        //    66: ldc             "Delay to use"
        //    68: fconst_1       
        //    69: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    72: fconst_0       
        //    73: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    76: ldc             10.0
        //    78: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    81: fconst_1       
        //    82: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    85: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //    88: putfield        com/Pyro/pyro/module/misc/HotbarCache.Delay:Lcom/Pyro/pyro/module/Value;
        //    91: aload_0         /* this */
        //    92: new             Ljava/util/ArrayList;
        //    95: dup            
        //    96: invokespecial   java/util/ArrayList.<init>:()V
        //    99: putfield        com/Pyro/pyro/module/misc/HotbarCache.Hotbar:Ljava/util/ArrayList;
        //   102: aload_0         /* this */
        //   103: new             Lcom/Pyro/pyro/util/Timer;
        //   106: dup            
        //   107: invokespecial   com/Pyro/pyro/util/Timer.<init>:()V
        //   110: putfield        com/Pyro/pyro/module/misc/HotbarCache.timer:Lcom/Pyro/pyro/util/Timer;
        //   113: aload_0         /* this */
        //   114: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   117: dup            
        //   118: aload_0         /* this */
        //   119: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/misc/HotbarCache;)Ljava/util/function/Consumer;
        //   124: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   127: putfield        com/Pyro/pyro/module/misc/HotbarCache.OnPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   130: aload_0         /* this */
        //   131: aload_0         /* this */
        //   132: invokevirtual   com/Pyro/pyro/module/misc/HotbarCache.getMetaData:()Ljava/lang/String;
        //   135: invokevirtual   com/Pyro/pyro/module/misc/HotbarCache.setMetaData:(Ljava/lang/String;)V
        //   138: aload_0         /* this */
        //   139: getfield        com/Pyro/pyro/module/misc/HotbarCache.Mode:Lcom/Pyro/pyro/module/Value;
        //   142: ldc             "Cache"
        //   144: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   147: aload_0         /* this */
        //   148: getfield        com/Pyro/pyro/module/misc/HotbarCache.Mode:Lcom/Pyro/pyro/module/Value;
        //   151: ldc             "Refill"
        //   153: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   156: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.Hotbar.clear();
        if (this.mc.player == null && this.Mode.getValue().equals("Cache")) {
            this.toggle();
            return;
        }
        if (this.Mode.getValue().equals("Cache")) {
            for (int l_I = 0; l_I < 9; ++l_I) {
                final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
                if (!l_Stack.isEmpty() && !this.Hotbar.contains(l_Stack.getItem())) {
                    this.Hotbar.add(l_Stack.getItem());
                }
                else {
                    this.Hotbar.add(Items.AIR);
                }
            }
        }
    }
    
    private boolean SwitchSlotIfNeed(final int p_Slot) {
        final Item l_Item = this.Hotbar.get(p_Slot);
        if (l_Item == Items.AIR) {
            return false;
        }
        if (!this.mc.player.inventory.getStackInSlot(p_Slot).isEmpty() && this.mc.player.inventory.getStackInSlot(p_Slot).getItem() == l_Item) {
            return false;
        }
        final int l_Slot = PlayerUtil.GetItemSlot(l_Item);
        if (l_Slot != -1 && l_Slot != 45) {
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, p_Slot + 36, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.updateController();
            return true;
        }
        return false;
    }
    
    private boolean RefillSlotIfNeed(final int p_Slot) {
        final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(p_Slot);
        if (l_Stack.isEmpty() || l_Stack.getItem() == Items.AIR) {
            return false;
        }
        if (!l_Stack.isStackable()) {
            return false;
        }
        if (l_Stack.getCount() >= l_Stack.getMaxStackSize()) {
            return false;
        }
        for (int l_I = 9; l_I < 36; ++l_I) {
            final ItemStack l_Item = this.mc.player.inventory.getStackInSlot(l_I);
            if (!l_Item.isEmpty()) {
                if (this.CanItemBeMergedWith(l_Stack, l_Item)) {
                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_I, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                    this.mc.playerController.updateController();
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean CanItemBeMergedWith(final ItemStack p_Source, final ItemStack p_Target) {
        return p_Source.getItem() == p_Target.getItem() && p_Source.getDisplayName().equals(p_Target.getDisplayName());
    }
}
