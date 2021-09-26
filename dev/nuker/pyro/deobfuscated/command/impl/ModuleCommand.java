// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import dev.nuker.pyro.deobfuscated.module.Value;
import java.util.List;
import dev.nuker.pyro.deobfuscated.command.util.ModuleCommandListener;
import dev.nuker.pyro.deobfuscated.command.Command;

public class ModuleCommand extends Command
{
    private final ModuleCommandListener listener;
    private final List<Value<?>> values;
    
    public ModuleCommand(final String name, final String description, final ModuleCommandListener listener, final List<Value<?>> values) {
        super(name, description);
        this.listener = listener;
        this.values = values;
        this.commandChunks.add("hide");
        this.commandChunks.add("toggle");
        this.commandChunks.add("rename <newname>");
        for (final Value<?> value : this.values) {
            this.commandChunks.add(String.format("%s <%s>", value.getName(), "value"));
        }
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length <= 1) {
            for (final Value<?> value : this.values) {
                this.SendToChat(String.format("%s : %s", value.getName(), value.getValue()));
            }
            return;
        }
        if (args[1].equalsIgnoreCase("hide")) {
            this.listener.onHide();
            return;
        }
        if (args[1].equalsIgnoreCase("toggle")) {
            this.listener.onHide();
            return;
        }
        if (args[1].equalsIgnoreCase("rename")) {
            if (args.length <= 3) {
                this.listener.onRename(args[2]);
            }
            return;
        }
        for (final Value value2 : this.values) {
            if (value2.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                if (args.length <= 2) {
                    break;
                }
                final String unknownValue = args[2].toLowerCase();
                if (value2.getValue() instanceof Number && !(value2.getValue() instanceof Enum)) {
                    if (value2.getValue() instanceof Integer) {
                        value2.SetForcedValue(Integer.parseInt(unknownValue));
                    }
                    else if (value2.getValue() instanceof Float) {
                        value2.SetForcedValue(Float.parseFloat(unknownValue));
                    }
                    else if (value2.getValue() instanceof Double) {
                        value2.SetForcedValue(Double.parseDouble(unknownValue));
                    }
                }
                else if (value2.getValue() instanceof Boolean) {
                    value2.SetForcedValue(unknownValue.equalsIgnoreCase("true"));
                }
                else if (value2.getValue() instanceof String) {
                    value2.setStringValue(unknownValue);
                }
                this.SendToChat(String.format("Set the value of %s to %s", value2.getName(), value2.getValue()));
                break;
            }
        }
    }
    
    @Override
    public String getHelp() {
        return this.getDescription();
    }
}
