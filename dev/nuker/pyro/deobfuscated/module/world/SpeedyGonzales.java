//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerDamageBlock;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerClickBlock;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerResetBlockRemoving;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class SpeedyGonzales extends Module
{
    public final Value<String> mode;
    public final Value<Float> Speed;
    public final Value<Boolean> reset;
    public final Value<Boolean> doubleBreak;
    public final Value<Boolean> FastFall;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerResetBlockRemoving> ResetBlock;
    @EventHandler
    private Listener<EventPlayerClickBlock> ClickBlock;
    @EventHandler
    private Listener<EventPlayerDamageBlock> OnDamageBlock;
    
    public SpeedyGonzales() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "SpeedyGonzales"
        //     3: iconst_1       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             "Speedy Gonzales"
        //    11: aastore        
        //    12: ldc             "Allows you to break blocks faster"
        //    14: ldc             "NONE"
        //    16: ldc             2415456
        //    18: getstatic       com/Pyro/pyro/module/Module$ModuleType.WORLD:Lcom/Pyro/pyro/module/Module$ModuleType;
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
        //    45: ldc             "The speed-mine mode to use."
        //    47: ldc             "Instant"
        //    49: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    52: putfield        com/Pyro/pyro/module/world/SpeedyGonzales.mode:Lcom/Pyro/pyro/module/Value;
        //    55: aload_0         /* this */
        //    56: new             Lcom/Pyro/pyro/module/Value;
        //    59: dup            
        //    60: ldc             "Speed"
        //    62: iconst_1       
        //    63: anewarray       Ljava/lang/String;
        //    66: dup            
        //    67: iconst_0       
        //    68: ldc             "S"
        //    70: aastore        
        //    71: ldc             "Speed for Bypass Mode"
        //    73: fconst_1       
        //    74: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    77: fconst_0       
        //    78: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    81: fconst_1       
        //    82: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    85: ldc             0.1
        //    87: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    90: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //    93: putfield        com/Pyro/pyro/module/world/SpeedyGonzales.Speed:Lcom/Pyro/pyro/module/Value;
        //    96: aload_0         /* this */
        //    97: new             Lcom/Pyro/pyro/module/Value;
        //   100: dup            
        //   101: ldc             "Reset"
        //   103: iconst_1       
        //   104: anewarray       Ljava/lang/String;
        //   107: dup            
        //   108: iconst_0       
        //   109: ldc             "Res"
        //   111: aastore        
        //   112: ldc             "Stops current block destroy damage from resetting if enabled."
        //   114: iconst_1       
        //   115: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   118: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   121: putfield        com/Pyro/pyro/module/world/SpeedyGonzales.reset:Lcom/Pyro/pyro/module/Value;
        //   124: aload_0         /* this */
        //   125: new             Lcom/Pyro/pyro/module/Value;
        //   128: dup            
        //   129: ldc             "DoubleBreak"
        //   131: iconst_3       
        //   132: anewarray       Ljava/lang/String;
        //   135: dup            
        //   136: iconst_0       
        //   137: ldc             "DoubleBreak"
        //   139: aastore        
        //   140: dup            
        //   141: iconst_1       
        //   142: ldc             "Double"
        //   144: aastore        
        //   145: dup            
        //   146: iconst_2       
        //   147: ldc             "DB"
        //   149: aastore        
        //   150: ldc             "Mining a block will also mine the block above it, if enabled."
        //   152: iconst_0       
        //   153: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   156: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   159: putfield        com/Pyro/pyro/module/world/SpeedyGonzales.doubleBreak:Lcom/Pyro/pyro/module/Value;
        //   162: aload_0         /* this */
        //   163: new             Lcom/Pyro/pyro/module/Value;
        //   166: dup            
        //   167: ldc             "FastFall"
        //   169: iconst_1       
        //   170: anewarray       Ljava/lang/String;
        //   173: dup            
        //   174: iconst_0       
        //   175: ldc             "FF"
        //   177: aastore        
        //   178: ldc             "Makes it so you fall faster."
        //   180: iconst_0       
        //   181: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   184: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   187: putfield        com/Pyro/pyro/module/world/SpeedyGonzales.FastFall:Lcom/Pyro/pyro/module/Value;
        //   190: aload_0         /* this */
        //   191: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   194: dup            
        //   195: aload_0         /* this */
        //   196: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/world/SpeedyGonzales;)Ljava/util/function/Consumer;
        //   201: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   204: putfield        com/Pyro/pyro/module/world/SpeedyGonzales.OnPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   207: aload_0         /* this */
        //   208: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   211: dup            
        //   212: aload_0         /* this */
        //   213: invokedynamic   BootstrapMethod #1, accept:(Lcom/Pyro/pyro/module/world/SpeedyGonzales;)Ljava/util/function/Consumer;
        //   218: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   221: putfield        com/Pyro/pyro/module/world/SpeedyGonzales.ResetBlock:Lcom/Pyro/pyro/events/bus/Listener;
        //   224: aload_0         /* this */
        //   225: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   228: dup            
        //   229: aload_0         /* this */
        //   230: invokedynamic   BootstrapMethod #2, accept:(Lcom/Pyro/pyro/module/world/SpeedyGonzales;)Ljava/util/function/Consumer;
        //   235: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   238: putfield        com/Pyro/pyro/module/world/SpeedyGonzales.ClickBlock:Lcom/Pyro/pyro/events/bus/Listener;
        //   241: aload_0         /* this */
        //   242: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   245: dup            
        //   246: aload_0         /* this */
        //   247: invokedynamic   BootstrapMethod #3, accept:(Lcom/Pyro/pyro/module/world/SpeedyGonzales;)Ljava/util/function/Consumer;
        //   252: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   255: putfield        com/Pyro/pyro/module/world/SpeedyGonzales.OnDamageBlock:Lcom/Pyro/pyro/events/bus/Listener;
        //   258: aload_0         /* this */
        //   259: aload_0         /* this */
        //   260: invokevirtual   com/Pyro/pyro/module/world/SpeedyGonzales.getMetaData:()Ljava/lang/String;
        //   263: invokevirtual   com/Pyro/pyro/module/world/SpeedyGonzales.setMetaData:(Ljava/lang/String;)V
        //   266: aload_0         /* this */
        //   267: getfield        com/Pyro/pyro/module/world/SpeedyGonzales.mode:Lcom/Pyro/pyro/module/Value;
        //   270: ldc             "Packet"
        //   272: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   275: aload_0         /* this */
        //   276: getfield        com/Pyro/pyro/module/world/SpeedyGonzales.mode:Lcom/Pyro/pyro/module/Value;
        //   279: ldc             "Damage"
        //   281: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   284: aload_0         /* this */
        //   285: getfield        com/Pyro/pyro/module/world/SpeedyGonzales.mode:Lcom/Pyro/pyro/module/Value;
        //   288: ldc             "Instant"
        //   290: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   293: aload_0         /* this */
        //   294: getfield        com/Pyro/pyro/module/world/SpeedyGonzales.mode:Lcom/Pyro/pyro/module/Value;
        //   297: ldc             "Bypass"
        //   299: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   302: aload_0         /* this */
        //   303: getfield        com/Pyro/pyro/module/world/SpeedyGonzales.mode:Lcom/Pyro/pyro/module/Value;
        //   306: ldc             "NoBreakDelay"
        //   308: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   311: return         
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
        return this.mode.getValue();
    }
    
    private boolean canBreak(final BlockPos pos) {
        final IBlockState blockState = this.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)Minecraft.getMinecraft().world, pos) != -1.0f;
    }
    
    private enum Mode
    {
        Packet, 
        Damage, 
        Instant, 
        Bypass, 
        NoBreakDelay;
    }
}
