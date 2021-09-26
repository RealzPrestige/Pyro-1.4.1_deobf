//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.init.Items;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class ChestStealer extends Module
{
    public Value<String> Mode;
    public Value<Float> Delay;
    public Value<Boolean> DepositShulkers;
    public Value<Boolean> EntityChests;
    public Value<Boolean> Shulkers;
    private Timer timer;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public ChestStealer() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "ChestStealer"
        //     3: iconst_1       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             "Chest"
        //    11: aastore        
        //    12: ldc             "Steals the contents from chests"
        //    14: ldc             "NONE"
        //    16: ldc             14376484
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
        //    40: ldc             "The mode for chest stealer"
        //    42: ldc             "Steal"
        //    44: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    47: putfield        com/Pyro/pyro/module/misc/ChestStealer.Mode:Lcom/Pyro/pyro/module/Value;
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
        //    66: ldc             "Delay for each tick"
        //    68: fconst_1       
        //    69: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    72: fconst_0       
        //    73: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    76: ldc             10.0
        //    78: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    81: fconst_1       
        //    82: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    85: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //    88: putfield        com/Pyro/pyro/module/misc/ChestStealer.Delay:Lcom/Pyro/pyro/module/Value;
        //    91: aload_0         /* this */
        //    92: new             Lcom/Pyro/pyro/module/Value;
        //    95: dup            
        //    96: ldc             "DepositShulkers"
        //    98: iconst_1       
        //    99: anewarray       Ljava/lang/String;
        //   102: dup            
        //   103: iconst_0       
        //   104: ldc             "S"
        //   106: aastore        
        //   107: ldc             "Only deposit shulkers"
        //   109: iconst_0       
        //   110: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   113: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   116: putfield        com/Pyro/pyro/module/misc/ChestStealer.DepositShulkers:Lcom/Pyro/pyro/module/Value;
        //   119: aload_0         /* this */
        //   120: new             Lcom/Pyro/pyro/module/Value;
        //   123: dup            
        //   124: ldc             "EntityChests"
        //   126: iconst_1       
        //   127: anewarray       Ljava/lang/String;
        //   130: dup            
        //   131: iconst_0       
        //   132: ldc             "EC"
        //   134: aastore        
        //   135: ldc             "Take from entity chests"
        //   137: iconst_0       
        //   138: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   141: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   144: putfield        com/Pyro/pyro/module/misc/ChestStealer.EntityChests:Lcom/Pyro/pyro/module/Value;
        //   147: aload_0         /* this */
        //   148: new             Lcom/Pyro/pyro/module/Value;
        //   151: dup            
        //   152: ldc             "Shulkers"
        //   154: iconst_1       
        //   155: anewarray       Ljava/lang/String;
        //   158: dup            
        //   159: iconst_0       
        //   160: ldc             "EC"
        //   162: aastore        
        //   163: ldc             "Take from shulkers"
        //   165: iconst_0       
        //   166: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   169: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   172: putfield        com/Pyro/pyro/module/misc/ChestStealer.Shulkers:Lcom/Pyro/pyro/module/Value;
        //   175: aload_0         /* this */
        //   176: new             Lcom/Pyro/pyro/util/Timer;
        //   179: dup            
        //   180: invokespecial   com/Pyro/pyro/util/Timer.<init>:()V
        //   183: putfield        com/Pyro/pyro/module/misc/ChestStealer.timer:Lcom/Pyro/pyro/util/Timer;
        //   186: aload_0         /* this */
        //   187: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   190: dup            
        //   191: aload_0         /* this */
        //   192: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/misc/ChestStealer;)Ljava/util/function/Consumer;
        //   197: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   200: putfield        com/Pyro/pyro/module/misc/ChestStealer.OnPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   203: aload_0         /* this */
        //   204: aload_0         /* this */
        //   205: invokevirtual   com/Pyro/pyro/module/misc/ChestStealer.getMetaData:()Ljava/lang/String;
        //   208: invokevirtual   com/Pyro/pyro/module/misc/ChestStealer.setMetaData:(Ljava/lang/String;)V
        //   211: aload_0         /* this */
        //   212: getfield        com/Pyro/pyro/module/misc/ChestStealer.Mode:Lcom/Pyro/pyro/module/Value;
        //   215: ldc             "Steal"
        //   217: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   220: aload_0         /* this */
        //   221: getfield        com/Pyro/pyro/module/misc/ChestStealer.Mode:Lcom/Pyro/pyro/module/Value;
        //   224: ldc             "Store"
        //   226: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   229: aload_0         /* this */
        //   230: getfield        com/Pyro/pyro/module/misc/ChestStealer.Mode:Lcom/Pyro/pyro/module/Value;
        //   233: ldc             "Drop"
        //   235: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   238: aload_0         /* this */
        //   239: getfield        com/Pyro/pyro/module/misc/ChestStealer.Mode:Lcom/Pyro/pyro/module/Value;
        //   242: ldc             "TakeEntityPut"
        //   244: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   247: return         
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
        return this.Mode.getValue().toString();
    }
    
    private void HandleStoring(final int p_WindowId, final int p_Slot) {
        if (this.Mode.getValue().equals("Store") || this.Mode.getValue().equals("TakeEntityPut")) {
            for (int l_Y = 9; l_Y < this.mc.player.inventoryContainer.inventorySlots.size() - 1; ++l_Y) {
                final ItemStack l_InvStack = this.mc.player.inventoryContainer.getSlot(l_Y).getStack();
                if (!l_InvStack.isEmpty()) {
                    if (l_InvStack.getItem() != Items.AIR) {
                        if (!this.DepositShulkers.getValue() || l_InvStack.getItem() instanceof ItemShulkerBox) {
                            this.mc.playerController.windowClick(p_WindowId, l_Y + p_Slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                            return;
                        }
                    }
                }
            }
        }
    }
}
