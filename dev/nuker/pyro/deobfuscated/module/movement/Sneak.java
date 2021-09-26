//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class Sneak extends Module
{
    public final Value<String> mode;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    
    public Sneak() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "Sneak"
        //     3: iconst_1       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             "Sneek"
        //    11: aastore        
        //    12: ldc             "Allows you to sneak at full speed"
        //    14: ldc             "NONE"
        //    16: ldc             14361747
        //    18: getstatic       com/Pyro/pyro/module/Module$ModuleType.MOVEMENT:Lcom/Pyro/pyro/module/Module$ModuleType;
        //    21: invokespecial   com/Pyro/pyro/module/Module.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/Pyro/pyro/module/Module$ModuleType;)V
        //    24: aload_0         /* this */
        //    25: new             Lcom/Pyro/pyro/module/Value;
        //    28: dup            
        //    29: ldc             "Mode"
        //    31: iconst_2       
        //    32: anewarray       Ljava/lang/String;
        //    35: dup            
        //    36: iconst_0       
        //    37: ldc             "Mode"
        //    39: aastore        
        //    40: dup            
        //    41: iconst_1       
        //    42: ldc             "M"
        //    44: aastore        
        //    45: ldc             "The sneak mode to use."
        //    47: ldc             "NCP"
        //    49: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    52: putfield        com/Pyro/pyro/module/movement/Sneak.mode:Lcom/Pyro/pyro/module/Value;
        //    55: aload_0         /* this */
        //    56: new             Lcom/Pyro/pyro/events/bus/Listener;
        //    59: dup            
        //    60: aload_0         /* this */
        //    61: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/movement/Sneak;)Ljava/util/function/Consumer;
        //    66: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //    69: putfield        com/Pyro/pyro/module/movement/Sneak.onPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //    72: aload_0         /* this */
        //    73: new             Lcom/Pyro/pyro/events/bus/Listener;
        //    76: dup            
        //    77: aload_0         /* this */
        //    78: invokedynamic   BootstrapMethod #1, accept:(Lcom/Pyro/pyro/module/movement/Sneak;)Ljava/util/function/Consumer;
        //    83: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //    86: putfield        com/Pyro/pyro/module/movement/Sneak.OnPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //    89: aload_0         /* this */
        //    90: new             Lcom/Pyro/pyro/events/bus/Listener;
        //    93: dup            
        //    94: aload_0         /* this */
        //    95: invokedynamic   BootstrapMethod #2, accept:(Lcom/Pyro/pyro/module/movement/Sneak;)Ljava/util/function/Consumer;
        //   100: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   103: putfield        com/Pyro/pyro/module/movement/Sneak.onClientPacket:Lcom/Pyro/pyro/events/bus/Listener;
        //   106: aload_0         /* this */
        //   107: aload_0         /* this */
        //   108: invokevirtual   com/Pyro/pyro/module/movement/Sneak.getMetaData:()Ljava/lang/String;
        //   111: invokevirtual   com/Pyro/pyro/module/movement/Sneak.setMetaData:(Ljava/lang/String;)V
        //   114: aload_0         /* this */
        //   115: getfield        com/Pyro/pyro/module/movement/Sneak.mode:Lcom/Pyro/pyro/module/Value;
        //   118: ldc             "Vanilla"
        //   120: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   123: aload_0         /* this */
        //   124: getfield        com/Pyro/pyro/module/movement/Sneak.mode:Lcom/Pyro/pyro/module/Value;
        //   127: ldc             "NCP"
        //   129: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   132: aload_0         /* this */
        //   133: getfield        com/Pyro/pyro/module/movement/Sneak.mode:Lcom/Pyro/pyro/module/Value;
        //   136: ldc             "Always"
        //   138: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   141: return         
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
        if (this.mc.world != null && !this.mc.player.isSneaking()) {
            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    @Override
    public String getMetaData() {
        return this.mode.getValue();
    }
    
    private boolean isMoving() {
        return GameSettings.isKeyDown(this.mc.gameSettings.keyBindForward) || GameSettings.isKeyDown(this.mc.gameSettings.keyBindLeft) || GameSettings.isKeyDown(this.mc.gameSettings.keyBindRight) || GameSettings.isKeyDown(this.mc.gameSettings.keyBindBack);
    }
}
