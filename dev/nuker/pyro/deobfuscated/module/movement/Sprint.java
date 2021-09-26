//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class Sprint extends Module
{
    public final Value<String> Mode;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdates;
    
    public Sprint() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "Sprint"
        //     3: iconst_2       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             "AutoSprint"
        //    11: aastore        
        //    12: dup            
        //    13: iconst_1       
        //    14: ldc             "Spr"
        //    16: aastore        
        //    17: ldc             "Automatically sprints for you"
        //    19: ldc             "NONE"
        //    21: ldc             9483402
        //    23: getstatic       com/Pyro/pyro/module/Module$ModuleType.MOVEMENT:Lcom/Pyro/pyro/module/Module$ModuleType;
        //    26: invokespecial   com/Pyro/pyro/module/Module.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/Pyro/pyro/module/Module$ModuleType;)V
        //    29: aload_0         /* this */
        //    30: new             Lcom/Pyro/pyro/module/Value;
        //    33: dup            
        //    34: ldc             "Mode"
        //    36: iconst_2       
        //    37: anewarray       Ljava/lang/String;
        //    40: dup            
        //    41: iconst_0       
        //    42: ldc             "Mode"
        //    44: aastore        
        //    45: dup            
        //    46: iconst_1       
        //    47: ldc             "M"
        //    49: aastore        
        //    50: ldc             "The sprint mode to use."
        //    52: ldc             "Rage"
        //    54: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    57: putfield        com/Pyro/pyro/module/movement/Sprint.Mode:Lcom/Pyro/pyro/module/Value;
        //    60: aload_0         /* this */
        //    61: new             Lcom/Pyro/pyro/events/bus/Listener;
        //    64: dup            
        //    65: aload_0         /* this */
        //    66: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/movement/Sprint;)Ljava/util/function/Consumer;
        //    71: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //    74: putfield        com/Pyro/pyro/module/movement/Sprint.onPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //    77: aload_0         /* this */
        //    78: new             Lcom/Pyro/pyro/events/bus/Listener;
        //    81: dup            
        //    82: aload_0         /* this */
        //    83: invokedynamic   BootstrapMethod #1, accept:(Lcom/Pyro/pyro/module/movement/Sprint;)Ljava/util/function/Consumer;
        //    88: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //    91: putfield        com/Pyro/pyro/module/movement/Sprint.onMotionUpdates:Lcom/Pyro/pyro/events/bus/Listener;
        //    94: aload_0         /* this */
        //    95: aload_0         /* this */
        //    96: invokevirtual   com/Pyro/pyro/module/movement/Sprint.getMetaData:()Ljava/lang/String;
        //    99: invokevirtual   com/Pyro/pyro/module/movement/Sprint.setMetaData:(Ljava/lang/String;)V
        //   102: aload_0         /* this */
        //   103: getfield        com/Pyro/pyro/module/movement/Sprint.Mode:Lcom/Pyro/pyro/module/Value;
        //   106: ldc             "Rage"
        //   108: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   111: aload_0         /* this */
        //   112: getfield        com/Pyro/pyro/module/movement/Sprint.Mode:Lcom/Pyro/pyro/module/Value;
        //   115: ldc             "Legit"
        //   117: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   120: return         
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
    public void onDisable() {
        super.onDisable();
        if (this.mc.world != null) {
            this.mc.player.setSprinting(false);
        }
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
}
