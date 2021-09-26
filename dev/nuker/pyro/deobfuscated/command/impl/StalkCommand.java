// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import dev.nuker.pyro.deobfuscated.util.PlayerAlert;
import dev.nuker.pyro.deobfuscated.command.Command;

public class StalkCommand extends Command
{
    public StalkCommand() {
        super("Stalk", "queue peek");
        this.commandChunks.add("add <username>");
        this.commandChunks.add("remove <username>");
        this.commandChunks.add("list");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        if (args[1].toLowerCase().startsWith("a")) {
            PlayerAlert.addName(args[2]);
            this.SendToChat("Added " + args[2]);
        }
        else if (args[1].toLowerCase().startsWith("r")) {
            PlayerAlert.removeName(args[2]);
            this.SendToChat("Removed " + args[2]);
        }
    }
    
    @Override
    public String getHelp() {
        return "Allows you to stalk people, or remove stalked people or list people being stalked..\nstalk add <name>\nstalk remove<name>\nstalk list";
    }
}
