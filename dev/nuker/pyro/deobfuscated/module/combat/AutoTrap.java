//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import dev.nuker.pyro.deobfuscated.util.BlockInteractionHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import net.minecraft.util.math.Vec3d;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class AutoTrap extends Module
{
    private final Vec3d[] offsetsDefault;
    private final Vec3d[] offsetsTall;
    public final Value<Boolean> toggleMode;
    public final Value<Float> range;
    public final Value<Integer> blockPerTick;
    public final Value<Boolean> rotate;
    public final Value<Boolean> announceUsage;
    public final Value<Boolean> EChests;
    public final Value<String> Mode;
    private String lastTickTargetName;
    private int playerHotbarSlot;
    private int lastHotbarSlot;
    private boolean isSneaking;
    private int offsetStep;
    private boolean firstRun;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AutoTrap() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "AutoTrap"
        //     3: iconst_1       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             "AutoTrap"
        //    11: aastore        
        //    12: ldc             "Traps enemies in obsidian"
        //    14: ldc             "NONE"
        //    16: ldc             2415427
        //    18: getstatic       com/Pyro/pyro/module/Module$ModuleType.COMBAT:Lcom/Pyro/pyro/module/Module$ModuleType;
        //    21: invokespecial   com/Pyro/pyro/module/Module.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/Pyro/pyro/module/Module$ModuleType;)V
        //    24: aload_0         /* this */
        //    25: bipush          14
        //    27: anewarray       Lnet/minecraft/util/math/Vec3d;
        //    30: dup            
        //    31: iconst_0       
        //    32: new             Lnet/minecraft/util/math/Vec3d;
        //    35: dup            
        //    36: dconst_0       
        //    37: dconst_0       
        //    38: ldc2_w          -1.0
        //    41: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //    44: aastore        
        //    45: dup            
        //    46: iconst_1       
        //    47: new             Lnet/minecraft/util/math/Vec3d;
        //    50: dup            
        //    51: dconst_1       
        //    52: dconst_0       
        //    53: dconst_0       
        //    54: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //    57: aastore        
        //    58: dup            
        //    59: iconst_2       
        //    60: new             Lnet/minecraft/util/math/Vec3d;
        //    63: dup            
        //    64: dconst_0       
        //    65: dconst_0       
        //    66: dconst_1       
        //    67: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //    70: aastore        
        //    71: dup            
        //    72: iconst_3       
        //    73: new             Lnet/minecraft/util/math/Vec3d;
        //    76: dup            
        //    77: ldc2_w          -1.0
        //    80: dconst_0       
        //    81: dconst_0       
        //    82: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //    85: aastore        
        //    86: dup            
        //    87: iconst_4       
        //    88: new             Lnet/minecraft/util/math/Vec3d;
        //    91: dup            
        //    92: dconst_0       
        //    93: dconst_1       
        //    94: ldc2_w          -1.0
        //    97: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   100: aastore        
        //   101: dup            
        //   102: iconst_5       
        //   103: new             Lnet/minecraft/util/math/Vec3d;
        //   106: dup            
        //   107: dconst_1       
        //   108: dconst_1       
        //   109: dconst_0       
        //   110: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   113: aastore        
        //   114: dup            
        //   115: bipush          6
        //   117: new             Lnet/minecraft/util/math/Vec3d;
        //   120: dup            
        //   121: dconst_0       
        //   122: dconst_1       
        //   123: dconst_1       
        //   124: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   127: aastore        
        //   128: dup            
        //   129: bipush          7
        //   131: new             Lnet/minecraft/util/math/Vec3d;
        //   134: dup            
        //   135: ldc2_w          -1.0
        //   138: dconst_1       
        //   139: dconst_0       
        //   140: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   143: aastore        
        //   144: dup            
        //   145: bipush          8
        //   147: new             Lnet/minecraft/util/math/Vec3d;
        //   150: dup            
        //   151: dconst_0       
        //   152: ldc2_w          2.0
        //   155: ldc2_w          -1.0
        //   158: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   161: aastore        
        //   162: dup            
        //   163: bipush          9
        //   165: new             Lnet/minecraft/util/math/Vec3d;
        //   168: dup            
        //   169: dconst_1       
        //   170: ldc2_w          2.0
        //   173: dconst_0       
        //   174: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   177: aastore        
        //   178: dup            
        //   179: bipush          10
        //   181: new             Lnet/minecraft/util/math/Vec3d;
        //   184: dup            
        //   185: dconst_0       
        //   186: ldc2_w          2.0
        //   189: dconst_1       
        //   190: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   193: aastore        
        //   194: dup            
        //   195: bipush          11
        //   197: new             Lnet/minecraft/util/math/Vec3d;
        //   200: dup            
        //   201: ldc2_w          -1.0
        //   204: ldc2_w          2.0
        //   207: dconst_0       
        //   208: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   211: aastore        
        //   212: dup            
        //   213: bipush          12
        //   215: new             Lnet/minecraft/util/math/Vec3d;
        //   218: dup            
        //   219: dconst_0       
        //   220: ldc2_w          3.0
        //   223: ldc2_w          -1.0
        //   226: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   229: aastore        
        //   230: dup            
        //   231: bipush          13
        //   233: new             Lnet/minecraft/util/math/Vec3d;
        //   236: dup            
        //   237: dconst_0       
        //   238: ldc2_w          3.0
        //   241: dconst_0       
        //   242: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   245: aastore        
        //   246: putfield        com/Pyro/pyro/module/combat/AutoTrap.offsetsDefault:[Lnet/minecraft/util/math/Vec3d;
        //   249: aload_0         /* this */
        //   250: bipush          15
        //   252: anewarray       Lnet/minecraft/util/math/Vec3d;
        //   255: dup            
        //   256: iconst_0       
        //   257: new             Lnet/minecraft/util/math/Vec3d;
        //   260: dup            
        //   261: dconst_0       
        //   262: dconst_0       
        //   263: ldc2_w          -1.0
        //   266: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   269: aastore        
        //   270: dup            
        //   271: iconst_1       
        //   272: new             Lnet/minecraft/util/math/Vec3d;
        //   275: dup            
        //   276: dconst_1       
        //   277: dconst_0       
        //   278: dconst_0       
        //   279: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   282: aastore        
        //   283: dup            
        //   284: iconst_2       
        //   285: new             Lnet/minecraft/util/math/Vec3d;
        //   288: dup            
        //   289: dconst_0       
        //   290: dconst_0       
        //   291: dconst_1       
        //   292: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   295: aastore        
        //   296: dup            
        //   297: iconst_3       
        //   298: new             Lnet/minecraft/util/math/Vec3d;
        //   301: dup            
        //   302: ldc2_w          -1.0
        //   305: dconst_0       
        //   306: dconst_0       
        //   307: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   310: aastore        
        //   311: dup            
        //   312: iconst_4       
        //   313: new             Lnet/minecraft/util/math/Vec3d;
        //   316: dup            
        //   317: dconst_0       
        //   318: dconst_1       
        //   319: ldc2_w          -1.0
        //   322: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   325: aastore        
        //   326: dup            
        //   327: iconst_5       
        //   328: new             Lnet/minecraft/util/math/Vec3d;
        //   331: dup            
        //   332: dconst_1       
        //   333: dconst_1       
        //   334: dconst_0       
        //   335: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   338: aastore        
        //   339: dup            
        //   340: bipush          6
        //   342: new             Lnet/minecraft/util/math/Vec3d;
        //   345: dup            
        //   346: dconst_0       
        //   347: dconst_1       
        //   348: dconst_1       
        //   349: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   352: aastore        
        //   353: dup            
        //   354: bipush          7
        //   356: new             Lnet/minecraft/util/math/Vec3d;
        //   359: dup            
        //   360: ldc2_w          -1.0
        //   363: dconst_1       
        //   364: dconst_0       
        //   365: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   368: aastore        
        //   369: dup            
        //   370: bipush          8
        //   372: new             Lnet/minecraft/util/math/Vec3d;
        //   375: dup            
        //   376: dconst_0       
        //   377: ldc2_w          2.0
        //   380: ldc2_w          -1.0
        //   383: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   386: aastore        
        //   387: dup            
        //   388: bipush          9
        //   390: new             Lnet/minecraft/util/math/Vec3d;
        //   393: dup            
        //   394: dconst_1       
        //   395: ldc2_w          2.0
        //   398: dconst_0       
        //   399: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   402: aastore        
        //   403: dup            
        //   404: bipush          10
        //   406: new             Lnet/minecraft/util/math/Vec3d;
        //   409: dup            
        //   410: dconst_0       
        //   411: ldc2_w          2.0
        //   414: dconst_1       
        //   415: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   418: aastore        
        //   419: dup            
        //   420: bipush          11
        //   422: new             Lnet/minecraft/util/math/Vec3d;
        //   425: dup            
        //   426: ldc2_w          -1.0
        //   429: ldc2_w          2.0
        //   432: dconst_0       
        //   433: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   436: aastore        
        //   437: dup            
        //   438: bipush          12
        //   440: new             Lnet/minecraft/util/math/Vec3d;
        //   443: dup            
        //   444: dconst_0       
        //   445: ldc2_w          3.0
        //   448: ldc2_w          -1.0
        //   451: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   454: aastore        
        //   455: dup            
        //   456: bipush          13
        //   458: new             Lnet/minecraft/util/math/Vec3d;
        //   461: dup            
        //   462: dconst_0       
        //   463: ldc2_w          3.0
        //   466: dconst_0       
        //   467: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   470: aastore        
        //   471: dup            
        //   472: bipush          14
        //   474: new             Lnet/minecraft/util/math/Vec3d;
        //   477: dup            
        //   478: dconst_0       
        //   479: ldc2_w          4.0
        //   482: dconst_0       
        //   483: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   486: aastore        
        //   487: putfield        com/Pyro/pyro/module/combat/AutoTrap.offsetsTall:[Lnet/minecraft/util/math/Vec3d;
        //   490: aload_0         /* this */
        //   491: new             Lcom/Pyro/pyro/module/Value;
        //   494: dup            
        //   495: ldc             "toggleMode"
        //   497: iconst_1       
        //   498: anewarray       Ljava/lang/String;
        //   501: dup            
        //   502: iconst_0       
        //   503: ldc             "toggleMode "
        //   505: aastore        
        //   506: ldc             "ToggleMode"
        //   508: iconst_1       
        //   509: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   512: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   515: putfield        com/Pyro/pyro/module/combat/AutoTrap.toggleMode:Lcom/Pyro/pyro/module/Value;
        //   518: aload_0         /* this */
        //   519: new             Lcom/Pyro/pyro/module/Value;
        //   522: dup            
        //   523: ldc             "range"
        //   525: iconst_1       
        //   526: anewarray       Ljava/lang/String;
        //   529: dup            
        //   530: iconst_0       
        //   531: ldc             "range"
        //   533: aastore        
        //   534: ldc             "Range"
        //   536: ldc             5.5
        //   538: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   541: fconst_0       
        //   542: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   545: ldc             10.0
        //   547: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   550: fconst_1       
        //   551: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   554: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   557: putfield        com/Pyro/pyro/module/combat/AutoTrap.range:Lcom/Pyro/pyro/module/Value;
        //   560: aload_0         /* this */
        //   561: new             Lcom/Pyro/pyro/module/Value;
        //   564: dup            
        //   565: ldc             "blockPerTick"
        //   567: iconst_1       
        //   568: anewarray       Ljava/lang/String;
        //   571: dup            
        //   572: iconst_0       
        //   573: ldc             "blockPerTick"
        //   575: aastore        
        //   576: ldc             "Blocks per Tick"
        //   578: iconst_4       
        //   579: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   582: iconst_1       
        //   583: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   586: bipush          10
        //   588: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   591: iconst_1       
        //   592: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   595: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   598: putfield        com/Pyro/pyro/module/combat/AutoTrap.blockPerTick:Lcom/Pyro/pyro/module/Value;
        //   601: aload_0         /* this */
        //   602: new             Lcom/Pyro/pyro/module/Value;
        //   605: dup            
        //   606: ldc             "rotate"
        //   608: iconst_1       
        //   609: anewarray       Ljava/lang/String;
        //   612: dup            
        //   613: iconst_0       
        //   614: ldc             "rotate"
        //   616: aastore        
        //   617: ldc             "Rotate"
        //   619: iconst_1       
        //   620: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   623: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   626: putfield        com/Pyro/pyro/module/combat/AutoTrap.rotate:Lcom/Pyro/pyro/module/Value;
        //   629: aload_0         /* this */
        //   630: new             Lcom/Pyro/pyro/module/Value;
        //   633: dup            
        //   634: ldc             "announceUsage"
        //   636: iconst_1       
        //   637: anewarray       Ljava/lang/String;
        //   640: dup            
        //   641: iconst_0       
        //   642: ldc             "announceUsage"
        //   644: aastore        
        //   645: ldc             "Announce Usage"
        //   647: iconst_1       
        //   648: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   651: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   654: putfield        com/Pyro/pyro/module/combat/AutoTrap.announceUsage:Lcom/Pyro/pyro/module/Value;
        //   657: aload_0         /* this */
        //   658: new             Lcom/Pyro/pyro/module/Value;
        //   661: dup            
        //   662: ldc             "EChests"
        //   664: iconst_1       
        //   665: anewarray       Ljava/lang/String;
        //   668: dup            
        //   669: iconst_0       
        //   670: ldc             "EChests"
        //   672: aastore        
        //   673: ldc             "EChests"
        //   675: iconst_0       
        //   676: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   679: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   682: putfield        com/Pyro/pyro/module/combat/AutoTrap.EChests:Lcom/Pyro/pyro/module/Value;
        //   685: aload_0         /* this */
        //   686: new             Lcom/Pyro/pyro/module/Value;
        //   689: dup            
        //   690: ldc             "Mode"
        //   692: iconst_1       
        //   693: anewarray       Ljava/lang/String;
        //   696: dup            
        //   697: iconst_0       
        //   698: ldc             "Mode"
        //   700: aastore        
        //   701: ldc             "The mode to use for autotrap"
        //   703: ldc             "Full"
        //   705: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   708: putfield        com/Pyro/pyro/module/combat/AutoTrap.Mode:Lcom/Pyro/pyro/module/Value;
        //   711: aload_0         /* this */
        //   712: ldc             ""
        //   714: putfield        com/Pyro/pyro/module/combat/AutoTrap.lastTickTargetName:Ljava/lang/String;
        //   717: aload_0         /* this */
        //   718: iconst_m1      
        //   719: putfield        com/Pyro/pyro/module/combat/AutoTrap.playerHotbarSlot:I
        //   722: aload_0         /* this */
        //   723: iconst_m1      
        //   724: putfield        com/Pyro/pyro/module/combat/AutoTrap.lastHotbarSlot:I
        //   727: aload_0         /* this */
        //   728: iconst_0       
        //   729: putfield        com/Pyro/pyro/module/combat/AutoTrap.isSneaking:Z
        //   732: aload_0         /* this */
        //   733: iconst_0       
        //   734: putfield        com/Pyro/pyro/module/combat/AutoTrap.offsetStep:I
        //   737: aload_0         /* this */
        //   738: iconst_1       
        //   739: putfield        com/Pyro/pyro/module/combat/AutoTrap.firstRun:Z
        //   742: aload_0         /* this */
        //   743: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   746: dup            
        //   747: aload_0         /* this */
        //   748: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/combat/AutoTrap;)Ljava/util/function/Consumer;
        //   753: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   756: putfield        com/Pyro/pyro/module/combat/AutoTrap.onPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   759: aload_0         /* this */
        //   760: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   763: dup            
        //   764: aload_0         /* this */
        //   765: invokedynamic   BootstrapMethod #1, accept:(Lcom/Pyro/pyro/module/combat/AutoTrap;)Ljava/util/function/Consumer;
        //   770: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   773: putfield        com/Pyro/pyro/module/combat/AutoTrap.OnPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   776: aload_0         /* this */
        //   777: aload_0         /* this */
        //   778: invokevirtual   com/Pyro/pyro/module/combat/AutoTrap.getMetaData:()Ljava/lang/String;
        //   781: invokevirtual   com/Pyro/pyro/module/combat/AutoTrap.setMetaData:(Ljava/lang/String;)V
        //   784: aload_0         /* this */
        //   785: getfield        com/Pyro/pyro/module/combat/AutoTrap.Mode:Lcom/Pyro/pyro/module/Value;
        //   788: ldc             "Full"
        //   790: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   793: aload_0         /* this */
        //   794: getfield        com/Pyro/pyro/module/combat/AutoTrap.Mode:Lcom/Pyro/pyro/module/Value;
        //   797: ldc             "Tall"
        //   799: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   802: return         
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
        if (this.EChests.getValue()) {
            return "Ender Chests";
        }
        return "Obsidian";
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.player == null) {
            this.toggle();
            return;
        }
        this.firstRun = true;
        this.playerHotbarSlot = this.mc.player.inventory.currentItem;
        this.lastHotbarSlot = -1;
        if (this.findObiInHotbar() == -1) {
            Pyro.SendMessage(String.format("[AutoTrap] You do not have any %s in your hotbar!", ChatFormatting.LIGHT_PURPLE + (this.EChests.getValue() ? "Ender Chests" : "Obsidian") + ChatFormatting.RESET));
            this.toggle();
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            this.mc.player.inventory.currentItem = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        if (this.announceUsage.getValue()) {
            Pyro.SendMessage("[AutoTrap] Disabled!");
        }
    }
    
    private boolean placeBlock(final BlockPos pos) {
        if (!this.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return false;
        }
        if (!BlockInteractionHelper.checkForNeighbours(pos)) {
            return false;
        }
        final Vec3d eyesPos = new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (this.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(this.mc.world.getBlockState(neighbor), false)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.distanceTo(hitVec) <= this.range.getValue()) {
                    final int obiSlot = this.findObiInHotbar();
                    if (obiSlot == -1) {
                        this.toggle();
                        return false;
                    }
                    if (this.lastHotbarSlot != obiSlot) {
                        this.mc.player.inventory.currentItem = obiSlot;
                        this.lastHotbarSlot = obiSlot;
                    }
                    final Block neighborPos = this.mc.world.getBlockState(neighbor).getBlock();
                    if (BlockInteractionHelper.blackList.contains(neighborPos) || BlockInteractionHelper.shulkerList.contains(neighborPos)) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                        this.isSneaking = true;
                    }
                    if (this.rotate.getValue()) {
                        BlockInteractionHelper.faceVectorPacketInstant(hitVec);
                    }
                    this.mc.playerController.processRightClickBlock(this.mc.player, this.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                    this.mc.player.swingArm(EnumHand.MAIN_HAND);
                    return true;
                }
            }
        }
        return false;
    }
    
    private int findObiInHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (this.EChests.getValue()) {
                    if (block instanceof BlockEnderChest) {
                        return i;
                    }
                }
                else if (block instanceof BlockObsidian) {
                    return i;
                }
            }
        }
        return -1;
    }
}
