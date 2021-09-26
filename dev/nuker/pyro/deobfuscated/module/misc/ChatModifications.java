// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.network.play.server.SPacketChat;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class ChatModifications extends Module
{
    public final Value<String> TimeMode;
    public final Value<Boolean> AntiEZ;
    public final Value<Boolean> NoDiscord;
    public final Value<String> NameHighlight;
    public final Value<String> OtherPlayers;
    public final Value<Boolean> BetterFormat;
    public final Value<Integer> ChatLength;
    ChatFormatting nameHighlight;
    ChatFormatting otherPlayers;
    @EventHandler
    private final Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private final Listener<EventServerPacket> onServerPacket;
    
    public ChatModifications() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "ChatMods"
        //     3: iconst_2       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             "ChatStamp"
        //    11: aastore        
        //    12: dup            
        //    13: iconst_1       
        //    14: ldc             "ChatStamps"
        //    16: aastore        
        //    17: ldc             "Allows for chat modifications"
        //    19: ldc             "NONE"
        //    21: ldc             14361680
        //    23: getstatic       com/Pyro/pyro/module/Module$ModuleType.MISC:Lcom/Pyro/pyro/module/Module$ModuleType;
        //    26: invokespecial   com/Pyro/pyro/module/Module.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/Pyro/pyro/module/Module$ModuleType;)V
        //    29: aload_0         /* this */
        //    30: new             Lcom/Pyro/pyro/module/Value;
        //    33: dup            
        //    34: ldc             "TimeMode"
        //    36: iconst_2       
        //    37: anewarray       Ljava/lang/String;
        //    40: dup            
        //    41: iconst_0       
        //    42: ldc             "TimeModes"
        //    44: aastore        
        //    45: dup            
        //    46: iconst_1       
        //    47: ldc             "Time"
        //    49: aastore        
        //    50: ldc             "Time format, 12 hour (NA) or 24 hour (EU)."
        //    52: ldc             "NA"
        //    54: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    57: putfield        com/Pyro/pyro/module/misc/ChatModifications.TimeMode:Lcom/Pyro/pyro/module/Value;
        //    60: aload_0         /* this */
        //    61: new             Lcom/Pyro/pyro/module/Value;
        //    64: dup            
        //    65: ldc             "AntiEZ"
        //    67: iconst_1       
        //    68: anewarray       Ljava/lang/String;
        //    71: dup            
        //    72: iconst_0       
        //    73: ldc             "NoEZ"
        //    75: aastore        
        //    76: ldc             "Prevents EZ from being rendered in chat, very useful for 2b2tpvp"
        //    78: iconst_1       
        //    79: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    82: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    85: putfield        com/Pyro/pyro/module/misc/ChatModifications.AntiEZ:Lcom/Pyro/pyro/module/Value;
        //    88: aload_0         /* this */
        //    89: new             Lcom/Pyro/pyro/module/Value;
        //    92: dup            
        //    93: ldc             "NoDiscord"
        //    95: iconst_1       
        //    96: anewarray       Ljava/lang/String;
        //    99: dup            
        //   100: iconst_0       
        //   101: ldc             "NoEZ"
        //   103: aastore        
        //   104: ldc             "Prevents discord from being rendered in chat"
        //   106: iconst_1       
        //   107: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   110: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   113: putfield        com/Pyro/pyro/module/misc/ChatModifications.NoDiscord:Lcom/Pyro/pyro/module/Value;
        //   116: aload_0         /* this */
        //   117: new             Lcom/Pyro/pyro/module/Value;
        //   120: dup            
        //   121: ldc             "NameHighlight"
        //   123: iconst_1       
        //   124: anewarray       Ljava/lang/String;
        //   127: dup            
        //   128: iconst_0       
        //   129: ldc             "Highlight"
        //   131: aastore        
        //   132: ldc             "Highlights your name in the selected Color."
        //   134: ldc             "None"
        //   136: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   139: putfield        com/Pyro/pyro/module/misc/ChatModifications.NameHighlight:Lcom/Pyro/pyro/module/Value;
        //   142: aload_0         /* this */
        //   143: new             Lcom/Pyro/pyro/module/Value;
        //   146: dup            
        //   147: ldc             "OtherPlayers"
        //   149: iconst_1       
        //   150: anewarray       Ljava/lang/String;
        //   153: dup            
        //   154: iconst_0       
        //   155: ldc             "OtherPlayers"
        //   157: aastore        
        //   158: ldc             "Choose the color other player names shows up in."
        //   160: ldc             "None"
        //   162: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   165: putfield        com/Pyro/pyro/module/misc/ChatModifications.OtherPlayers:Lcom/Pyro/pyro/module/Value;
        //   168: aload_0         /* this */
        //   169: new             Lcom/Pyro/pyro/module/Value;
        //   172: dup            
        //   173: ldc             "BetterFormat"
        //   175: iconst_1       
        //   176: anewarray       Ljava/lang/String;
        //   179: dup            
        //   180: iconst_0       
        //   181: ldc             "BetterFormat"
        //   183: aastore        
        //   184: ldc             "Removes Carrots in Chat."
        //   186: iconst_1       
        //   187: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   190: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   193: putfield        com/Pyro/pyro/module/misc/ChatModifications.BetterFormat:Lcom/Pyro/pyro/module/Value;
        //   196: aload_0         /* this */
        //   197: new             Lcom/Pyro/pyro/module/Value;
        //   200: dup            
        //   201: ldc             "ChatLength"
        //   203: iconst_1       
        //   204: anewarray       Ljava/lang/String;
        //   207: dup            
        //   208: iconst_0       
        //   209: ldc             "ChatLength"
        //   211: aastore        
        //   212: ldc             "ChatLength number for more chat length"
        //   214: bipush          100
        //   216: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   219: iconst_0       
        //   220: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   223: ldc             16777215
        //   225: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   228: sipush          1000
        //   231: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   234: invokespecial   com/Pyro/pyro/module/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   237: putfield        com/Pyro/pyro/module/misc/ChatModifications.ChatLength:Lcom/Pyro/pyro/module/Value;
        //   240: aload_0         /* this */
        //   241: aconst_null    
        //   242: putfield        com/Pyro/pyro/module/misc/ChatModifications.nameHighlight:Lcom/mojang/realmsclient/gui/ChatFormatting;
        //   245: aload_0         /* this */
        //   246: aconst_null    
        //   247: putfield        com/Pyro/pyro/module/misc/ChatModifications.otherPlayers:Lcom/mojang/realmsclient/gui/ChatFormatting;
        //   250: aload_0         /* this */
        //   251: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   254: dup            
        //   255: aload_0         /* this */
        //   256: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/misc/ChatModifications;)Ljava/util/function/Consumer;
        //   261: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   264: putfield        com/Pyro/pyro/module/misc/ChatModifications.onPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   267: aload_0         /* this */
        //   268: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   271: dup            
        //   272: aload_0         /* this */
        //   273: invokedynamic   BootstrapMethod #1, accept:(Lcom/Pyro/pyro/module/misc/ChatModifications;)Ljava/util/function/Consumer;
        //   278: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   281: putfield        com/Pyro/pyro/module/misc/ChatModifications.onServerPacket:Lcom/Pyro/pyro/events/bus/Listener;
        //   284: aload_0         /* this */
        //   285: aload_0         /* this */
        //   286: invokevirtual   com/Pyro/pyro/module/misc/ChatModifications.getMetaData:()Ljava/lang/String;
        //   289: invokevirtual   com/Pyro/pyro/module/misc/ChatModifications.setMetaData:(Ljava/lang/String;)V
        //   292: aload_0         /* this */
        //   293: getfield        com/Pyro/pyro/module/misc/ChatModifications.TimeMode:Lcom/Pyro/pyro/module/Value;
        //   296: ldc             "NA"
        //   298: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   301: aload_0         /* this */
        //   302: getfield        com/Pyro/pyro/module/misc/ChatModifications.TimeMode:Lcom/Pyro/pyro/module/Value;
        //   305: ldc             "EU"
        //   307: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   310: aload_0         /* this */
        //   311: getfield        com/Pyro/pyro/module/misc/ChatModifications.TimeMode:Lcom/Pyro/pyro/module/Value;
        //   314: ldc             "NONE"
        //   316: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   319: aload_0         /* this */
        //   320: getfield        com/Pyro/pyro/module/misc/ChatModifications.NameHighlight:Lcom/Pyro/pyro/module/Value;
        //   323: ldc             "None"
        //   325: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   328: aload_0         /* this */
        //   329: getfield        com/Pyro/pyro/module/misc/ChatModifications.NameHighlight:Lcom/Pyro/pyro/module/Value;
        //   332: ldc             "Gold"
        //   334: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   337: aload_0         /* this */
        //   338: getfield        com/Pyro/pyro/module/misc/ChatModifications.NameHighlight:Lcom/Pyro/pyro/module/Value;
        //   341: ldc             "DarkRed"
        //   343: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   346: aload_0         /* this */
        //   347: getfield        com/Pyro/pyro/module/misc/ChatModifications.NameHighlight:Lcom/Pyro/pyro/module/Value;
        //   350: ldc             "Red"
        //   352: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   355: aload_0         /* this */
        //   356: getfield        com/Pyro/pyro/module/misc/ChatModifications.NameHighlight:Lcom/Pyro/pyro/module/Value;
        //   359: ldc             "DarkGreen"
        //   361: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   364: aload_0         /* this */
        //   365: getfield        com/Pyro/pyro/module/misc/ChatModifications.NameHighlight:Lcom/Pyro/pyro/module/Value;
        //   368: ldc             "Green"
        //   370: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   373: aload_0         /* this */
        //   374: getfield        com/Pyro/pyro/module/misc/ChatModifications.NameHighlight:Lcom/Pyro/pyro/module/Value;
        //   377: ldc             "DarkBlue"
        //   379: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   382: aload_0         /* this */
        //   383: getfield        com/Pyro/pyro/module/misc/ChatModifications.NameHighlight:Lcom/Pyro/pyro/module/Value;
        //   386: ldc             "Blue"
        //   388: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   391: aload_0         /* this */
        //   392: getfield        com/Pyro/pyro/module/misc/ChatModifications.OtherPlayers:Lcom/Pyro/pyro/module/Value;
        //   395: ldc             "None"
        //   397: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   400: aload_0         /* this */
        //   401: getfield        com/Pyro/pyro/module/misc/ChatModifications.OtherPlayers:Lcom/Pyro/pyro/module/Value;
        //   404: ldc             "Gold"
        //   406: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   409: aload_0         /* this */
        //   410: getfield        com/Pyro/pyro/module/misc/ChatModifications.OtherPlayers:Lcom/Pyro/pyro/module/Value;
        //   413: ldc             "DarkRed"
        //   415: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   418: aload_0         /* this */
        //   419: getfield        com/Pyro/pyro/module/misc/ChatModifications.OtherPlayers:Lcom/Pyro/pyro/module/Value;
        //   422: ldc             "Red"
        //   424: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   427: aload_0         /* this */
        //   428: getfield        com/Pyro/pyro/module/misc/ChatModifications.OtherPlayers:Lcom/Pyro/pyro/module/Value;
        //   431: ldc             "DarkGreen"
        //   433: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   436: aload_0         /* this */
        //   437: getfield        com/Pyro/pyro/module/misc/ChatModifications.OtherPlayers:Lcom/Pyro/pyro/module/Value;
        //   440: ldc             "Green"
        //   442: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   445: aload_0         /* this */
        //   446: getfield        com/Pyro/pyro/module/misc/ChatModifications.OtherPlayers:Lcom/Pyro/pyro/module/Value;
        //   449: ldc             "DarkBlue"
        //   451: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   454: aload_0         /* this */
        //   455: getfield        com/Pyro/pyro/module/misc/ChatModifications.OtherPlayers:Lcom/Pyro/pyro/module/Value;
        //   458: ldc             "Blue"
        //   460: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   463: aload_0         /* this */
        //   464: getfield        com/Pyro/pyro/module/misc/ChatModifications.OtherPlayers:Lcom/Pyro/pyro/module/Value;
        //   467: ldc             "DarkGray"
        //   469: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   472: aload_0         /* this */
        //   473: getfield        com/Pyro/pyro/module/misc/ChatModifications.OtherPlayers:Lcom/Pyro/pyro/module/Value;
        //   476: ldc             "Gray"
        //   478: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   481: aload_0         /* this */
        //   482: getfield        com/Pyro/pyro/module/misc/ChatModifications.OtherPlayers:Lcom/Pyro/pyro/module/Value;
        //   485: ldc             "White"
        //   487: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   490: return         
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
        return this.TimeMode.getValue();
    }
}
