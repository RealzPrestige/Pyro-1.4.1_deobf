//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.managers;

import java.util.ArrayList;
import dev.nuker.pyro.deobfuscated.util.MinecraftInstance;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import java.util.List;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.HashMap;

public class MacroManager
{
    private HashMap<String, Macro> macros;
    
    public MacroManager() {
        this.macros = new HashMap<String, Macro>();
    }
    
    public void Load() {
        try {
            final File files = new File(DirectoryManager.Get().GetCurrentDirectory() + "/Pyro/Macros/");
            for (final File file : files.listFiles()) {
                final List<String> lines = (List<String>)FileUtils.readLines(file, "UTF-8");
                final Macro mac = new Macro();
                lines.forEach(l -> mac.addText(l));
                this.macros.put(file.getName().replace(".txt", ""), mac);
            }
        }
        catch (Exception ex) {}
    }
    
    private void save() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/Pyro/pyro/managers/MacroManager.macros:Ljava/util/HashMap;
        //     4: invokedynamic   BootstrapMethod #1, accept:()Ljava/util/function/BiConsumer;
        //     9: invokevirtual   java/util/HashMap.forEach:(Ljava/util/function/BiConsumer;)V
        //    12: return         
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
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
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
    
    public void OnKeyPress(final String key) {
        this.macros.forEach((K, V) -> {
            if (K.equals(key)) {
                V.process();
            }
        });
    }
    
    public static MacroManager Get() {
        return Pyro.GetMacroManager();
    }
    
    public void addMacro(final String string, final String string2) {
        if (this.macros.containsKey(string)) {
            final Macro mac = this.macros.get(string);
            mac.addText(string2);
        }
        else {
            this.macros.put(string, new Macro(string2));
        }
        this.save();
    }
    
    public void removeMacro(final String string) {
        if (this.macros.containsKey(string)) {
            this.macros.remove(string);
        }
        this.save();
    }
    
    public class Macro extends MinecraftInstance
    {
        private ArrayList<String> Text;
        
        public Macro() {
            this.Text = new ArrayList<String>();
        }
        
        public Macro(final String string2) {
            (this.Text = new ArrayList<String>()).add(string2);
        }
        
        public void process() {
            this.Text.forEach(t -> Macro.mc.player.sendChatMessage(t));
        }
        
        public void removeText(final String text) {
            if (this.Text.contains(text)) {
                this.Text.remove(text);
            }
        }
        
        public void addText(final String text) {
            this.Text.add(text);
        }
    }
}
