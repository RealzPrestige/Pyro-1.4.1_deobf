//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.util.EnumHand;
import net.minecraft.network.play.client.CPacketPlayer;
import dev.nuker.pyro.deobfuscated.managers.TickRateManager;
import java.util.Comparator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.EntityLivingBase;
import dev.nuker.pyro.deobfuscated.managers.FriendManager;
import javax.annotation.Nullable;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Aura extends Module
{
    public final Value<String> Mode;
    public final Value<Float> Distance;
    public final Value<Float> WallsRange;
    public final Value<Boolean> HitDelay;
    public final Value<Boolean> TpsSync;
    public final Value<String> SwitchMode;
    public final Value<Boolean> Players;
    public final Value<Boolean> Monsters;
    public final Value<Boolean> Neutrals;
    public final Value<Boolean> Animals;
    public final Value<Boolean> Tamed;
    public final Value<Boolean> Shulker;
    public final Value<Boolean> Projectiles;
    private Entity _lastTarget;
    private Timer _rotationEndTimer;
    private float[] _rotations;
    private Timer critTimer;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdate;
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    
    public Aura() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "Aura"
        //     3: iconst_1       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             "KillAura, Forcefield"
        //    11: aastore        
        //    12: ldc             "Automatically attacks enemies in range"
        //    14: ldc             "NONE"
        //    16: ldc             16393729
        //    18: getstatic       com/Pyro/pyro/module/Module$ModuleType.COMBAT:Lcom/Pyro/pyro/module/Module$ModuleType;
        //    21: invokespecial   com/Pyro/pyro/module/Module.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/Pyro/pyro/module/Module$ModuleType;)V
        //    24: aload_0         /* this */
        //    25: new             Lcom/Pyro/pyro/module/Value;
        //    28: dup            
        //    29: ldc             "Mode"
        //    31: iconst_1       
        //    32: anewarray       Ljava/lang/String;
        //    35: dup            
        //    36: iconst_0       
        //    37: ldc             "Mode"
        //    39: aastore        
        //    40: ldc             "The KillAura Mode to use"
        //    42: ldc             "Closest"
        //    44: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    47: putfield        com/Pyro/pyro/module/combat/Aura.Mode:Lcom/Pyro/pyro/module/Value;
        //    50: aload_0         /* this */
        //    51: new             Lcom/Pyro/pyro/module/Value;
        //    54: dup            
        //    55: ldc             "Distance"
        //    57: iconst_1       
        //    58: anewarray       Ljava/lang/String;
        //    61: dup            
        //    62: iconst_0       
        //    63: ldc             "Range"
        //    65: aastore        
        //    66: ldc             "Range for attacking a target"
        //    68: ldc             5.0
        //    70: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    73: fconst_0       
        //    74: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    77: ldc             10.0
        //    79: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    82: fconst_1       
        //    83: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    86: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //    89: putfield        com/Pyro/pyro/module/combat/Aura.Distance:Lcom/Pyro/pyro/module/Value;
        //    92: aload_0         /* this */
        //    93: new             Lcom/Pyro/pyro/module/Value;
        //    96: dup            
        //    97: ldc             "WallsRange"
        //    99: iconst_1       
        //   100: anewarray       Ljava/lang/String;
        //   103: dup            
        //   104: iconst_0       
        //   105: ldc             "Wall"
        //   107: aastore        
        //   108: ldc             "Range for attacking a target"
        //   110: ldc             5.0
        //   112: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   115: fconst_0       
        //   116: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   119: ldc             10.0
        //   121: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   124: fconst_1       
        //   125: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   128: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   131: putfield        com/Pyro/pyro/module/combat/Aura.WallsRange:Lcom/Pyro/pyro/module/Value;
        //   134: aload_0         /* this */
        //   135: new             Lcom/Pyro/pyro/module/Value;
        //   138: dup            
        //   139: ldc             "Hit Delay"
        //   141: iconst_1       
        //   142: anewarray       Ljava/lang/String;
        //   145: dup            
        //   146: iconst_0       
        //   147: ldc             "Hit Delay"
        //   149: aastore        
        //   150: ldc             "Use vanilla hit delay"
        //   152: iconst_1       
        //   153: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   156: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   159: putfield        com/Pyro/pyro/module/combat/Aura.HitDelay:Lcom/Pyro/pyro/module/Value;
        //   162: aload_0         /* this */
        //   163: new             Lcom/Pyro/pyro/module/Value;
        //   166: dup            
        //   167: ldc             "TpsSync"
        //   169: iconst_1       
        //   170: anewarray       Ljava/lang/String;
        //   173: dup            
        //   174: iconst_0       
        //   175: ldc             "TPS"
        //   177: aastore        
        //   178: ldc             "Tps syncs the HitDelay"
        //   180: iconst_0       
        //   181: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   184: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   187: putfield        com/Pyro/pyro/module/combat/Aura.TpsSync:Lcom/Pyro/pyro/module/Value;
        //   190: aload_0         /* this */
        //   191: new             Lcom/Pyro/pyro/module/Value;
        //   194: dup            
        //   195: ldc             "SwitchMode"
        //   197: iconst_1       
        //   198: anewarray       Ljava/lang/String;
        //   201: dup            
        //   202: iconst_0       
        //   203: ldc             "AutoSwitch"
        //   205: aastore        
        //   206: ldc             "Different types of switch modes"
        //   208: ldc             "Auto"
        //   210: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   213: putfield        com/Pyro/pyro/module/combat/Aura.SwitchMode:Lcom/Pyro/pyro/module/Value;
        //   216: aload_0         /* this */
        //   217: new             Lcom/Pyro/pyro/module/Value;
        //   220: dup            
        //   221: ldc             "Players"
        //   223: iconst_1       
        //   224: anewarray       Ljava/lang/String;
        //   227: dup            
        //   228: iconst_0       
        //   229: ldc             "Players"
        //   231: aastore        
        //   232: ldc             "Should we target Players"
        //   234: iconst_1       
        //   235: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   238: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   241: putfield        com/Pyro/pyro/module/combat/Aura.Players:Lcom/Pyro/pyro/module/Value;
        //   244: aload_0         /* this */
        //   245: new             Lcom/Pyro/pyro/module/Value;
        //   248: dup            
        //   249: ldc             "Monsters"
        //   251: iconst_1       
        //   252: anewarray       Ljava/lang/String;
        //   255: dup            
        //   256: iconst_0       
        //   257: ldc             "Players"
        //   259: aastore        
        //   260: ldc             "Should we target Monsters"
        //   262: iconst_1       
        //   263: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   266: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   269: putfield        com/Pyro/pyro/module/combat/Aura.Monsters:Lcom/Pyro/pyro/module/Value;
        //   272: aload_0         /* this */
        //   273: new             Lcom/Pyro/pyro/module/Value;
        //   276: dup            
        //   277: ldc             "Neutrals"
        //   279: iconst_1       
        //   280: anewarray       Ljava/lang/String;
        //   283: dup            
        //   284: iconst_0       
        //   285: ldc             "Neutral"
        //   287: aastore        
        //   288: ldc             "Should we target Neutrals"
        //   290: iconst_0       
        //   291: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   294: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   297: putfield        com/Pyro/pyro/module/combat/Aura.Neutrals:Lcom/Pyro/pyro/module/Value;
        //   300: aload_0         /* this */
        //   301: new             Lcom/Pyro/pyro/module/Value;
        //   304: dup            
        //   305: ldc             "Animals"
        //   307: iconst_1       
        //   308: anewarray       Ljava/lang/String;
        //   311: dup            
        //   312: iconst_0       
        //   313: ldc             "Cows"
        //   315: aastore        
        //   316: ldc             "Should we target Animals"
        //   318: iconst_0       
        //   319: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   322: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   325: putfield        com/Pyro/pyro/module/combat/Aura.Animals:Lcom/Pyro/pyro/module/Value;
        //   328: aload_0         /* this */
        //   329: new             Lcom/Pyro/pyro/module/Value;
        //   332: dup            
        //   333: ldc             "Tamed"
        //   335: iconst_1       
        //   336: anewarray       Ljava/lang/String;
        //   339: dup            
        //   340: iconst_0       
        //   341: ldc             "Tame"
        //   343: aastore        
        //   344: ldc             "Should we target Tamed"
        //   346: iconst_0       
        //   347: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   350: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   353: putfield        com/Pyro/pyro/module/combat/Aura.Tamed:Lcom/Pyro/pyro/module/Value;
        //   356: aload_0         /* this */
        //   357: new             Lcom/Pyro/pyro/module/Value;
        //   360: dup            
        //   361: ldc             "Shulker"
        //   363: iconst_1       
        //   364: anewarray       Ljava/lang/String;
        //   367: dup            
        //   368: iconst_0       
        //   369: ldc             "Shulker"
        //   371: aastore        
        //   372: ldc             "Should we target Shulker"
        //   374: iconst_0       
        //   375: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   378: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   381: putfield        com/Pyro/pyro/module/combat/Aura.Shulker:Lcom/Pyro/pyro/module/Value;
        //   384: aload_0         /* this */
        //   385: new             Lcom/Pyro/pyro/module/Value;
        //   388: dup            
        //   389: ldc             "Projectile"
        //   391: iconst_1       
        //   392: anewarray       Ljava/lang/String;
        //   395: dup            
        //   396: iconst_0       
        //   397: ldc             "Projectile"
        //   399: aastore        
        //   400: ldc             "Should we target Projectiles (shulker bullets, etc)"
        //   402: iconst_0       
        //   403: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   406: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   409: putfield        com/Pyro/pyro/module/combat/Aura.Projectiles:Lcom/Pyro/pyro/module/Value;
        //   412: aload_0         /* this */
        //   413: aconst_null    
        //   414: putfield        com/Pyro/pyro/module/combat/Aura._lastTarget:Lnet/minecraft/entity/Entity;
        //   417: aload_0         /* this */
        //   418: new             Lcom/Pyro/pyro/util/Timer;
        //   421: dup            
        //   422: invokespecial   com/Pyro/pyro/util/Timer.<init>:()V
        //   425: putfield        com/Pyro/pyro/module/combat/Aura._rotationEndTimer:Lcom/Pyro/pyro/util/Timer;
        //   428: aload_0         /* this */
        //   429: new             Lcom/Pyro/pyro/util/Timer;
        //   432: dup            
        //   433: invokespecial   com/Pyro/pyro/util/Timer.<init>:()V
        //   436: putfield        com/Pyro/pyro/module/combat/Aura.critTimer:Lcom/Pyro/pyro/util/Timer;
        //   439: aload_0         /* this */
        //   440: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   443: dup            
        //   444: aload_0         /* this */
        //   445: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/combat/Aura;)Ljava/util/function/Consumer;
        //   450: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   453: putfield        com/Pyro/pyro/module/combat/Aura.onPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   456: aload_0         /* this */
        //   457: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   460: dup            
        //   461: aload_0         /* this */
        //   462: invokedynamic   BootstrapMethod #1, accept:(Lcom/Pyro/pyro/module/combat/Aura;)Ljava/util/function/Consumer;
        //   467: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   470: putfield        com/Pyro/pyro/module/combat/Aura.onMotionUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   473: aload_0         /* this */
        //   474: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   477: dup            
        //   478: aload_0         /* this */
        //   479: invokedynamic   BootstrapMethod #2, accept:(Lcom/Pyro/pyro/module/combat/Aura;)Ljava/util/function/Consumer;
        //   484: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   487: putfield        com/Pyro/pyro/module/combat/Aura.onClientPacket:Lcom/Pyro/pyro/events/bus/Listener;
        //   490: aload_0         /* this */
        //   491: aload_0         /* this */
        //   492: getfield        com/Pyro/pyro/module/combat/Aura.Mode:Lcom/Pyro/pyro/module/Value;
        //   495: invokevirtual   com/Pyro/pyro/module/Value.getValue:()Ljava/lang/Object;
        //   498: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   501: invokevirtual   com/Pyro/pyro/module/combat/Aura.setMetaData:(Ljava/lang/String;)V
        //   504: aload_0         /* this */
        //   505: getfield        com/Pyro/pyro/module/combat/Aura.Mode:Lcom/Pyro/pyro/module/Value;
        //   508: ldc             "Closest"
        //   510: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   513: aload_0         /* this */
        //   514: getfield        com/Pyro/pyro/module/combat/Aura.Mode:Lcom/Pyro/pyro/module/Value;
        //   517: ldc             "Priority"
        //   519: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   522: aload_0         /* this */
        //   523: getfield        com/Pyro/pyro/module/combat/Aura.Mode:Lcom/Pyro/pyro/module/Value;
        //   526: ldc             "Switch"
        //   528: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   531: aload_0         /* this */
        //   532: getfield        com/Pyro/pyro/module/combat/Aura.Mode:Lcom/Pyro/pyro/module/Value;
        //   535: ldc             "LowestHP"
        //   537: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   540: aload_0         /* this */
        //   541: getfield        com/Pyro/pyro/module/combat/Aura.SwitchMode:Lcom/Pyro/pyro/module/Value;
        //   544: ldc             "None"
        //   546: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   549: aload_0         /* this */
        //   550: getfield        com/Pyro/pyro/module/combat/Aura.SwitchMode:Lcom/Pyro/pyro/module/Value;
        //   553: ldc             "Auto"
        //   555: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   558: aload_0         /* this */
        //   559: getfield        com/Pyro/pyro/module/combat/Aura.SwitchMode:Lcom/Pyro/pyro/module/Value;
        //   562: ldc_w           "OnlySword"
        //   565: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   568: return         
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
    
    private boolean isValidTarget(final Entity e, @Nullable final Entity toExclude) {
        if (e == toExclude || this.mc.player == e || FriendManager.Get().IsFriend(e)) {
            return false;
        }
        if (!(e instanceof EntityLivingBase)) {
            final boolean isProj = e instanceof EntityShulkerBullet || e instanceof EntityFireball;
            if (!isProj) {
                return false;
            }
            if (isProj && !this.Projectiles.getValue()) {
                return false;
            }
        }
        if (e instanceof EntityPlayer && !this.Players.getValue()) {
            return false;
        }
        if (EntityUtil.isHostileMob(e) && !this.Monsters.getValue()) {
            return false;
        }
        if (EntityUtil.isNeutralMob(e) && !this.Neutrals.getValue()) {
            return false;
        }
        if (EntityUtil.isPassive(e)) {
            boolean skipCheck = false;
            if (e instanceof EntityTameable) {
                skipCheck = true;
                if (((EntityTameable)e).isTamed() && !this.Tamed.getValue()) {
                    return false;
                }
            }
            else if (e instanceof AbstractHorse) {
                skipCheck = true;
                if (((AbstractHorse)e).isTame() && !this.Tamed.getValue()) {
                    return false;
                }
            }
            if (!skipCheck && !this.Animals.getValue()) {
                return false;
            }
        }
        if (e instanceof EntityShulker && !this.Shulker.getValue()) {
            return false;
        }
        if (e instanceof EntityLivingBase) {
            final EntityLivingBase base = (EntityLivingBase)e;
            if (base.getHealth() + base.getAbsorptionAmount() <= 0.0) {
                return false;
            }
        }
        final float dist = e.getDistance((Entity)this.mc.player);
        return (!e.isDead && dist <= this.Distance.getValue() && this.mc.player.canEntityBeSeen(e)) || dist <= this.WallsRange.getValue();
    }
    
    public void autoSwitchIfEnabled() {
        if (this.SwitchMode.getValue().equals("Auto") && !(this.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
                if (!stack.isEmpty() && stack.getItem() instanceof ItemSword) {
                    this.mc.player.inventory.currentItem = i;
                    this.mc.playerController.updateController();
                    break;
                }
            }
        }
    }
    
    private Entity getTargetEntity() {
        Entity who = this._lastTarget;
        final String s = this.Mode.getValue();
        switch (s) {
            case "Closest": {
                who = (Entity)this.mc.world.loadedEntityList.stream().filter(e -> this.isValidTarget(e, null)).min(Comparator.comparing(e -> this.mc.player.getDistance(e))).orElse(null);
                break;
            }
            case "Priority": {
                if (who == null) {
                    who = (Entity)this.mc.world.loadedEntityList.stream().filter(e -> this.isValidTarget(e, null)).min(Comparator.comparing(e -> this.mc.player.getDistance(e))).orElse(null);
                    break;
                }
                break;
            }
            case "Switch": {
                who = (Entity)this.mc.world.loadedEntityList.stream().filter(e -> this.isValidTarget(e, this.isAttackReady() ? this._lastTarget : null)).min(Comparator.comparing(e -> this.mc.player.getDistance(e))).orElse(null);
                if (who == null) {
                    who = this._lastTarget;
                    break;
                }
                break;
            }
            case "LowestHP": {
                who = (Entity)this.mc.world.loadedEntityList.stream().filter(e -> this.isValidTarget(e, null)).map(e -> e).min(Comparator.comparing(e -> e.getHealth())).orElse(null);
                break;
            }
        }
        if (who != this._lastTarget) {
            this._lastTarget = who;
        }
        return who;
    }
    
    private boolean isAttackReady() {
        final float ticks = 20.0f - TickRateManager.Get().getTickRate();
        return !this.HitDelay.getValue() || this.mc.player.getCooledAttackStrength(((boolean)this.TpsSync.getValue()) ? (-ticks) : 0.0f) >= 1.0f;
    }
}
