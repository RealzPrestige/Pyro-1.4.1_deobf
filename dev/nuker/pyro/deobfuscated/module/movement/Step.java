//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import dev.nuker.pyro.deobfuscated.util.MovementUtils;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdateMoveState;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Step extends Module
{
    public final Value<String> Mode;
    public final Value<Boolean> EntityStep;
    public final Value<Float> Height;
    public final Value<Boolean> Reverse;
    public final Value<Boolean> UseTimer;
    private float _prevEntityStep;
    private int _stage;
    private double startY;
    private boolean _isReverseStep;
    private double stepHeight;
    private Timer timer;
    private int ncpNextStep;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerUpdateMoveState> onInputUpdate;
    @EventHandler
    private Listener<EventServerPacket> onPlayerPosLook;
    @EventHandler
    private Listener<EventPlayerMove> OnPlayerMove;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnMotionUpdate;
    
    public Step() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "Step"
        //     3: iconst_3       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             "Spider"
        //    11: aastore        
        //    12: dup            
        //    13: iconst_1       
        //    14: ldc             "NCPStep"
        //    16: aastore        
        //    17: dup            
        //    18: iconst_2       
        //    19: ldc             "Stairstep"
        //    21: aastore        
        //    22: ldc             "Allows you to walk up blocks like stairs"
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
        //    49: ldc             "The mode used for step on different types of servers"
        //    51: ldc             "Normal"
        //    53: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    56: putfield        com/Pyro/pyro/module/movement/Step.Mode:Lcom/Pyro/pyro/module/Value;
        //    59: aload_0         /* this */
        //    60: new             Lcom/Pyro/pyro/module/Value;
        //    63: dup            
        //    64: ldc             "EntityStep"
        //    66: iconst_1       
        //    67: anewarray       Ljava/lang/String;
        //    70: dup            
        //    71: iconst_0       
        //    72: ldc             "ES"
        //    74: aastore        
        //    75: ldc             "Modifies your riding entity to max step height"
        //    77: iconst_0       
        //    78: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    81: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    84: putfield        com/Pyro/pyro/module/movement/Step.EntityStep:Lcom/Pyro/pyro/module/Value;
        //    87: aload_0         /* this */
        //    88: new             Lcom/Pyro/pyro/module/Value;
        //    91: dup            
        //    92: ldc             "Height"
        //    94: iconst_1       
        //    95: anewarray       Ljava/lang/String;
        //    98: dup            
        //    99: iconst_0       
        //   100: ldc             "H"
        //   102: aastore        
        //   103: ldc             "Modifier of height"
        //   105: fconst_1       
        //   106: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   109: fconst_0       
        //   110: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   113: ldc             10.0
        //   115: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   118: fconst_1       
        //   119: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   122: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   125: putfield        com/Pyro/pyro/module/movement/Step.Height:Lcom/Pyro/pyro/module/Value;
        //   128: aload_0         /* this */
        //   129: new             Lcom/Pyro/pyro/module/Value;
        //   132: dup            
        //   133: ldc             "Reverse"
        //   135: iconst_1       
        //   136: anewarray       Ljava/lang/String;
        //   139: dup            
        //   140: iconst_0       
        //   141: ldc             "Reverse"
        //   143: aastore        
        //   144: ldc             "Reverse Step"
        //   146: iconst_0       
        //   147: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   150: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   153: putfield        com/Pyro/pyro/module/movement/Step.Reverse:Lcom/Pyro/pyro/module/Value;
        //   156: aload_0         /* this */
        //   157: new             Lcom/Pyro/pyro/module/Value;
        //   160: dup            
        //   161: ldc             "UseTimer"
        //   163: iconst_1       
        //   164: anewarray       Ljava/lang/String;
        //   167: dup            
        //   168: iconst_0       
        //   169: ldc             "UseTimer"
        //   171: aastore        
        //   172: ldc             "Uses timer to bypass the anticheat better"
        //   174: iconst_0       
        //   175: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   178: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   181: putfield        com/Pyro/pyro/module/movement/Step.UseTimer:Lcom/Pyro/pyro/module/Value;
        //   184: aload_0         /* this */
        //   185: iconst_0       
        //   186: putfield        com/Pyro/pyro/module/movement/Step._stage:I
        //   189: aload_0         /* this */
        //   190: dconst_0       
        //   191: putfield        com/Pyro/pyro/module/movement/Step.startY:D
        //   194: aload_0         /* this */
        //   195: iconst_0       
        //   196: putfield        com/Pyro/pyro/module/movement/Step._isReverseStep:Z
        //   199: aload_0         /* this */
        //   200: dconst_0       
        //   201: putfield        com/Pyro/pyro/module/movement/Step.stepHeight:D
        //   204: aload_0         /* this */
        //   205: new             Lcom/Pyro/pyro/util/Timer;
        //   208: dup            
        //   209: invokespecial   com/Pyro/pyro/util/Timer.<init>:()V
        //   212: putfield        com/Pyro/pyro/module/movement/Step.timer:Lcom/Pyro/pyro/util/Timer;
        //   215: aload_0         /* this */
        //   216: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   219: dup            
        //   220: aload_0         /* this */
        //   221: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/movement/Step;)Ljava/util/function/Consumer;
        //   226: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   229: putfield        com/Pyro/pyro/module/movement/Step.onPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   232: aload_0         /* this */
        //   233: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   236: dup            
        //   237: aload_0         /* this */
        //   238: invokedynamic   BootstrapMethod #1, accept:(Lcom/Pyro/pyro/module/movement/Step;)Ljava/util/function/Consumer;
        //   243: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   246: putfield        com/Pyro/pyro/module/movement/Step.onInputUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   249: aload_0         /* this */
        //   250: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   253: dup            
        //   254: aload_0         /* this */
        //   255: invokedynamic   BootstrapMethod #2, accept:(Lcom/Pyro/pyro/module/movement/Step;)Ljava/util/function/Consumer;
        //   260: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   263: putfield        com/Pyro/pyro/module/movement/Step.onPlayerPosLook:Lcom/Pyro/pyro/events/bus/Listener;
        //   266: aload_0         /* this */
        //   267: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   270: dup            
        //   271: aload_0         /* this */
        //   272: invokedynamic   BootstrapMethod #3, accept:(Lcom/Pyro/pyro/module/movement/Step;)Ljava/util/function/Consumer;
        //   277: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   280: putfield        com/Pyro/pyro/module/movement/Step.OnPlayerMove:Lcom/Pyro/pyro/events/bus/Listener;
        //   283: aload_0         /* this */
        //   284: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   287: dup            
        //   288: aload_0         /* this */
        //   289: invokedynamic   BootstrapMethod #4, accept:(Lcom/Pyro/pyro/module/movement/Step;)Ljava/util/function/Consumer;
        //   294: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   297: putfield        com/Pyro/pyro/module/movement/Step.OnMotionUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   300: aload_0         /* this */
        //   301: aload_0         /* this */
        //   302: invokevirtual   com/Pyro/pyro/module/movement/Step.getMetaData:()Ljava/lang/String;
        //   305: invokevirtual   com/Pyro/pyro/module/movement/Step.setMetaData:(Ljava/lang/String;)V
        //   308: aload_0         /* this */
        //   309: getfield        com/Pyro/pyro/module/movement/Step.Mode:Lcom/Pyro/pyro/module/Value;
        //   312: ldc             "Normal"
        //   314: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   317: aload_0         /* this */
        //   318: getfield        com/Pyro/pyro/module/movement/Step.Mode:Lcom/Pyro/pyro/module/Value;
        //   321: ldc             "AAC"
        //   323: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   326: aload_0         /* this */
        //   327: getfield        com/Pyro/pyro/module/movement/Step.Mode:Lcom/Pyro/pyro/module/Value;
        //   330: ldc             "Vanilla"
        //   332: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   335: aload_0         /* this */
        //   336: getfield        com/Pyro/pyro/module/movement/Step.Mode:Lcom/Pyro/pyro/module/Value;
        //   339: ldc             "MotionNCP"
        //   341: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   344: return         
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
    public void onEnable() {
        super.onEnable();
        this._stage = 0;
        if (this.mc.player != null && this.mc.player.isRiding()) {
            this._prevEntityStep = this.mc.player.getRidingEntity().stepHeight;
        }
        if (this.mc.player != null) {
            this.mc.player.stepHeight = 0.6f;
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.player.stepHeight = 0.6f;
        if (this.mc.player.isRiding()) {
            this.mc.player.getRidingEntity().stepHeight = this._prevEntityStep;
        }
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    private boolean couldStep() {
        final float yaw = (float)MovementUtils.getDirection();
        final double x = -MathHelper.sin(yaw) * 0.4;
        final double z = MathHelper.cos(yaw) * 0.4;
        return this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(x, 1.001335979112147, z)).isEmpty();
    }
    
    private double getStepHeight() {
        final boolean flag = this.mc.player.onGround && this.mc.player.collidedHorizontally;
        if (!flag) {
            return 0.0;
        }
        double maxY2 = -1.0;
        final float yaw = (float)MovementUtils.getDirection();
        final double x = -MathHelper.sin(yaw) * 0.4;
        final double z = MathHelper.cos(yaw) * 0.4;
        AxisAlignedBB grow2 = this.mc.player.getEntityBoundingBox().offset(0.0, 0.05, 0.0).grow(0.05);
        grow2 = grow2.setMaxY(grow2.maxY + this.Height.getValue());
        for (final AxisAlignedBB axisAlignedBB2 : this.mc.world.getCollisionBoxes((Entity)this.mc.player, grow2)) {
            if (axisAlignedBB2.maxY > maxY2) {
                maxY2 = axisAlignedBB2.maxY;
            }
        }
        maxY2 -= this.mc.player.posY;
        return (maxY2 > 0.0 && maxY2 <= this.Height.getValue()) ? maxY2 : 0.0;
    }
}
