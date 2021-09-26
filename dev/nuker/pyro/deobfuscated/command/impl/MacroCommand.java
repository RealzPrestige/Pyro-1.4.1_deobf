// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import dev.nuker.pyro.deobfuscated.managers.MacroManager;
import dev.nuker.pyro.deobfuscated.command.Command;

public class MacroCommand extends Command
{
    public MacroCommand() {
        super("Macro", "create macros");
        this.commandChunks.add("add <macro> <input>");
        this.commandChunks.add("remove <macro>");
        this.commandChunks.add("list");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        if (args[1].toLowerCase().startsWith("a") || args[1].toLowerCase().startsWith("c")) {
            if (args.length >= 4) {
                MacroManager.Get().addMacro(args[2].toUpperCase(), args[3]);
                this.SendToChat("added a macro named " + args[2] + " with args " + args[3]);
            }
        }
        else if (args[1].toLowerCase().startsWith("r")) {
            if (args.length >= 3) {
                MacroManager.Get().removeMacro(args[2].toUpperCase());
                this.SendToChat("Removed a macro at key " + args[2].toUpperCase());
            }
        }
        else if (args[1].toLowerCase().startsWith("l")) {}
    }
    
    @Override
    public String getHelp() {
        return "Allows you to create macros to execute commands right away";
    }
}
