// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Hole;
import java.util.List;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class HoleESP extends Module
{
    public final Value<String> HoleMode;
    public final Value<Integer> Radius;
    public final Value<Boolean> IgnoreOwnHole;
    public final Value<Float> ObsidianRed;
    public final Value<Float> ObsidianGreen;
    public final Value<Float> ObsidianBlue;
    public final Value<Float> ObsidianAlpha;
    public final Value<Float> BedrockRed;
    public final Value<Float> BedrockGreen;
    public final Value<Float> BedrockBlue;
    public final Value<Float> BedrockAlpha;
    public final List<Hole> holes;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public HoleESP() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "HoleESP"
        //     3: iconst_1       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             ""
        //    11: aastore        
        //    12: ldc             "Highlights holes for crystal pvp"
        //    14: ldc             "NONE"
        //    16: iconst_m1      
        //    17: getstatic       com/Pyro/pyro/module/Module$ModuleType.RENDER:Lcom/Pyro/pyro/module/Module$ModuleType;
        //    20: invokespecial   com/Pyro/pyro/module/Module.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/Pyro/pyro/module/Module$ModuleType;)V
        //    23: aload_0         /* this */
        //    24: new             Lcom/Pyro/pyro/module/Value;
        //    27: dup            
        //    28: ldc             "Mode"
        //    30: iconst_1       
        //    31: anewarray       Ljava/lang/String;
        //    34: dup            
        //    35: iconst_0       
        //    36: ldc             "HM"
        //    38: aastore        
        //    39: ldc             "Mode for rendering holes"
        //    41: ldc             "FlatOutline"
        //    43: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    46: putfield        com/Pyro/pyro/module/render/HoleESP.HoleMode:Lcom/Pyro/pyro/module/Value;
        //    49: aload_0         /* this */
        //    50: new             Lcom/Pyro/pyro/module/Value;
        //    53: dup            
        //    54: ldc             "Radius"
        //    56: iconst_3       
        //    57: anewarray       Ljava/lang/String;
        //    60: dup            
        //    61: iconst_0       
        //    62: ldc             "Radius"
        //    64: aastore        
        //    65: dup            
        //    66: iconst_1       
        //    67: ldc             "Range"
        //    69: aastore        
        //    70: dup            
        //    71: iconst_2       
        //    72: ldc             "Distance"
        //    74: aastore        
        //    75: ldc             "Radius in blocks to scan for holes."
        //    77: bipush          8
        //    79: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    82: iconst_0       
        //    83: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    86: bipush          32
        //    88: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    91: iconst_1       
        //    92: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    95: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //    98: putfield        com/Pyro/pyro/module/render/HoleESP.Radius:Lcom/Pyro/pyro/module/Value;
        //   101: aload_0         /* this */
        //   102: new             Lcom/Pyro/pyro/module/Value;
        //   105: dup            
        //   106: ldc             "IgnoreOwnHole"
        //   108: iconst_1       
        //   109: anewarray       Ljava/lang/String;
        //   112: dup            
        //   113: iconst_0       
        //   114: ldc             "NoSelfHole"
        //   116: aastore        
        //   117: ldc             "Doesn't render the hole you're standing in"
        //   119: iconst_0       
        //   120: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   123: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   126: putfield        com/Pyro/pyro/module/render/HoleESP.IgnoreOwnHole:Lcom/Pyro/pyro/module/Value;
        //   129: aload_0         /* this */
        //   130: new             Lcom/Pyro/pyro/module/Value;
        //   133: dup            
        //   134: ldc             "ObsidianRed"
        //   136: iconst_1       
        //   137: anewarray       Ljava/lang/String;
        //   140: dup            
        //   141: iconst_0       
        //   142: ldc             "oRed"
        //   144: aastore        
        //   145: ldc             "Red for rendering"
        //   147: fconst_0       
        //   148: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   151: fconst_0       
        //   152: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   155: fconst_1       
        //   156: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   159: ldc             0.1
        //   161: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   164: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   167: putfield        com/Pyro/pyro/module/render/HoleESP.ObsidianRed:Lcom/Pyro/pyro/module/Value;
        //   170: aload_0         /* this */
        //   171: new             Lcom/Pyro/pyro/module/Value;
        //   174: dup            
        //   175: ldc             "ObsidianGreen"
        //   177: iconst_1       
        //   178: anewarray       Ljava/lang/String;
        //   181: dup            
        //   182: iconst_0       
        //   183: ldc             "oGreen"
        //   185: aastore        
        //   186: ldc             "Green for rendering"
        //   188: fconst_1       
        //   189: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   192: fconst_0       
        //   193: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   196: fconst_1       
        //   197: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   200: ldc             0.1
        //   202: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   205: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   208: putfield        com/Pyro/pyro/module/render/HoleESP.ObsidianGreen:Lcom/Pyro/pyro/module/Value;
        //   211: aload_0         /* this */
        //   212: new             Lcom/Pyro/pyro/module/Value;
        //   215: dup            
        //   216: ldc             "ObsidianBlue"
        //   218: iconst_1       
        //   219: anewarray       Ljava/lang/String;
        //   222: dup            
        //   223: iconst_0       
        //   224: ldc             "oBlue"
        //   226: aastore        
        //   227: ldc             "Blue for rendering"
        //   229: fconst_0       
        //   230: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   233: fconst_0       
        //   234: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   237: fconst_1       
        //   238: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   241: ldc             0.1
        //   243: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   246: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   249: putfield        com/Pyro/pyro/module/render/HoleESP.ObsidianBlue:Lcom/Pyro/pyro/module/Value;
        //   252: aload_0         /* this */
        //   253: new             Lcom/Pyro/pyro/module/Value;
        //   256: dup            
        //   257: ldc             "ObsidianAlpha"
        //   259: iconst_1       
        //   260: anewarray       Ljava/lang/String;
        //   263: dup            
        //   264: iconst_0       
        //   265: ldc             "oAlpha"
        //   267: aastore        
        //   268: ldc             "Alpha for rendering"
        //   270: ldc             0.5
        //   272: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   275: fconst_0       
        //   276: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   279: fconst_1       
        //   280: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   283: ldc             0.1
        //   285: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   288: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   291: putfield        com/Pyro/pyro/module/render/HoleESP.ObsidianAlpha:Lcom/Pyro/pyro/module/Value;
        //   294: aload_0         /* this */
        //   295: new             Lcom/Pyro/pyro/module/Value;
        //   298: dup            
        //   299: ldc             "BedrockRed"
        //   301: iconst_1       
        //   302: anewarray       Ljava/lang/String;
        //   305: dup            
        //   306: iconst_0       
        //   307: ldc             "bRed"
        //   309: aastore        
        //   310: ldc             "Red for rendering"
        //   312: fconst_0       
        //   313: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   316: fconst_0       
        //   317: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   320: fconst_1       
        //   321: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   324: ldc             0.1
        //   326: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   329: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   332: putfield        com/Pyro/pyro/module/render/HoleESP.BedrockRed:Lcom/Pyro/pyro/module/Value;
        //   335: aload_0         /* this */
        //   336: new             Lcom/Pyro/pyro/module/Value;
        //   339: dup            
        //   340: ldc             "BedrockGreen"
        //   342: iconst_1       
        //   343: anewarray       Ljava/lang/String;
        //   346: dup            
        //   347: iconst_0       
        //   348: ldc             "bGreen"
        //   350: aastore        
        //   351: ldc             "Green for rendering"
        //   353: fconst_1       
        //   354: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   357: fconst_0       
        //   358: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   361: fconst_1       
        //   362: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   365: ldc             0.1
        //   367: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   370: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   373: putfield        com/Pyro/pyro/module/render/HoleESP.BedrockGreen:Lcom/Pyro/pyro/module/Value;
        //   376: aload_0         /* this */
        //   377: new             Lcom/Pyro/pyro/module/Value;
        //   380: dup            
        //   381: ldc             "BedrockBlue"
        //   383: iconst_1       
        //   384: anewarray       Ljava/lang/String;
        //   387: dup            
        //   388: iconst_0       
        //   389: ldc             "bBlue"
        //   391: aastore        
        //   392: ldc             "Blue for rendering"
        //   394: ldc             0.8
        //   396: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   399: fconst_0       
        //   400: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   403: fconst_1       
        //   404: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   407: ldc             0.1
        //   409: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   412: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   415: putfield        com/Pyro/pyro/module/render/HoleESP.BedrockBlue:Lcom/Pyro/pyro/module/Value;
        //   418: aload_0         /* this */
        //   419: new             Lcom/Pyro/pyro/module/Value;
        //   422: dup            
        //   423: ldc             "BedrockAlpha"
        //   425: iconst_1       
        //   426: anewarray       Ljava/lang/String;
        //   429: dup            
        //   430: iconst_0       
        //   431: ldc             "bAlpha"
        //   433: aastore        
        //   434: ldc             "Alpha for rendering"
        //   436: ldc             0.5
        //   438: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   441: fconst_0       
        //   442: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   445: fconst_1       
        //   446: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   449: ldc             0.1
        //   451: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   454: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   457: putfield        com/Pyro/pyro/module/render/HoleESP.BedrockAlpha:Lcom/Pyro/pyro/module/Value;
        //   460: aload_0         /* this */
        //   461: new             Ljava/util/concurrent/CopyOnWriteArrayList;
        //   464: dup            
        //   465: invokespecial   java/util/concurrent/CopyOnWriteArrayList.<init>:()V
        //   468: putfield        com/Pyro/pyro/module/render/HoleESP.holes:Ljava/util/List;
        //   471: aload_0         /* this */
        //   472: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   475: dup            
        //   476: aload_0         /* this */
        //   477: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/render/HoleESP;)Ljava/util/function/Consumer;
        //   482: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   485: putfield        com/Pyro/pyro/module/render/HoleESP.OnPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   488: aload_0         /* this */
        //   489: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   492: dup            
        //   493: aload_0         /* this */
        //   494: invokedynamic   BootstrapMethod #1, accept:(Lcom/Pyro/pyro/module/render/HoleESP;)Ljava/util/function/Consumer;
        //   499: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   502: putfield        com/Pyro/pyro/module/render/HoleESP.OnRenderEvent:Lcom/Pyro/pyro/events/bus/Listener;
        //   505: aload_0         /* this */
        //   506: getfield        com/Pyro/pyro/module/render/HoleESP.HoleMode:Lcom/Pyro/pyro/module/Value;
        //   509: ldc             "None"
        //   511: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   514: aload_0         /* this */
        //   515: getfield        com/Pyro/pyro/module/render/HoleESP.HoleMode:Lcom/Pyro/pyro/module/Value;
        //   518: ldc             "FlatOutline"
        //   520: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   523: aload_0         /* this */
        //   524: getfield        com/Pyro/pyro/module/render/HoleESP.HoleMode:Lcom/Pyro/pyro/module/Value;
        //   527: ldc             "Flat"
        //   529: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   532: aload_0         /* this */
        //   533: getfield        com/Pyro/pyro/module/render/HoleESP.HoleMode:Lcom/Pyro/pyro/module/Value;
        //   536: ldc             "Outline"
        //   538: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   541: aload_0         /* this */
        //   542: getfield        com/Pyro/pyro/module/render/HoleESP.HoleMode:Lcom/Pyro/pyro/module/Value;
        //   545: ldc             "Full"
        //   547: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   550: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
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
}
