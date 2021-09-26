// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import dev.nuker.pyro.deobfuscated.module.Module;
import dev.nuker.pyro.deobfuscated.managers.ModuleManager;
import dev.nuker.pyro.deobfuscated.command.Command;

public class BindCommand extends Command
{
    public BindCommand() {
        super("Bind", "Allows you to bind a mod to a key");
        this.commandChunks.add("<module>");
        this.commandChunks.add("<module> <key>");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        final Module searchedMod = ModuleManager.Get().GetModLike(args[1]);
        if (searchedMod != null) {
            if (args.length <= 2) {
                this.SendToChat(String.format("The key of %s is %s", searchedMod.getDisplayName(), searchedMod.getKey()));
                return;
            }
            searchedMod.setKey(args[2].toUpperCase());
            this.SendToChat(String.format("Set the key of %s to %s", searchedMod.getDisplayName(), searchedMod.getKey()));
        }
        else {
            this.SendToChat(String.format("Could not find the module named %s", args[1]));
        }
    }
    
    @Override
    public String getHelp() {
        return "Allows you to Bind a mod";
    }
}
