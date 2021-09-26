// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.command.Command;

public class AnnoyCommand extends Command
{
    public AnnoyCommand() {
        super("Annoy", "Choose who you will annoy.");
        this.commandChunks.add("<username>");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length < 1) {
            this.SendToChat("Please specify a target!");
        }
        else if (!PyroStatic.ANNOY.manualTarget.getValue()) {
            this.SendToChat("Please enable manual target in the Annoy Module.");
        }
        else {
            PyroStatic.ANNOY.targetName = args[1].toLowerCase();
            this.SendToChat("Locked on to " + args[1]);
        }
    }
    
    @Override
    public String getHelp() {
        return "Locks on to a specific player.\ntarget <username>";
    }
}
