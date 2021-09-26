//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.PotionEffect;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.init.MobEffects;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.world.World;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class Fullbright extends Module
{
    public final Value<String> mode;
    public final Value<Boolean> effects;
    private float lastGamma;
    private World world;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    
    public Fullbright() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "Fullbright"
        //     3: iconst_3       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             "FullBright"
        //    11: aastore        
        //    12: dup            
        //    13: iconst_1       
        //    14: ldc             "Bright"
        //    16: aastore        
        //    17: dup            
        //    18: iconst_2       
        //    19: ldc             "Brightness"
        //    21: aastore        
        //    22: ldc             "Makes the world brighter"
        //    24: ldc             "NONE"
        //    26: ldc             15834214
        //    28: getstatic       com/Pyro/pyro/module/Module$ModuleType.RENDER:Lcom/Pyro/pyro/module/Module$ModuleType;
        //    31: invokespecial   com/Pyro/pyro/module/Module.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/Pyro/pyro/module/Module$ModuleType;)V
        //    34: aload_0         /* this */
        //    35: new             Lcom/Pyro/pyro/module/Value;
        //    38: dup            
        //    39: ldc             "Mode"
        //    41: iconst_2       
        //    42: anewarray       Ljava/lang/String;
        //    45: dup            
        //    46: iconst_0       
        //    47: ldc             "Mode"
        //    49: aastore        
        //    50: dup            
        //    51: iconst_1       
        //    52: ldc             "M"
        //    54: aastore        
        //    55: ldc             "The brightness mode to use."
        //    57: ldc             "Gamma"
        //    59: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    62: putfield        com/Pyro/pyro/module/render/Fullbright.mode:Lcom/Pyro/pyro/module/Value;
        //    65: aload_0         /* this */
        //    66: new             Lcom/Pyro/pyro/module/Value;
        //    69: dup            
        //    70: ldc             "Effects"
        //    72: iconst_1       
        //    73: anewarray       Ljava/lang/String;
        //    76: dup            
        //    77: iconst_0       
        //    78: ldc             "Eff"
        //    80: aastore        
        //    81: ldc             "Blocks blindness & nausea effects if enabled."
        //    83: iconst_1       
        //    84: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    87: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    90: putfield        com/Pyro/pyro/module/render/Fullbright.effects:Lcom/Pyro/pyro/module/Value;
        //    93: aload_0         /* this */
        //    94: new             Lcom/Pyro/pyro/events/bus/Listener;
        //    97: dup            
        //    98: aload_0         /* this */
        //    99: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/render/Fullbright;)Ljava/util/function/Consumer;
        //   104: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   107: putfield        com/Pyro/pyro/module/render/Fullbright.OnPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   110: aload_0         /* this */
        //   111: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   114: dup            
        //   115: aload_0         /* this */
        //   116: invokedynamic   BootstrapMethod #1, accept:(Lcom/Pyro/pyro/module/render/Fullbright;)Ljava/util/function/Consumer;
        //   121: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   124: putfield        com/Pyro/pyro/module/render/Fullbright.onServerPacket:Lcom/Pyro/pyro/events/bus/Listener;
        //   127: aload_0         /* this */
        //   128: aload_0         /* this */
        //   129: invokevirtual   com/Pyro/pyro/module/render/Fullbright.getMetaData:()Ljava/lang/String;
        //   132: invokevirtual   com/Pyro/pyro/module/render/Fullbright.setMetaData:(Ljava/lang/String;)V
        //   135: aload_0         /* this */
        //   136: getfield        com/Pyro/pyro/module/render/Fullbright.mode:Lcom/Pyro/pyro/module/Value;
        //   139: ldc             "Gamma"
        //   141: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   144: aload_0         /* this */
        //   145: getfield        com/Pyro/pyro/module/render/Fullbright.mode:Lcom/Pyro/pyro/module/Value;
        //   148: ldc             "Potion"
        //   150: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   153: aload_0         /* this */
        //   154: getfield        com/Pyro/pyro/module/render/Fullbright.mode:Lcom/Pyro/pyro/module/Value;
        //   157: ldc             "Table"
        //   159: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   162: return         
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
        return String.valueOf(this.mode.getValue());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mode.getValue().equals("Gamma")) {
            this.lastGamma = this.mc.gameSettings.gammaSetting;
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mode.getValue().equals("Gamma")) {
            this.mc.gameSettings.gammaSetting = this.lastGamma;
        }
        if (this.mode.getValue().equals("Potion") && this.mc.player != null) {
            this.mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
        }
        if (this.mode.getValue().equals("Table") && this.mc.world != null) {
            for (int i = 0; i <= 15; ++i) {
                final float f1 = 1.0f - i / 15.0f;
                this.mc.world.provider.getLightBrightnessTable()[i] = (1.0f - f1) / (f1 * 3.0f + 1.0f) * 1.0f + 0.0f;
            }
        }
    }
}
