// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import dev.nuker.pyro.deobfuscated.managers.PresetsManager;
import dev.nuker.pyro.deobfuscated.command.Command;

public class PresetsCommand extends Command
{
    public PresetsCommand() {
        super("Presets", "Allows you to create custom presets");
        this.commandChunks.add("create <name>");
        this.commandChunks.add("delete <name>");
        this.commandChunks.add("list");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        if (args[1].toLowerCase().startsWith("c")) {
            final String presetName = args[2].toLowerCase();
            if (!presetName.equalsIgnoreCase("Default")) {
                PresetsManager.Get().CreatePreset(presetName);
                this.SendToChat("Created a preset named " + presetName);
            }
            else {
                this.SendToChat("Default preset is reserved!");
            }
        }
        else if (args[1].toLowerCase().startsWith("d")) {
            final String presetName = args[2].toLowerCase();
            if (!presetName.equalsIgnoreCase("Default")) {
                PresetsManager.Get().RemovePreset(presetName);
                this.SendToChat("Removed a preset named " + presetName);
            }
            else {
                this.SendToChat("Default preset is reserved!");
            }
        }
        else if (args[1].toLowerCase().startsWith("l")) {
            PresetsManager.Get().GetItems().forEach(p -> this.SendToChat(p.getName()));
        }
    }
    
    @Override
    public String getHelp() {
        return "Allows you to create, remove and list the presets";
    }
}
