// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderBossHealth;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderMap;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderArmorLayer;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderSign;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerIsPotionActive;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderHurtCameraEffect;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderEntity;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class NoRender extends Module
{
    public final Value<String> NoItems;
    public final Value<Boolean> Fire;
    public final Value<Boolean> NoHurtCam;
    public final Value<Boolean> PumpkinOverlay;
    public final Value<Boolean> Blindness;
    public final Value<Boolean> TotemAnimation;
    public final Value<Boolean> Skylight;
    public final Value<Boolean> SignText;
    public final Value<Boolean> NoArmor;
    public final Value<Boolean> NoArmorPlayers;
    public final Value<Boolean> Maps;
    public final Value<Boolean> BossHealth;
    private Timer timer;
    @EventHandler
    private Listener<EventRenderEntity> OnRenderEntity;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventRenderHurtCameraEffect> OnHurtCameraEffect;
    @EventHandler
    private Listener<RenderBlockOverlayEvent> OnBlockOverlayEvent;
    @EventHandler
    private Listener<EventPlayerIsPotionActive> IsPotionActive;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    @EventHandler
    private Listener<EventRenderSign> OnRenderSign;
    @EventHandler
    private Listener<EventRenderArmorLayer> OnRenderArmorLayer;
    @EventHandler
    private Listener<EventRenderMap> OnRenderMap;
    @EventHandler
    private Listener<EventRenderBossHealth> OnRenderBossHealth;
    
    public NoRender() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "NoRender"
        //     3: iconst_1       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             "NR"
        //    11: aastore        
        //    12: ldc             "Doesn't render certain things, if enabled"
        //    14: ldc             "NONE"
        //    16: iconst_m1      
        //    17: getstatic       com/Pyro/pyro/module/Module$ModuleType.RENDER:Lcom/Pyro/pyro/module/Module$ModuleType;
        //    20: invokespecial   com/Pyro/pyro/module/Module.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/Pyro/pyro/module/Module$ModuleType;)V
        //    23: aload_0         /* this */
        //    24: new             Lcom/Pyro/pyro/module/Value;
        //    27: dup            
        //    28: ldc             "NoItemsMode"
        //    30: iconst_1       
        //    31: anewarray       Ljava/lang/String;
        //    34: dup            
        //    35: iconst_0       
        //    36: ldc             "NoItems"
        //    38: aastore        
        //    39: ldc             "Prevents items from being rendered"
        //    41: ldc             "Off"
        //    43: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    46: putfield        com/Pyro/pyro/module/render/NoRender.NoItems:Lcom/Pyro/pyro/module/Value;
        //    49: aload_0         /* this */
        //    50: new             Lcom/Pyro/pyro/module/Value;
        //    53: dup            
        //    54: ldc             "Fire"
        //    56: iconst_1       
        //    57: anewarray       Ljava/lang/String;
        //    60: dup            
        //    61: iconst_0       
        //    62: ldc             "Fire"
        //    64: aastore        
        //    65: ldc             "Doesn't render Fire overlay"
        //    67: iconst_1       
        //    68: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    71: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    74: putfield        com/Pyro/pyro/module/render/NoRender.Fire:Lcom/Pyro/pyro/module/Value;
        //    77: aload_0         /* this */
        //    78: new             Lcom/Pyro/pyro/module/Value;
        //    81: dup            
        //    82: ldc             "HurtCamera"
        //    84: iconst_1       
        //    85: anewarray       Ljava/lang/String;
        //    88: dup            
        //    89: iconst_0       
        //    90: ldc             "NHC"
        //    92: aastore        
        //    93: ldc             "Doesn't render the Hurt camera"
        //    95: iconst_1       
        //    96: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    99: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   102: putfield        com/Pyro/pyro/module/render/NoRender.NoHurtCam:Lcom/Pyro/pyro/module/Value;
        //   105: aload_0         /* this */
        //   106: new             Lcom/Pyro/pyro/module/Value;
        //   109: dup            
        //   110: ldc             "PumpkinOverlay"
        //   112: iconst_1       
        //   113: anewarray       Ljava/lang/String;
        //   116: dup            
        //   117: iconst_0       
        //   118: ldc             "PO"
        //   120: aastore        
        //   121: ldc             "Doesn't render the pumpkin overlay"
        //   123: iconst_0       
        //   124: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   127: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   130: putfield        com/Pyro/pyro/module/render/NoRender.PumpkinOverlay:Lcom/Pyro/pyro/module/Value;
        //   133: aload_0         /* this */
        //   134: new             Lcom/Pyro/pyro/module/Value;
        //   137: dup            
        //   138: ldc             "Blindness"
        //   140: iconst_1       
        //   141: anewarray       Ljava/lang/String;
        //   144: dup            
        //   145: iconst_0       
        //   146: ldc             "Blindness"
        //   148: aastore        
        //   149: ldc             "Doesn't render the blindness effect"
        //   151: iconst_1       
        //   152: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   155: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   158: putfield        com/Pyro/pyro/module/render/NoRender.Blindness:Lcom/Pyro/pyro/module/Value;
        //   161: aload_0         /* this */
        //   162: new             Lcom/Pyro/pyro/module/Value;
        //   165: dup            
        //   166: ldc             "TotemAnimation"
        //   168: iconst_1       
        //   169: anewarray       Ljava/lang/String;
        //   172: dup            
        //   173: iconst_0       
        //   174: ldc             "TotemAnimation"
        //   176: aastore        
        //   177: ldc             "Doesn't render the totem animation"
        //   179: iconst_0       
        //   180: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   183: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   186: putfield        com/Pyro/pyro/module/render/NoRender.TotemAnimation:Lcom/Pyro/pyro/module/Value;
        //   189: aload_0         /* this */
        //   190: new             Lcom/Pyro/pyro/module/Value;
        //   193: dup            
        //   194: ldc             "Skylight"
        //   196: iconst_1       
        //   197: anewarray       Ljava/lang/String;
        //   200: dup            
        //   201: iconst_0       
        //   202: ldc             "Skylight"
        //   204: aastore        
        //   205: ldc             "Doesn't render skylight updates"
        //   207: iconst_0       
        //   208: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   211: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   214: putfield        com/Pyro/pyro/module/render/NoRender.Skylight:Lcom/Pyro/pyro/module/Value;
        //   217: aload_0         /* this */
        //   218: new             Lcom/Pyro/pyro/module/Value;
        //   221: dup            
        //   222: ldc             "SignText"
        //   224: iconst_1       
        //   225: anewarray       Ljava/lang/String;
        //   228: dup            
        //   229: iconst_0       
        //   230: ldc             "SignText"
        //   232: aastore        
        //   233: ldc             "Doesn't render SignText"
        //   235: iconst_0       
        //   236: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   239: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   242: putfield        com/Pyro/pyro/module/render/NoRender.SignText:Lcom/Pyro/pyro/module/Value;
        //   245: aload_0         /* this */
        //   246: new             Lcom/Pyro/pyro/module/Value;
        //   249: dup            
        //   250: ldc             "NoArmor"
        //   252: iconst_1       
        //   253: anewarray       Ljava/lang/String;
        //   256: dup            
        //   257: iconst_0       
        //   258: ldc             "NoArmor"
        //   260: aastore        
        //   261: ldc             "Doesn't render armor"
        //   263: iconst_0       
        //   264: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   267: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   270: putfield        com/Pyro/pyro/module/render/NoRender.NoArmor:Lcom/Pyro/pyro/module/Value;
        //   273: aload_0         /* this */
        //   274: new             Lcom/Pyro/pyro/module/Value;
        //   277: dup            
        //   278: ldc             "NoArmorPlayers"
        //   280: iconst_1       
        //   281: anewarray       Ljava/lang/String;
        //   284: dup            
        //   285: iconst_0       
        //   286: ldc             "NoArmorPlayers"
        //   288: aastore        
        //   289: ldc             "Use in conjunction with the above mod"
        //   291: iconst_0       
        //   292: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   295: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   298: putfield        com/Pyro/pyro/module/render/NoRender.NoArmorPlayers:Lcom/Pyro/pyro/module/Value;
        //   301: aload_0         /* this */
        //   302: new             Lcom/Pyro/pyro/module/Value;
        //   305: dup            
        //   306: ldc             "Maps"
        //   308: iconst_1       
        //   309: anewarray       Ljava/lang/String;
        //   312: dup            
        //   313: iconst_0       
        //   314: ldc             "Maps"
        //   316: aastore        
        //   317: ldc             "Doesn't render maps"
        //   319: iconst_0       
        //   320: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   323: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   326: putfield        com/Pyro/pyro/module/render/NoRender.Maps:Lcom/Pyro/pyro/module/Value;
        //   329: aload_0         /* this */
        //   330: new             Lcom/Pyro/pyro/module/Value;
        //   333: dup            
        //   334: ldc             "BossHealth"
        //   336: iconst_1       
        //   337: anewarray       Ljava/lang/String;
        //   340: dup            
        //   341: iconst_0       
        //   342: ldc             "WitherNames"
        //   344: aastore        
        //   345: ldc             "Doesn't render wither names, and other boss health"
        //   347: iconst_0       
        //   348: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   351: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   354: putfield        com/Pyro/pyro/module/render/NoRender.BossHealth:Lcom/Pyro/pyro/module/Value;
        //   357: aload_0         /* this */
        //   358: new             Lcom/Pyro/pyro/util/Timer;
        //   361: dup            
        //   362: invokespecial   com/Pyro/pyro/util/Timer.<init>:()V
        //   365: putfield        com/Pyro/pyro/module/render/NoRender.timer:Lcom/Pyro/pyro/util/Timer;
        //   368: aload_0         /* this */
        //   369: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   372: dup            
        //   373: aload_0         /* this */
        //   374: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/render/NoRender;)Ljava/util/function/Consumer;
        //   379: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   382: putfield        com/Pyro/pyro/module/render/NoRender.OnRenderEntity:Lcom/Pyro/pyro/events/bus/Listener;
        //   385: aload_0         /* this */
        //   386: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   389: dup            
        //   390: aload_0         /* this */
        //   391: invokedynamic   BootstrapMethod #1, accept:(Lcom/Pyro/pyro/module/render/NoRender;)Ljava/util/function/Consumer;
        //   396: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   399: putfield        com/Pyro/pyro/module/render/NoRender.OnPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   402: aload_0         /* this */
        //   403: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   406: dup            
        //   407: aload_0         /* this */
        //   408: invokedynamic   BootstrapMethod #2, accept:(Lcom/Pyro/pyro/module/render/NoRender;)Ljava/util/function/Consumer;
        //   413: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   416: putfield        com/Pyro/pyro/module/render/NoRender.OnHurtCameraEffect:Lcom/Pyro/pyro/events/bus/Listener;
        //   419: aload_0         /* this */
        //   420: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   423: dup            
        //   424: aload_0         /* this */
        //   425: invokedynamic   BootstrapMethod #3, accept:(Lcom/Pyro/pyro/module/render/NoRender;)Ljava/util/function/Consumer;
        //   430: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   433: putfield        com/Pyro/pyro/module/render/NoRender.OnBlockOverlayEvent:Lcom/Pyro/pyro/events/bus/Listener;
        //   436: aload_0         /* this */
        //   437: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   440: dup            
        //   441: aload_0         /* this */
        //   442: invokedynamic   BootstrapMethod #4, accept:(Lcom/Pyro/pyro/module/render/NoRender;)Ljava/util/function/Consumer;
        //   447: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   450: putfield        com/Pyro/pyro/module/render/NoRender.IsPotionActive:Lcom/Pyro/pyro/events/bus/Listener;
        //   453: aload_0         /* this */
        //   454: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   457: dup            
        //   458: aload_0         /* this */
        //   459: invokedynamic   BootstrapMethod #5, accept:(Lcom/Pyro/pyro/module/render/NoRender;)Ljava/util/function/Consumer;
        //   464: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   467: putfield        com/Pyro/pyro/module/render/NoRender.onServerPacket:Lcom/Pyro/pyro/events/bus/Listener;
        //   470: aload_0         /* this */
        //   471: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   474: dup            
        //   475: aload_0         /* this */
        //   476: invokedynamic   BootstrapMethod #6, accept:(Lcom/Pyro/pyro/module/render/NoRender;)Ljava/util/function/Consumer;
        //   481: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   484: putfield        com/Pyro/pyro/module/render/NoRender.OnRenderSign:Lcom/Pyro/pyro/events/bus/Listener;
        //   487: aload_0         /* this */
        //   488: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   491: dup            
        //   492: aload_0         /* this */
        //   493: invokedynamic   BootstrapMethod #7, accept:(Lcom/Pyro/pyro/module/render/NoRender;)Ljava/util/function/Consumer;
        //   498: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   501: putfield        com/Pyro/pyro/module/render/NoRender.OnRenderArmorLayer:Lcom/Pyro/pyro/events/bus/Listener;
        //   504: aload_0         /* this */
        //   505: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   508: dup            
        //   509: aload_0         /* this */
        //   510: invokedynamic   BootstrapMethod #8, accept:(Lcom/Pyro/pyro/module/render/NoRender;)Ljava/util/function/Consumer;
        //   515: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   518: putfield        com/Pyro/pyro/module/render/NoRender.OnRenderMap:Lcom/Pyro/pyro/events/bus/Listener;
        //   521: aload_0         /* this */
        //   522: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   525: dup            
        //   526: aload_0         /* this */
        //   527: invokedynamic   BootstrapMethod #9, accept:(Lcom/Pyro/pyro/module/render/NoRender;)Ljava/util/function/Consumer;
        //   532: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   535: putfield        com/Pyro/pyro/module/render/NoRender.OnRenderBossHealth:Lcom/Pyro/pyro/events/bus/Listener;
        //   538: aload_0         /* this */
        //   539: getfield        com/Pyro/pyro/module/render/NoRender.NoItems:Lcom/Pyro/pyro/module/Value;
        //   542: ldc             "Off"
        //   544: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   547: aload_0         /* this */
        //   548: getfield        com/Pyro/pyro/module/render/NoRender.NoItems:Lcom/Pyro/pyro/module/Value;
        //   551: ldc_w           "Remove"
        //   554: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   557: aload_0         /* this */
        //   558: getfield        com/Pyro/pyro/module/render/NoRender.NoItems:Lcom/Pyro/pyro/module/Value;
        //   561: ldc_w           "Hide"
        //   564: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   567: return         
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
}
