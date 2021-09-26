//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerTravel;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class ElytraFly extends Module
{
    public final Value<String> Mode;
    public final Value<Float> Speed;
    public final Value<Float> GlideSpeed;
    public final Value<Float> DownSpeed;
    public final Value<Boolean> PitchSpoof;
    public final Value<Boolean> UseTimer;
    public final Value<Boolean> InstantTakeoff;
    public final Value<Boolean> AutoAccelerate;
    public final Value<Boolean> InfiniteDurability;
    public final Value<Boolean> NCPStrict;
    private float currentSpeed;
    private float currentPitchSpoof;
    private float currentOffset;
    private float lastRotationYaw;
    private boolean canAutoAccelerate;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerTravel> OnTravel;
    @EventHandler
    private Listener<EventServerPacket> onPlayerPosLook;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdate;
    
    public ElytraFly() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "ElytraFly"
        //     3: iconst_3       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             "ElytraPlus"
        //    11: aastore        
        //    12: dup            
        //    13: iconst_1       
        //    14: ldc             "ElytraControl"
        //    16: aastore        
        //    17: dup            
        //    18: iconst_2       
        //    19: ldc             "ElytraFlight"
        //    21: aastore        
        //    22: ldc             "Allows you to fully control elytras"
        //    24: ldc             "NONE"
        //    26: iconst_m1      
        //    27: getstatic       com/Pyro/pyro/module/Module$ModuleType.MOVEMENT:Lcom/Pyro/pyro/module/Module$ModuleType;
        //    30: invokespecial   com/Pyro/pyro/module/Module.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/Pyro/pyro/module/Module$ModuleType;)V
        //    33: aload_0         /* this */
        //    34: new             Lcom/Pyro/pyro/module/Value;
        //    37: dup            
        //    38: ldc             "Mode"
        //    40: iconst_1       
        //    41: anewarray       Ljava/lang/String;
        //    44: dup            
        //    45: iconst_0       
        //    46: ldc             "M"
        //    48: aastore        
        //    49: ldc             "Modes of the speed to use"
        //    51: ldc             "Control"
        //    53: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    56: putfield        com/Pyro/pyro/module/movement/ElytraFly.Mode:Lcom/Pyro/pyro/module/Value;
        //    59: aload_0         /* this */
        //    60: new             Lcom/Pyro/pyro/module/Value;
        //    63: dup            
        //    64: ldc             "Speed"
        //    66: iconst_1       
        //    67: anewarray       Ljava/lang/String;
        //    70: dup            
        //    71: iconst_0       
        //    72: ldc             ""
        //    74: aastore        
        //    75: ldc             "Speed to use"
        //    77: ldc             1.8
        //    79: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    82: fconst_0       
        //    83: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    86: ldc             3.0
        //    88: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    91: fconst_1       
        //    92: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    95: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //    98: putfield        com/Pyro/pyro/module/movement/ElytraFly.Speed:Lcom/Pyro/pyro/module/Value;
        //   101: aload_0         /* this */
        //   102: new             Lcom/Pyro/pyro/module/Value;
        //   105: dup            
        //   106: ldc             "GlideSpeed"
        //   108: iconst_1       
        //   109: anewarray       Ljava/lang/String;
        //   112: dup            
        //   113: iconst_0       
        //   114: ldc             ""
        //   116: aastore        
        //   117: ldc             "GlideSpeed to use"
        //   119: ldc             2.73
        //   121: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   124: fconst_0       
        //   125: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   128: ldc             3.0
        //   130: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   133: fconst_1       
        //   134: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   137: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   140: putfield        com/Pyro/pyro/module/movement/ElytraFly.GlideSpeed:Lcom/Pyro/pyro/module/Value;
        //   143: aload_0         /* this */
        //   144: new             Lcom/Pyro/pyro/module/Value;
        //   147: dup            
        //   148: ldc             "DownSpeed"
        //   150: iconst_1       
        //   151: anewarray       Ljava/lang/String;
        //   154: dup            
        //   155: iconst_0       
        //   156: ldc             "DS"
        //   158: aastore        
        //   159: ldc             "DownSpeed multiplier for flight, higher values equals more speed."
        //   161: ldc             1.82
        //   163: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   166: fconst_0       
        //   167: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   170: ldc             10.0
        //   172: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   175: ldc             0.1
        //   177: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   180: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   183: putfield        com/Pyro/pyro/module/movement/ElytraFly.DownSpeed:Lcom/Pyro/pyro/module/Value;
        //   186: aload_0         /* this */
        //   187: new             Lcom/Pyro/pyro/module/Value;
        //   190: dup            
        //   191: ldc             "PitchSpoof"
        //   193: iconst_1       
        //   194: anewarray       Ljava/lang/String;
        //   197: dup            
        //   198: iconst_0       
        //   199: ldc             "PS"
        //   201: aastore        
        //   202: ldc             "Spoofs pitch as a workaround for hauses patch"
        //   204: iconst_1       
        //   205: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   208: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   211: putfield        com/Pyro/pyro/module/movement/ElytraFly.PitchSpoof:Lcom/Pyro/pyro/module/Value;
        //   214: aload_0         /* this */
        //   215: new             Lcom/Pyro/pyro/module/Value;
        //   218: dup            
        //   219: ldc             "UseTimer"
        //   221: iconst_1       
        //   222: anewarray       Ljava/lang/String;
        //   225: dup            
        //   226: iconst_0       
        //   227: ldc             "Timer"
        //   229: aastore        
        //   230: ldc             "Uses timer to go faster"
        //   232: iconst_1       
        //   233: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   236: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   239: putfield        com/Pyro/pyro/module/movement/ElytraFly.UseTimer:Lcom/Pyro/pyro/module/Value;
        //   242: aload_0         /* this */
        //   243: new             Lcom/Pyro/pyro/module/Value;
        //   246: dup            
        //   247: ldc             "InstantTakeoff"
        //   249: iconst_1       
        //   250: anewarray       Ljava/lang/String;
        //   253: dup            
        //   254: iconst_0       
        //   255: ldc             "InstantTakeOff"
        //   257: aastore        
        //   258: ldc             "Instantly takes off"
        //   260: iconst_1       
        //   261: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   264: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   267: putfield        com/Pyro/pyro/module/movement/ElytraFly.InstantTakeoff:Lcom/Pyro/pyro/module/Value;
        //   270: aload_0         /* this */
        //   271: new             Lcom/Pyro/pyro/module/Value;
        //   274: dup            
        //   275: ldc             "AutoAccelerate"
        //   277: iconst_1       
        //   278: anewarray       Ljava/lang/String;
        //   281: dup            
        //   282: iconst_0       
        //   283: ldc             "Accelerate"
        //   285: aastore        
        //   286: ldc             "(PacketMode): auto accelerates"
        //   288: iconst_1       
        //   289: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   292: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   295: putfield        com/Pyro/pyro/module/movement/ElytraFly.AutoAccelerate:Lcom/Pyro/pyro/module/Value;
        //   298: aload_0         /* this */
        //   299: new             Lcom/Pyro/pyro/module/Value;
        //   302: dup            
        //   303: ldc             "InfiniteDurability"
        //   305: iconst_1       
        //   306: anewarray       Ljava/lang/String;
        //   309: dup            
        //   310: iconst_0       
        //   311: ldc             "Inf"
        //   313: aastore        
        //   314: ldc             "(PacketMode): Infinite durability exploit"
        //   316: iconst_1       
        //   317: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   320: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   323: putfield        com/Pyro/pyro/module/movement/ElytraFly.InfiniteDurability:Lcom/Pyro/pyro/module/Value;
        //   326: aload_0         /* this */
        //   327: new             Lcom/Pyro/pyro/module/Value;
        //   330: dup            
        //   331: ldc             "NCPStrict"
        //   333: iconst_1       
        //   334: anewarray       Ljava/lang/String;
        //   337: dup            
        //   338: iconst_0       
        //   339: ldc             "NCPStrict"
        //   341: aastore        
        //   342: ldc             "(PacketMode): Allows working on a strict NCP config"
        //   344: iconst_0       
        //   345: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   348: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   351: putfield        com/Pyro/pyro/module/movement/ElytraFly.NCPStrict:Lcom/Pyro/pyro/module/Value;
        //   354: aload_0         /* this */
        //   355: ldc             0.1
        //   357: putfield        com/Pyro/pyro/module/movement/ElytraFly.currentSpeed:F
        //   360: aload_0         /* this */
        //   361: ldc             10.0
        //   363: putfield        com/Pyro/pyro/module/movement/ElytraFly.currentPitchSpoof:F
        //   366: aload_0         /* this */
        //   367: fconst_0       
        //   368: putfield        com/Pyro/pyro/module/movement/ElytraFly.currentOffset:F
        //   371: aload_0         /* this */
        //   372: fconst_0       
        //   373: putfield        com/Pyro/pyro/module/movement/ElytraFly.lastRotationYaw:F
        //   376: aload_0         /* this */
        //   377: iconst_1       
        //   378: putfield        com/Pyro/pyro/module/movement/ElytraFly.canAutoAccelerate:Z
        //   381: aload_0         /* this */
        //   382: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   385: dup            
        //   386: aload_0         /* this */
        //   387: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/movement/ElytraFly;)Ljava/util/function/Consumer;
        //   392: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   395: putfield        com/Pyro/pyro/module/movement/ElytraFly.onPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   398: aload_0         /* this */
        //   399: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   402: dup            
        //   403: aload_0         /* this */
        //   404: invokedynamic   BootstrapMethod #1, accept:(Lcom/Pyro/pyro/module/movement/ElytraFly;)Ljava/util/function/Consumer;
        //   409: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   412: putfield        com/Pyro/pyro/module/movement/ElytraFly.OnTravel:Lcom/Pyro/pyro/events/bus/Listener;
        //   415: aload_0         /* this */
        //   416: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   419: dup            
        //   420: aload_0         /* this */
        //   421: invokedynamic   BootstrapMethod #2, accept:(Lcom/Pyro/pyro/module/movement/ElytraFly;)Ljava/util/function/Consumer;
        //   426: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   429: putfield        com/Pyro/pyro/module/movement/ElytraFly.onPlayerPosLook:Lcom/Pyro/pyro/events/bus/Listener;
        //   432: aload_0         /* this */
        //   433: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   436: dup            
        //   437: aload_0         /* this */
        //   438: invokedynamic   BootstrapMethod #3, accept:(Lcom/Pyro/pyro/module/movement/ElytraFly;)Ljava/util/function/Consumer;
        //   443: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   446: putfield        com/Pyro/pyro/module/movement/ElytraFly.onMotionUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   449: aload_0         /* this */
        //   450: aload_0         /* this */
        //   451: invokevirtual   com/Pyro/pyro/module/movement/ElytraFly.getMetaData:()Ljava/lang/String;
        //   454: invokevirtual   com/Pyro/pyro/module/movement/ElytraFly.setMetaData:(Ljava/lang/String;)V
        //   457: aload_0         /* this */
        //   458: getfield        com/Pyro/pyro/module/movement/ElytraFly.Mode:Lcom/Pyro/pyro/module/Value;
        //   461: ldc             "Control"
        //   463: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   466: aload_0         /* this */
        //   467: getfield        com/Pyro/pyro/module/movement/ElytraFly.Mode:Lcom/Pyro/pyro/module/Value;
        //   470: ldc             "PitchControl"
        //   472: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   475: aload_0         /* this */
        //   476: getfield        com/Pyro/pyro/module/movement/ElytraFly.Mode:Lcom/Pyro/pyro/module/Value;
        //   479: ldc             "JetPack"
        //   481: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   484: aload_0         /* this */
        //   485: getfield        com/Pyro/pyro/module/movement/ElytraFly.Mode:Lcom/Pyro/pyro/module/Value;
        //   488: ldc             "Packet"
        //   490: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   493: return         
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
    public void onDisable() {
        super.onDisable();
        this.mc.player.capabilities.isFlying = false;
        PyroStatic.TIMER.SetOverrideSpeed(1.0f);
    }
}
